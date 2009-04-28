package csocial.server.service.test;

import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;

/**
 * Classe abstrata com codigo comum para os testes de *ManagerBean. Cada teste
 * deve chamar o metodo startTransaction() antes de chamar quaisquer metodos de
 * persistencia do ManagerBean e o metodo commit() para finalizar a transacao e
 * persistir os dados.
 *
 * @author Marcos Hack <marcoshack@gmail.com>
 */
@Ignore
public abstract class GenericManagerTest {

    private EntityManager em;
    private static EntityManagerFactory emf;
    protected static Logger log;

    public GenericManagerTest() {
        log = Logger.getLogger(this.getClass().getName());
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        emf = Persistence.createEntityManagerFactory("csocial-test-pu");
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
     * Retorna a instancia de EntityManager criada para o teste.
     *
     * @return EntityManager
     */
    protected EntityManager getEntityManager() {
        return em;
    }
}
