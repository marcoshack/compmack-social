/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csocial.server.service.ejb3;

import csocial.server.service.*;
import csocial.server.entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author mhack
 */
@Stateless
public class UserManagerBean extends GenericManagerBean<User, Long> 
        implements UserManager
{
    public User findByUsername(String username) {
        Query q = getEntityManager().createQuery("FROM User u WHERE "
                + "u.username = :username");

        q.setParameter("username", username);
        return getSingleResult(q);
    }

    public User findByEmail(String email) {
        Query q = getEntityManager().createQuery("FROM User u WHERE "
                + "u.email = :email");

        q.setParameter("email", email);
        return getSingleResult(q);
    }

    public List<User> find(String pattern) {
        // TODO [mhack] nao consegui fazer funcionar a query usando parametros,
        //      tanto ordinais como nomeados, quando usando o "...LIKE '%?%'..."
        Query q = getEntityManager().createQuery("FROM User u WHERE "
                + "u.username LIKE '%" + pattern + "%' "
                + "OR u.email LIKE '%" + pattern + "%' "
                + "OR u.realName LIKE '%" + pattern + "%' "
                + "ORDER BY u.username");

        return q.getResultList();
    }
}
