/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csocial.server.web.controller;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mhack
 */
public abstract class BaseController extends HttpServlet {
    public static final String WEB_PAGES_PREFIX = "/pages";
    private HttpSession session;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        session = req.getSession();
        setAttributes(req);
        processRequest(req, res);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        session = req.getSession();
        setAttributes(req);
        processRequest(req, res);
    }

    protected abstract void processRequest(HttpServletRequest req,
            HttpServletResponse res) throws ServletException, IOException;

    protected void dispatch(HttpServletRequest req, HttpServletResponse res,
            String page) throws ServletException, IOException {

        ServletContext context = getSession().getServletContext();
        context.getRequestDispatcher(page).forward(req, res);
    }

    public HttpSession getSession() {
        return session;
    }

    protected Long getUserID(HttpServletRequest req) {
        Long userID = (Long) getSession().getAttribute("user_id");
        return userID;
    }

    public static String getResourcePath(String page) {
        return WEB_PAGES_PREFIX + "/" + page;
    }

    protected void setAttributes(HttpServletRequest req) {
        
    }
}
