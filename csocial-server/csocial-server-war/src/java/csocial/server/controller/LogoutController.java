/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csocial.server.controller;

import csocial.server.entity.User;
import csocial.server.service.UserManager;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mhack
 */
public class LogoutController extends BaseController {
    
    @EJB
    UserManager userManager;

    @Override
    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {
        String username = (String) req.getAttribute("username");
        User user = userManager.findByUsername(username);

        HttpSession session = req.getSession();
        session.removeAttribute("user");
        session.removeAttribute("username");
        session.removeAttribute("isAuthenticated");

        dispatch(req, res, "/login.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Login Controller";
    }
}
