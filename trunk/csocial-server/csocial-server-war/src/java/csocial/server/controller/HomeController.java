/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csocial.server.controller;

import csocial.server.entity.User;
import csocial.server.service.FriendshipManager;
import csocial.server.service.UserManager;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mhack
 */
public class HomeController extends BaseController {
    @EJB
    private UserManager userManager;
    @EJB
    private FriendshipManager fsManager;

    @Override
    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException
    {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        List<User> friendList = fsManager.getFriendList(user);

        req.setAttribute("friendList", friendList);
        req.setAttribute("friend_count", friendList.size());

        dispatch(req, res, "/home.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Home Controller";
    }
}
