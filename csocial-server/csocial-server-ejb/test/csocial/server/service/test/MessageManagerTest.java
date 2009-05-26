/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csocial.server.service.test;

import csocial.server.entity.Message;
import csocial.server.entity.User;
import csocial.server.service.MessageManager;
import csocial.server.service.test.util.MessageUtils;
import csocial.server.service.test.util.ServiceLocator;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static csocial.server.service.test.util.UserUtils.createUser;

/**
 *
 * @author Marcos Hack <marcoshack@gmail.com>
 */
public class MessageManagerTest extends GenericManagerTest {

    private MessageManager msgManager;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        msgManager = ServiceLocator.getInstance().createMessageManager(getEntityManager());
    }

    /**
     * Teste do metodo save() da classe MessageManagerBean. Cria dois usuarios
     * para associar como author e owner da mensagem e verifica se a mensagem
     * foi devidamente salva verificando se um ID foi atribuito para mensagem.
     *
     * @author Marcos Hack <marcoshack@gmail.com>
     */
    @Test
    public void testSave() {
        startTransaction();
        User author = createUser("foo_save", getEntityManager());
        User owner = createUser("bar_save", getEntityManager());
        Message m = MessageUtils.createMessage(author, owner,
                "save() method test message", getEntityManager());
        commit();
        
        assertNotNull("A mensagem nao foi salva corretamente. ID inexistente",
                m.getId());
    }

    /**
     * Teste do metodo findByOwner() da class MessageManagerBean. Cria e salva
     * uma mensagem de teste e tenta recupera-la atraves do metodo findByOwner()
     * do MessageManager.
     *
     * @author Marcos Hack <marcoshack@gmail.com>
     */
    @Test
    public void testFindByOwner() {
        startTransaction();
        User author = createUser("foo_findbyowner", getEntityManager());
        User owner = createUser("bar_findbyowner", getEntityManager());
        MessageUtils.createMessage(author, owner,
                "findByOwner() method test message", getEntityManager());
        commit();

        List<Message> msgList = msgManager.findByOwner(owner);
        assertTrue("Nenhuma mensagem encontrada para " + owner,
                msgList.size() == 1);
    }

    /**
     * Teste do metodo findByOwner() da class MessageManagerBean. Cria e salva
     * uma mensagem de teste e tenta recupera-la atraves do metodo findByOwner()
     * do MessageManager.
     *
     * @author Marcos Hack <marcoshack@gmail.com>
     */
    @Test
    public void testFindBySender() {
        startTransaction();
        User author = createUser("foo_findbysender", getEntityManager());
        User owner = createUser("bar_findbysender", getEntityManager());
        MessageUtils.createMessage(author, owner,
                "findBySender() method test message", getEntityManager());
        commit();

        List<Message> msgList = msgManager.findBySender(author);
        assertTrue("Nenhuma mensagem encontrada com autor " + author,
                msgList.size() == 1);
    }

    /**
     * Teste do metodo find() da class MessageManagerBean. Cria e salva
     * uma mensagem de teste e tenta recupera-la passando o dono (owner) e parte
     * do texto da mensagem (pattern) para o metodo find().
     *
     * @author Marcos Hack <marcoshack@gmail.com>
     */
    @Test
    public void testFind() {
        startTransaction();
        User author = createUser("foo_find", getEntityManager());
        User owner = createUser("bar_find", getEntityManager());
        String pattern = "testFind pattern";
        MessageUtils.createMessage(author, owner, "find() method test message ("
                + pattern + ")", getEntityManager());
        commit();

        List<Message> msgList = msgManager.find(author, pattern);
        assertTrue("Nenhuma mensagem encontrada com padrao " + pattern 
                + "para o usuario " + owner, msgList.size() == 1);
    }

    @Test
    public void testNoResult() {
        startTransaction();
        User author = createUser("noResultUser", getEntityManager());
        commit();

        List<Message> msgList = msgManager.findByOwner(author);

        assertEquals("Message list is not empty", 0, msgList.size());
    }
}