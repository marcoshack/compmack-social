package csocial.server.service.test.util;

import csocial.server.service.FriendshipManager;
import csocial.server.service.MessageManager;
import csocial.server.service.UserManager;
import csocial.server.service.ejb3.FriendshipManagerBean;
import csocial.server.service.ejb3.MessageManagerBean;
import csocial.server.service.ejb3.UserManagerBean;
import javax.persistence.EntityManager;

/**
 * Classe auxiliar para localizacao de servicos utilizados nos testes.
 *
 * Alguns servicos quando utilizados fora do container J2EE precisam ser
 * inicializados de forma especial, por exemplo unidades de persistencia
 * (Persistence Unit) do JPA.
 *
 * @author Marcos Hack <marcoshack@gmail.com>
 */
public class ServiceLocator {
    private static ServiceLocator instance;

    private ServiceLocator() {}

    /**
     * Obtem uma instancia de ServiceLocator.
     *
     * O ServiceLocator implementa o padrao Singleton e sua instancia deve ser
     * obtida atraves desse metodo.
     * 
     * @return Instancia de ServiceLocator.
     */
    public static ServiceLocator getInstance() {
        if (instance == null) {
            synchronized(ServiceLocator.class) {
                instance = new ServiceLocator();
            }
        }
        return instance;
    }

    /**
     * Cria uma instancia da implementacao do servico MessageManager e configura
     * o EntityManager passado no parametro 'em'.
     *
     * @author Marcos Hack <marcoshack@gmail.com>
     * @param em EntityManager a ser configurada na instancia do MessageManager
     * @return MessageManager
     */
    public MessageManager createMessageManager(EntityManager em) {
        MessageManagerBean manager = new MessageManagerBean();
        manager.setEntityManager(em);
        return manager;
    }

    /**
     * Cria uma instancia da implementacao do servico UserManager e configura o
     * EntityManager passado no parametro 'em'.
     *
     * @author Marcos Hack <marcoshack@gmail.com>
     * @param em EntityManager a ser configurada na instancia do UserManager
     * @return UserManager
     */
    public UserManager createUserManager(EntityManager em) {
        UserManagerBean manager = new UserManagerBean();
        manager.setEntityManager(em);
        return manager;
    }

    /**
     * Cria uma instancia da implementacao do servico FriendshipManager e
     * configura o EntityManager passado no parametro 'em'.
     *
     * @author Marcos Hack <marcoshack@gmail.com>
     * @param em EntityManager a ser configurada na instancia do UserManager
     * @return UserManager
     */
    public FriendshipManager createFriendshipManager(EntityManager em) {
        FriendshipManagerBean manager = new FriendshipManagerBean();
        manager.setEntityManager(em);
        return manager;
    }

}
