/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csocial.server.service;

import csocial.server.entity.Message;
import csocial.server.entity.User;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Marcos Hack <marcoshack@gmail.com>
 */
public class MessageManagerTest extends GenericManagerTest {

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
        User author = createUser("foo");
        User owner = createUser("bar");
        Message m = createMessage(author, owner, "save() method test message");
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
        User author = createUser("foo");
        User owner = createUser("bar");
        createMessage(author, owner, "findByOwner() method test message");
        commit();

        List<Message> msgList = createMessageManager().findByOwner(owner);
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
        User author = createUser("foo");
        User owner = createUser("bar");
        createMessage(author, owner, "findBySender() method test message");
        commit();

        List<Message> msgList = createMessageManager().findBySender(author);
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
        User author = createUser("foo");
        User owner = createUser("bar");
        String pattern = "testFind pattern";
        createMessage(author, owner, "find() method test message (" + pattern
                + ")");
        commit();

        List<Message> msgList = createMessageManager().find(author, pattern);
        assertTrue("Nenhuma mensagem encontrada com padrao " + pattern 
                + "para o usuario " + owner, msgList.size() == 1);
    }
}