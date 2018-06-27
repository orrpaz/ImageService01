using ImageService.Communtication;
using ImageService.Logging;
using ImageService.Server;
using Infrastructure.Enum;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace ImageService.Communtication
{
    class MobileServer : ITCPServer
    {
       
        private int port;
        private TcpListener listener;
        private MobileHandler ch;
        private ILoggingService m_logging;
        private bool closeConn;
      
        public MobileServer(int port, MobileHandler ch, ILoggingService log)
        {
            this.port = port;
            this.ch = ch;
            this.m_logging = log;
            closeConn = false;
            
        }
        public void Start()
        {
            IPEndPoint ep = new IPEndPoint(IPAddress.Parse("127.0.0.1"), 7999);
            listener = new TcpListener(ep);
            listener.Start();
            m_logging.Log("Started TCP Server", MessageTypeEnum.INFO);

            Task task = new Task(() =>
            {
                while (true)
                {
                    try
                    {
                        TcpClient client = listener.AcceptTcpClient();
                        m_logging.Log("Got new connection", MessageTypeEnum.INFO);
                        ch.HandleClient(client);
                    }
                    catch (SocketException)
                    {
                        m_logging.Log("Socket exception", MessageTypeEnum.FAIL);
                       // break;
                    }
                }
                m_logging.Log("Server stopped", MessageTypeEnum.INFO);
            });
            task.Start();
        }

        public void Stop()
        {
            //  ch.closeAllConnections();
            closeConn = true;
            listener.Stop();
        }
    }
}
