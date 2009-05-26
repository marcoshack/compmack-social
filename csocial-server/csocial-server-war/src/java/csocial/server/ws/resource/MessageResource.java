/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csocial.server.ws.resource;

import csocial.server.entity.Message;
import csocial.server.entity.User;
import csocial.server.entity.collection.MessageList;
import csocial.server.service.MessageManager;
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
class ResultadoMensagem extends ResultadoBase
{
    @XmlElementRef
    MessageList listaMensagens;
}

/**
 *
 * @author daniel
 */
public class MessageResource extends BaseControllerME {

    @EJB
    MessageManager messageManager;
    @EJB
    UserManager userManager;

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        ResultadoMensagem rm = new ResultadoMensagem();

        rm.sucesso = false;

        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("username");

        if ( username != null )
        {
            User u = userManager.findByUsername(username);
            List<Message> mensagens = messageManager.findByOwner(u);

            rm.sucesso = true;
            rm.listaMensagens = new MessageList();
            rm.listaMensagens.addAll(mensagens);
        }

        PrintWriter out = response.getWriter();

        JAXB.marshal(rm, out);

        out.close();
    }
}
