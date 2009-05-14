/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csocial.server.controller;

import csocial.server.service.FriendshipManager;
import csocial.server.service.UserManager;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mhack
 */
public class FriendshipController extends BaseController {

    @EJB
    private UserManager userManagerBean;
    @EJB
    private FriendshipManager friendshipManagerBean;

    @Override
    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        dispatch(req, res, "/friendship.jsp");
    }


    @Override
    public String getServletInfo() {
        return "Friendship Controller";
    }
}
