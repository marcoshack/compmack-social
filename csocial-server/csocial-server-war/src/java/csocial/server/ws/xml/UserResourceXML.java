package csocial.server.ws.xml;

import csocial.server.entity.User;
import csocial.server.service.UserManager;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * Interface REST Web Service no formato XML para o recurso User.
 *
 * @author Marcos Hack <marcoshack@gmail.com>
 */
@Path("user")
public class UserResourceXML {
    @Context
    private UriInfo context;

    @EJB
    private UserManager userManager;
    
    /**
     * Retorna a representacao XML do objeto User com o ID especificado.
     *
     * @return an instance of csocial.server.entity.User
     */
    @GET
    @Path("/{id}")
    @Produces("application/xml")
    public User getById(@PathParam("id") Long id) {
        return userManager.findById(id);
    }
}
