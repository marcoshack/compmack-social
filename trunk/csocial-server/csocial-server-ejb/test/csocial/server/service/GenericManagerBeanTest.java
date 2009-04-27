package csocial.server.service;

import csocial.server.service.ejb3.MessageManagerBean;
import csocial.server.service.ejb3.UserManagerBean;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;

/**
 *
 * @author Marcos Hack <marcoshack@gmail.com>
 */
public abstract class GenericManagerBeanTest {

    private EntityManager em;
    protected static Logger log;

    public GenericManagerBeanTest() {
        log = Logger.getLogger(this.getClass().getName());
    }

    @Before
    public void setUp() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("csocial-test-pu");
        em = emf.createEntityManager();

        startTransaction();
        em.createQuery("DELETE FROM User").executeUpdate();
        em.createQuery("DELETE FROM Message").executeUpdate();
        commit();
    }

    @After
    public void tearDown() {
        em.close();
    }

    protected void startTransaction() {
        em.getTransaction().begin();
    }

    protected void commit() {
        em.getTransaction().commit();
    }

    protected MessageManager createMessageManager() {
        MessageManagerBean manager = new MessageManagerBean();
        manager.setEntityManager(em);
        return manager;
    }

    protected UserManager createUserManager() {
        UserManagerBean manager = new UserManagerBean();
        manager.setEntityManager(em);
        return manager;
    }
}
