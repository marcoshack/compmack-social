/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csocial.server.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mhack
 */
public class WebController extends BaseController {

    @Override
    protected void processRequest(HttpServletRequest req,
            HttpServletResponse res) throws ServletException, IOException {

        String action = (String) req.getParameter("a");
        String url = getControllerURL(action);
        log("Dispatching request action \"" + action + "\" to controller URL \""
                + url);
        dispatch(req, res, url);
    }

    protected String getControllerURL(String action) {
        if (action == null) {
            return "/web/home";

        } else if (action.equals("register")) {
            return "/web/register";

        } else if (action.equals("messages")) {
            return "/web/message";

        } else if (action.equals("friends")) {
            return "/web/friendship";

        } else if (action.equals("photos")) {
            return "/web/photo";

        } else if (action.equals("videos")) {
            return "/web/video";

        } else if (action.equals("profile")) {
            return "/web/profile";

        } else if (action.equals("logout")) {
            return "/web/logout";

        } else {
            return "/web/home";
        }
    }

    @Override
    public String getServletInfo() {
        return "Application Controller";
    }
}
