/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csocial.server.service;

import csocial.server.entity.User;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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
        usrManager = createUserManager();
    }

    /**
     * Test of findById method, of class UserManager.
     */
    @Test
    public void testSave() {
        startTransaction();
        User user = createUser("foo_save");
        commit();

        assertNotNull("Nao foi possivel recuperar o usuario '", user.getId());
    }

    /**
     * Test of findById method, of class UserManager.
     */
    @Test
    public void testFindById() {
        startTransaction();
        User user = createUser("foo_findById");
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
        User user = createUser("foo_findByUsername");
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
        User user = createUser("foo_findByEmail");
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
        User user = createUser("foo_pattern_find");
        commit();

        List<User> userList = usrManager.find("pattern");

        assertNotNull("Nao foi possivel recuperar o usuario pelo endereco de "
                + "e-mail '" + user.getEmail(), userList);

        assertEquals("Apenas 1 registro deveria ser retornado", 1,
                userList.size());

        assertEquals("IDs do usuario criado e recuperado nao batem",
                user.getId(), userList.get(0).getId());
    }
}