using ImageServiceWeb.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace ImageServiceWeb.Controllers
{
    public class ConfigController : Controller
    {
        static ConfigModel config = new ConfigModel();
        private static string m_toBeDeletedHandler;


        /// <summary>
        /// constructor.
        /// </summary>
        public ConfigController()
        {
         //   config.Notify -= WhenChanged;
           // config.NotifyEvent += WhenChanged;
        }
        /// <summary>
        /// Notify function.
        /// notify view about change.
        /// </summary>
        public ActionResult WhenChanged()
        {
            return View(config);
        }

        // GET: Config/DeleteHandler/
        public ActionResult DeleteHandler(string toBeDeletedHandler)
        {
            m_toBeDeletedHandler = toBeDeletedHandler;
            return RedirectToAction("Confirm");

        }
        // GET: Confirm
        public ActionResult Confirm()
        {
            return View(config);
        }
        // GET: Config
        public ActionResult Config()
        {
     //       config.getConfig();
            return View(config);
        }
        //// GET: Config/DeleteOK/
        //public ActionResult DeleteOK()
        //{
        //    //delete the handler
        //    config.DeleteHandler(m_toBeDeletedHandler);
        //    //go back to config page
        //    return RedirectToAction("Config");

        //}
        // GET: Config/DeleteCancel/
        public ActionResult DeleteCancel()
        {
            //go back to config page
            return RedirectToAction("Config");

        }
    }
}