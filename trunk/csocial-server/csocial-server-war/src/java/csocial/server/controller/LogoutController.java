/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csocial.server.controller;

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
        HttpSession session = req.getSession();
        session.invalidate();
        dispatch(req, res, "/login.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Login Controller";
    }
}
