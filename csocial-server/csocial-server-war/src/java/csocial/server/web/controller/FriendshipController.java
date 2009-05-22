/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csocial.server.web.controller;

import csocial.server.entity.User;
import csocial.server.service.FriendshipManager;
import csocial.server.service.UserManager;
import java.io.IOException;
import java.util.List;
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

        Long userID = getUserID(req);
        User user = userManagerBean.findById(userID);
        List<User> friends = friendshipManagerBean.getFriendList(user);

        req.setAttribute("friend_list", friends);

        dispatch(req, res, getResourcePath("friendship.jsp"));
    }


    @Override
    public String getServletInfo() {
        return "Friendship Controller";
    }
}
