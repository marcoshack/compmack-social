package csocial.server.service.test.util;

import csocial.server.entity.User;
import csocial.server.service.UserManager;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;

/**
 *
 * @author Marcos Hack <marcoshack@gmail.com>
 */
public class UserUtils {

    /**
     * Metodo auxiliar para criar e persistir um objeto User de teste. Utiliza
     * 'username' como prefixo para os campos obrigatorios. Este metodo nao abre
     * uma nova transacao logo deve ser chamado dentro do contexto de uma
     * transacao ativa previsamente iniciada.
     *
     * @param username Nome utilizado como 'username' e prefixo dos demais campos
     * @param em EntityManager a ser utilizada para persistir o usuario criado
     * @return
     */
    public static User createUser(String username, EntityManager em) {
        User u = new User();
        u.setUsername(username);
        u.setNickName(username + " nick");
        u.setRealName(username + " surename");
        u.setPassword(username + "_password");
        u.setEmail(username + "@example.com");
        u.setBirthday(GregorianCalendar.getInstance().getTime());

        UserManager userManager = ServiceLocator.getInstance().createUserManager(em);
        userManager.save(u);

        return u;
    }
}
