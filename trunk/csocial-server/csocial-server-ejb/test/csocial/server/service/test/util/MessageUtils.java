package csocial.server.service.test.util;

import csocial.server.entity.Message;
import csocial.server.entity.MessageStatus;
import csocial.server.entity.User;
import csocial.server.service.MessageManager;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;

/**
 *
 * @author Marcos Hack <marcoshack@gmail.com>
 */
public class MessageUtils {

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
    public static Message createMessage(User author, User owner,
            String msgPrefix, EntityManager em) 
    {
        Message m = new Message();
        m.setAuthor(author);
        m.setOwner(owner);
        m.setSubject("Subject of " + msgPrefix);
        m.setText("Text of " + msgPrefix);
        m.setPostDate(GregorianCalendar.getInstance().getTime());
        m.setStatus(MessageStatus.NEW);

        MessageManager msgManager = ServiceLocator.getInstance().createMessageManager(em);
        msgManager.save(m);

        return m;
    }

}
