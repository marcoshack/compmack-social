/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csocial.server.service;

import csocial.server.entity.Message;
import csocial.server.entity.MessageStatus;
import csocial.server.entity.User;
import java.util.GregorianCalendar;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mhack
 */
public class MessageManagerBeanTest extends GenericManagerBeanTest {

    /**
     * Test of save method, of class MessageManagerBean.
     */
    @Test
    public void testSave() {
        startTransaction();

        User author = new User();
        author.setUsername("Foo");
        author.setRealName("Foo");
        author.setPassword("fooPassword");
        author.setEmail("foo@example.com");

        User owner = new User();
        owner.setUsername("Bar");
        owner.setRealName("Bar");
        owner.setPassword("barPassword");
        owner.setEmail("bar@example.com");

        UserManager userManager = createUserManager();
        userManager.save(owner);
        userManager.save(author);

        Message m = new Message();
        m.setAuthor(author);
        m.setOwner(owner);
        m.setSubject("Subject of save test message");
        m.setText("Text of save test message");
        m.setPostDate(GregorianCalendar.getInstance().getTime());
        m.setStatus(MessageStatus.NEW);

        MessageManager msgManager = createMessageManager();
        msgManager.save(m);

        commit();

        log.info(m.toString());

        assertNotNull(m.getId());
    }
}