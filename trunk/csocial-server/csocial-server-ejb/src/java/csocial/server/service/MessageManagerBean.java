/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csocial.server.service;

import csocial.server.entity.Message;
import csocial.server.entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mhack
 */
@Stateless
public class MessageManagerBean implements MessageManagerLocal {
    @PersistenceContext(name = "csocial-pu")
    protected EntityManager em;

    public void save(Message m) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Message getMessage(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Message> getByOwner(User u) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Message> getBySender(User u) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Message> find(User user, String pattern) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
