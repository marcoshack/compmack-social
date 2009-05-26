/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csocial.server.ws.resource;

import csocial.server.entity.collection.MessageList;
import csocial.server.service.UserManager;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "resultado")
class ResultadoMensagem extends ResultadoBase
{
    @XmlElementRef
    MessageList listaFriendship;
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
        ResultadoMensagem rm = new ResultadoMensagem();
        rm.sucesso = false;

        

    }

}
