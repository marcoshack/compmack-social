/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csocial.server.ws.resource;

import csocial.server.web.controller.*;
import csocial.server.ws.resource.BaseControllerME;
import csocial.server.entity.User;
import csocial.server.service.UserManager;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXB;

class resultado
{
    public boolean sucesso;
    public User usuario;
    public String cookie;
}

/**
 *
 * @author daniel
 */
public class LoginControllerME extends BaseControllerME {

    @EJB
    UserManager userManager;

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        String username, password;
        response.setContentType("text/xml;charset=UTF-8");
        resultado r = new resultado();

        username = request.getParameter("username");
        password = request.getParameter("password");

        // Autentica o usuário
        r.usuario = authenticate(username, password);

        PrintWriter out = response.getWriter();

        if ( r.usuario != null )
        {
            // Guarda na sessão
            HttpSession session = request.getSession();
            session.setAttribute("username", r.usuario.getUsername());
        }

        r.sucesso = ( r.usuario == null ) ? false : true;

        JAXB.marshal(r, out);

        out.close();
    }

    private User authenticate(String username, String password) {
        User user = userManager.findByUsername(username);

        // TODO [mhack] usar senha criptografada
        if (user != null && password.equals(user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }
}
