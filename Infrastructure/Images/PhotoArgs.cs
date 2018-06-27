using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Infrastructure.Images
{
    public class PhotoArgs
    {
        public string m_date { get; set; }      // The Command ID
        public string m_name { get; set; }
        public string m_path { get; set; }

        /// <summary>
        /// Constructor
        /// </summary>
        /// <param name="date"></param>
        /// <param name="name"></param>
        /// <param name="path"></param>
        public PhotoArgs(string date, string name, string path)
        {
            m_name = name;
            m_path = path;
            m_date = date;
        }
        /// <summary>
        /// Another constructor
        /// </summary>
        /// <param name="path"></param>
        public PhotoArgs(string path)
        {
            m_path = path;
        }




    }


}
