/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csocial.server.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mhack
 */
public class AppController extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest req,
            HttpServletResponse res) throws ServletException, IOException {

        String action = (String) req.getParameter("a");
        dispatch(req, res, getPage(action));
    }

    protected String getPage(String action) {
        if (action == null) {
            return "/home";

        } else if (action.equals("register")) {
            return "/register";

        } else if (action.equals("messages")) {
            return "/message";

        } else if (action.equals("friends")) {
            return "/friendship";

        } else if (action.equals("photos")) {
            return "/photo";

        } else if (action.equals("videos")) {
            return "/video";

        } else if (action.equals("profile")) {
            return "/profile";

        } else if (action.equals("logout")) {
            return "/logout";

        } else {
            return "/home";
        }
    }

    @Override
    public String getServletInfo() {
        return "Application Controller";
    }
}
