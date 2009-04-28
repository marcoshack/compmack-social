/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csocial.server.service.test;

import csocial.server.service.*;
import csocial.server.entity.User;
import csocial.server.service.test.util.ServiceLocator;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static csocial.server.service.test.util.UserUtils.createUser;

/**
 *
 * @author mhack
 */
public class UserManagerTest extends GenericManagerTest {

    private UserManager usrManager;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        usrManager = ServiceLocator.getInstance().createUserManager(getEntityManager());
    }

    /**
     * Test of findById method, of class UserManager.
     */
    @Test
    public void testSave() {
        startTransaction();
        User user = createUser("foo_save", getEntityManager());
        commit();

        assertNotNull("Nao foi possivel recuperar o usuario '", user.getId());
    }

    /**
     * Test of findById method, of class UserManager.
     */
    @Test
    public void testFindById() {
        startTransaction();
        User user = createUser("foo_findById", getEntityManager());
        commit();

        User res = usrManager.findById(user.getId());

        assertNotNull("Nao foi possivel recuperar o usuario '"
                + user.getUsername() + "'", res);

        assertEquals("IDs do usuario criado e recuperado nao batem",
                res.getId(), user.getId());
    }

    /**
     * Test of findByUsername method, of class UserManager.
     */
    @Test
    public void testFindByUsername() {
        startTransaction();
        User user = createUser("foo_findByUsername", getEntityManager());
        commit();
        
        User res = usrManager.findByUsername(user.getUsername());

        assertNotNull("Nao foi possivel recuperar o usuario '" 
                + user.getUsername() + "'", res);

        assertEquals("IDs do usuario criado e recuperado nao batem",
                res.getId(), user.getId());
    }

    /**
     * Test of findByEmail method, of class UserManager.
     */
    @Test
    public void testFindByEmail() {
        startTransaction();
        User user = createUser("foo_findByEmail", getEntityManager());
        commit();

        User u = usrManager.findByEmail(user.getEmail());

        assertNotNull("Nao foi possivel recuperar o usuario pelo endereco de "
                + "e-mail '" + user.getEmail(), u);

        assertEquals("IDs do usuario criado e recuperado nao batem",
                u.getId(), user.getId());
    }

    /**
     * Test of find method, of class UserManager.
     */
    @Test
    public void testFind() {
        startTransaction();
        User user = createUser("foo_pattern_find", getEntityManager());
        commit();

        List<User> userList = usrManager.find("pattern");

        assertNotNull("Nao foi possivel recuperar o usuario pelo endereco de "
                + "e-mail '" + user.getEmail(), userList);

        assertTrue("Apenas 1 registro deveria ser retornado",
                userList.size() == 1);

        assertTrue("IDs do usuario criado e recuperado nao batem",
                user.getId() == userList.get(0).getId());
    }
}