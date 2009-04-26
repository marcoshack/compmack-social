/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csocial.server.service;

import csocial.server.entity.User;
import java.util.List;
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

    public void save(User u) throws UserAlreadyExistException {
        if (u.getId() == null) {
            Query q = em.createQuery("SELECT u FROM User u WHERE "
                    + "u.username = :username "
                    + "OR u.email = :email");

            if (q.getSingleResult() != null) {
                throw new UserAlreadyExistException();
            }
        }

        em.persist(u);
    }

    public User getUser(long id) {
        Query q = em.createQuery("SELECT u FROM User u WHERE u.id = :id");
        q.setParameter("id", id);
        return (User) q.getSingleResult();
    }

    public User getByUsername(String username) {
        Query q = em.createQuery("SELECT u FROM User u WHERE u.username = "
                + ":username");
        
        q.setParameter("username", username);
        return (User) q.getResultList();
    }

    public User getByEmail(String email) {
        Query q = em.createQuery("SELECT u FROM User u WHERE u.email = :email");
        q.setParameter("email", email);
        return (User) q.getResultList();
    }

    public List<User> find(String pattern) {
        Query q = em.createQuery("SELECT u FROM User u WHERE "
                + "u.username LIKE '%:pattern%' "
                + "OR u.email LIKE '%:pattern%' "
                + "OR u.realName LIKE '%:pattern%' "
                + "ORDER BY u.username");
        
        q.setParameter("pattern", pattern);
        return q.getResultList();
    }
}
