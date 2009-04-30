/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csocial.server.service.ejb3;

import csocial.server.service.*;
import csocial.server.entity.Message;
import csocial.server.entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author mhack
 */
@Stateless
public class MessageManagerBean extends GenericManagerBean<Message, Long>
        implements MessageManager
{

    public List<Message> findByOwner(User u) {
        Query q = getEntityManager().createQuery("FROM Message m WHERE "
                + "m.owner = :user");
        q.setParameter("user", u);
        return q.getResultList();
    }

    public List<Message> findBySender(User u) {
        Query q = getEntityManager().createQuery("FROM Message m WHERE "
                + "m.author = :user");
        q.setParameter("user", u);
        return q.getResultList();
    }

    public List<Message> find(User user, String pattern) {
        // TODO [mhack] valor de 'pattern' como parametro, nao consequi usando LIKE
        Query q = getEntityManager().createQuery("FROM Message m WHERE "
                + "m.author = :user "
                + "AND (m.subject LIKE '%" + pattern + "%' "
                +      "OR m.text LIKE '%" + pattern + "%')");
        q.setParameter("user", user);
        return q.getResultList();
    }
}
