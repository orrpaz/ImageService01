using ImageService.Logging;
using ImageService.Logging.Modal;
using ImageService.Server;
using Infrastructure.Enum;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace ImageService.Communtication
{
    public class MobileHandler
    {
        private bool clientConnected;
      
        private ILoggingService m_logging;
        private ImageServer m_imageServer;

        public MobileHandler(ImageServer imageServer, ILoggingService logging)
        {
            this.m_logging = logging;
            this.m_imageServer = imageServer;
            //this.clientConnected = true;
        }



        /// <summary>
        /// handle the client
        /// </summary>
        /// <param name="client">client</param>
        public void HandleClient(TcpClient client)
        {
            NetworkStream stream = client.GetStream();
            BinaryReader reader = new BinaryReader(stream);
            new Task(() =>
            {
                while (true)
                {
                    try
                    {
                        Byte[] fileName = ReadMessage(reader);
                        Byte[] image = ReadMessage(reader);
                        if (image == null || fileName == null)
                        {
                            return;
                        }
                        TransferImage(fileName, image);
                    }
                    catch (Exception e)
                    {
                        this.clientConnected = false;
                        e.Data.ToString();
                        return;
                    }

                }
            }).Start();
        }


    public void TransferImage(Byte[] bytesFileName, Byte[] imageBytes)
        {
            string fileName = Path.GetFileName(Encoding.UTF8.GetString(bytesFileName,0, bytesFileName.Length));
            File.WriteAllBytes(m_imageServer.handlersList[0] + "\\" + fileName.ToString(), imageBytes);
        }

        public Byte[] ReadMessage(BinaryReader reader)
        {
            int len = 0;
            byte[] bytesLen = reader.ReadBytes(4);

            if (BitConverter.IsLittleEndian)
            {
                Array.Reverse(bytesLen);
            }
            try
            {
                 len = BitConverter.ToInt32(bytesLen, 0);
            } catch (Exception e)
            {
                e.Data.ToString();
                m_logging.Log("exception BitConverter " + e.Message, MessageTypeEnum.FAIL);
                return null;
            }

            return reader.ReadBytes(len);
        }

    }
}