/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csocial.server.service;

import csocial.server.entity.Message;
import csocial.server.entity.User;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mhack
 */
@Ignore
public class MessageManagerBeanTest {
    private EntityManager em;

    @Before
    public void setUp() {
        HashMap map = new HashMap();
        map.put("javax.persistence.transactionType", "RESOURCE_LOCAL");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("csocial-pu", map);
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
        System.out.println("save");
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
        
        MessageManagerBean instance = new MessageManagerBeanWrapper(null);
        instance.save(m);
        assertNotNull(m.getId());
    }

    /**
     * Test of getMessage method, of class MessageManagerBean.
     */
    @Test
    public void testGetMessage() {
        System.out.println("getMessage");
        Long id = null;
        MessageManagerBean instance = new MessageManagerBeanWrapper(null);
        Message expResult = null;
        Message result = instance.getMessage(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getByOwner method, of class MessageManagerBean.
     */
    @Test
    public void testGetByOwner() {
        System.out.println("getByOwner");
        User u = null;
        MessageManagerBean instance = new MessageManagerBeanWrapper(null);
        List<Message> expResult = null;
        List<Message> result = instance.getByOwner(u);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBySender method, of class MessageManagerBean.
     */
    @Test
    public void testGetBySender() {
        System.out.println("getBySender");
        User u = null;
        MessageManagerBean instance = new MessageManagerBeanWrapper(null);
        List<Message> expResult = null;
        List<Message> result = instance.getBySender(u);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of find method, of class MessageManagerBean.
     */
    @Test
    public void testFind() {
        System.out.println("find");
        User user = null;
        String pattern = "";
        MessageManagerBean instance = new MessageManagerBeanWrapper(null);
        List<Message> expResult = null;
        List<Message> result = instance.find(user, pattern);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    public class MessageManagerBeanWrapper extends MessageManagerBean {
        public MessageManagerBeanWrapper(EntityManager em) {
            this.em = em;
        }
    }
}