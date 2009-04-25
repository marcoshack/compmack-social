/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csocial.server.service;

import csocial.server.entity.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mhack
 */
@Stateless
public class UserManagerBean implements UserManagerLocal {
    @PersistenceContext(unitName = "csocial-pu")
    private EntityManager em;

    public User getUser(long id) {
        Query q = em.createQuery("SELECT u FROM User WHERE u.id = :id");
        q.setParameter("id", id);
        return (User)q.getSingleResult();
    }
}
