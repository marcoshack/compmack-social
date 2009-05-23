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

    private static final String ATTR_SEARCH_FRIENDS = "search_friends";
    private static final String ATTR_FRIEND_LIST = "friend_list";
    private static final String ATTR_FRIEND_COUNT = "friend_count";
    private static final String ATTR_BUTTON_SEARCH = "button_search";
    private static final String ATTR_SEARCH_RESULT = "search_result";
    @EJB
    private UserManager userManagerBean;
    @EJB
    private FriendshipManager friendshipManagerBean;
    private String searchFriends;

    @Override
    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        processFriendList(req, res);

        if (searchFriends != null && !searchFriends.equals("")) {
            processFriendSearch(req, res);
        } else {
            req.removeAttribute(ATTR_SEARCH_RESULT);
        }

        dispatch(req, res, getResourcePath("friendship.jsp"));
    }

    private void processFriendList(HttpServletRequest req, HttpServletResponse res) {
        Long userID = getUserID(req);
        User user = userManagerBean.findById(userID);
        List<User> friends = friendshipManagerBean.getFriendList(user);

        req.setAttribute(ATTR_FRIEND_LIST, friends);
        req.setAttribute(ATTR_FRIEND_COUNT, friends.size());
    }

    private void processFriendSearch(HttpServletRequest req, HttpServletResponse res) {
        List<User> searchResult = userManagerBean.find(searchFriends);
        req.setAttribute(ATTR_SEARCH_RESULT, searchResult);
    }

    @Override
    public String getServletInfo() {
        return "Friendship Controller";
    }

    @Override
    protected void setAttributes(HttpServletRequest req) {
        if (req.getParameter(ATTR_BUTTON_SEARCH) != null) {
            searchFriends = req.getParameter(ATTR_SEARCH_FRIENDS);
            req.setAttribute(ATTR_SEARCH_FRIENDS, searchFriends);

        } else {
            req.setAttribute(ATTR_SEARCH_FRIENDS, "");
        }

    }
}
