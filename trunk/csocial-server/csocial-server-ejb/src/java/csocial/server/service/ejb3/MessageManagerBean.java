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

/**
 *
 * @author mhack
 */
@Stateless
public class MessageManagerBean extends GenericManagerBean<Message, Long>
        implements MessageManager
{

    public List<Message> findByOwner(User u) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Message> findBySender(User u) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Message> find(User user, String pattern) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
