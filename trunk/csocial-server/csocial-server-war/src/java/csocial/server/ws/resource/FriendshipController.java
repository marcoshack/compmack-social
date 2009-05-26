/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csocial.server.ws.resource;

import csocial.server.entity.User;
import csocial.server.entity.collection.UserList;
import csocial.server.service.UserManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "resultado")
class ResultadoSearchFriendship extends ResultadoBase
{
    @XmlElementRef
    UserList  listaFriendship;
}

/**
 *
 * @author esantos
 */
public class FriendshipController extends BaseControllerME {

    @EJB
    private csocial.server.service.FriendshipManager friendshipManeger;

    @EJB
    UserManager userManager;

    @Override
    protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ResultadoSearchFriendship rm = new ResultadoSearchFriendship();
        rm.sucesso = false;

        HttpSession session = req.getSession();
        String username = (String)session.getAttribute("username");

        if ( username != null )
        {
            User u = userManager.findByUsername(username);
            List<User> friends = friendshipManeger.getFriendList(u);

            rm.sucesso = true;
            rm.listaFriendship = new UserList();
            rm.listaFriendship.addAll(friends);
        }

        PrintWriter out = res.getWriter();

        JAXB.marshal(rm, out);

        out.close();
    }

}
