/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csocial.server.ws;

import csocial.server.entity.User;
import csocial.server.service.UserManager;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author mhack
 */

@Path("user")
public class UserResource {
    @Context
    private UriInfo context;

    @EJB
    private UserManager userManager;

    /**
     * 
     * @return an instance of csocial.server.entity.User
     */
    @GET
    @Path("/{id}")
    @Produces("application/xml")
    public String getXml(@PathParam("id") Long id) {
        // TODO verificar se o Jersey nao usa JAXB, nao funcionou pro User.
        // Codigo de teste
        User u = userManager.findById(id);
        StringBuilder sb = new StringBuilder("<user");
        sb.append(" id=\"").append(u.getId()).append("\"");
        sb.append(" userName=\"").append(u.getUsername()).append("\"");
        sb.append(" email=\"").append(u.getEmail()).append("\"");
        sb.append(" realName=\"").append(u.getRealName()).append("\"");
        sb.append(" birthDay=\"").append(u.getBirthday()).append("\"");
        sb.append(" nickName=\"").append(u.getNickName()).append("\"");
        sb.append(" />");
        return sb.toString();
    }

    /**
     * 
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    /*
    @PUT
    @Consumes("application/xml")
    public void putXml(User u) {
    }
    */
}
