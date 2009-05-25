/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csocial.server.ws.resource;

import csocial.server.entity.collection.UserList;
import csocial.server.service.UserManager;
import csocial.server.web.controller.BaseController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXB;

/**
 *
 * @author mhack
 */
public class FriendshipResource extends BaseControllerME {

    @EJB
    private UserManager userManager;

    @Override
    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("application/xml;charset=UTF-8");
        PrintWriter out = res.getWriter();
        UserList userList = new UserList();
        String searchPattern = req.getParameter("search");

        if (searchPattern != null) {
            userList.addAll(userManager.find(searchPattern));
        }
        
        JAXB.marshal(userList, out);
        out.close();
    }
}
