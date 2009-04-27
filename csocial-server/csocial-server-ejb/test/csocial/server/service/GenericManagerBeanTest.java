package csocial.server.service;

import csocial.server.entity.Message;
import csocial.server.entity.MessageStatus;
import csocial.server.entity.User;
import csocial.server.service.ejb3.MessageManagerBean;
import csocial.server.service.ejb3.UserManagerBean;
import java.util.GregorianCalendar;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;

/**
 * Classe abstrata com codigo comum para os testes de *ManagerBean. Cada teste
 * deve chamar o metodo startTransaction() antes de chamar quaisquer metodos de
 * persistencia do ManagerBean e o metodo commit() para finalizar a transacao e
 * persistir os dados.
 *
 * @author Marcos Hack <marcoshack@gmail.com>
 */
public abstract class GenericManagerBeanTest {
    private EntityManager em;
    protected static Logger log;

    public GenericManagerBeanTest() {
        log = Logger.getLogger(this.getClass().getName());
    }

    /**
     * Cria o EntityManager para ser utilizado pelo ManagerBean e apaga os dados
     * de todas as tabelas para que dados de testes anteriores nao afetem o
     * teste a ser executado.
     *
     * @author Marcos Hack <marcoshack@gmail.com>
     */
    @Before
    public void setUp() {
        // TODO [mhack] nao recriar a factory em cada execucao do teste
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("csocial-test-pu");
        em = emf.createEntityManager();
    }

    /**
     * Finaliza o EntityManager ao final de cada teste.
     */
    @After
    public void tearDown() {
        em.close();
    }

    /**
     * Inicia a transacao no EntityBean. Deve ser chamado antes de chamar
     * quaisquer metodos do ManagerBean que persistam dados atraves do
     * EntityManager.
     *
     * @author Marcos Hack <marcoshack@gmail.com>
     */
    protected void startTransaction() {
        em.getTransaction().begin();
    }

    /**
     * Persiste os objetos da transacao no banco de dados.
     *
     * @author Marcos Hack <marcoshack@gmail.com>
     */
    protected void commit() {
        em.getTransaction().commit();
    }

    /**
     * Cria uma instancia do session bean MessageManager.
     *
     * @author Marcos Hack <marcoshack@gmail.com>
     * @return MessageManager
     */
    protected MessageManager createMessageManager() {
        MessageManagerBean manager = new MessageManagerBean();
        manager.setEntityManager(em);
        return manager;
    }

    /**
     * Cria uma instancia do session bean UserManager.
     *
     * @author Marcos Hack <marcoshack@gmail.com>
     * @return UserManager
     */
    protected UserManager createUserManager() {
        UserManagerBean manager = new UserManagerBean();
        manager.setEntityManager(em);
        return manager;
    }

    /**
     * Metodo auxiliar para criar um objeto Message de teste. Utiliza 'message'
     * como prefixo dos campos de texto obrigatorios. Este metodo nao abre uma
     * nova transacao logo deve ser chamado dentro do contexto de uma transacao
     * ativa previsamente iniciada.
     *
     * @param author
     * @param owner
     * @param message
     * @return
     */
    protected Message createMessage(User author, User owner, String msgPrefix) {
        Message m = new Message();
        m.setAuthor(author);
        m.setOwner(owner);
        m.setSubject("Subject of " + msgPrefix);
        m.setText("Text of " + msgPrefix);
        m.setPostDate(GregorianCalendar.getInstance().getTime());
        m.setStatus(MessageStatus.NEW);

        MessageManager msgManager = createMessageManager();
        msgManager.save(m);

        return m;
    }

    /**
     * Metodo auxiliar para criar e persistir um objeto User de teste. Utiliza
     * 'username' como prefixo para os campos obrigatorios. Este metodo nao abre
     * uma nova transacao logo deve ser chamado dentro do contexto de uma
     * transacao ativa previsamente iniciada.
     *
     * @param username
     * @return
     */
    protected User createUser(String username) {
        User u = new User();
        u.setUsername(username);
        u.setRealName(username + " Something");
        u.setPassword(username + "_password");
        u.setEmail(username + "@example.com");
        u.setBirthday(GregorianCalendar.getInstance().getTime());

        UserManager userManager = createUserManager();
        userManager.save(u);

        return u;
    }
}
