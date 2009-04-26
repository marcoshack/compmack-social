/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csocial.server.service;

import csocial.server.entity.Message;
import csocial.server.entity.MessageStatus;
import csocial.server.entity.User;
import csocial.server.service.ejb3.MessageManagerBean;
import java.util.GregorianCalendar;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mhack
 */
public class MessageManagerBeanTest {
    private EntityManager em;
    private static Logger log = Logger.getLogger(MessageManagerBeanTest.class.getName());

    @Before
    public void setUp() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("csocial-test-pu");
        em = emf.createEntityManager();
    }

    @After
    public void tearDown() {
        em.close();
    }

    /**
     * Test of save method, of class MessageManagerBean.
     */
    @Test
    public void testSave() {
        User author = new User();
        author.setUsername("Foo");
        author.setPassword("fooPassword");
        author.setEmail("foo@example.com");

        User owner  = new User();
        owner.setUsername("Bar");
        owner.setPassword("barPassword");
        owner.setEmail("bar@example.com");

        Message m = new Message();
        m.setAuthor(author);
        m.setOwner(owner);
        m.setSubject("Subject of save test message");
        m.setText("Text of save test message");
        m.setPostDate(GregorianCalendar.getInstance().getTime());
        m.setStatus(MessageStatus.NEW);
        
        MessageManager msgManager = createMessageManager();
        msgManager.save(m);

        log.info(m.toString());

        assertNotNull(m.getId());
    }

    private MessageManager createMessageManager() {
        MessageManagerBean manager = new MessageManagerBean();
        manager.setEntityManager(em);
        return manager;
    }
}