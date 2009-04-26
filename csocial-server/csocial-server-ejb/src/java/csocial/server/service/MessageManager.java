/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csocial.server.service;

import csocial.server.entity.Message;
import csocial.server.entity.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author mhack
 */
@Local
public interface MessageManager extends GenericManager<Message, Long> {

    /**
     * Busca lista de mensagens pelo dono.
     *
     * @param Dono da mensagem (destinatario)
     * @return Lista de mensagens.
     */
    public List<Message> findByOwner(User u);

    /**
     * Busca lista de mensagens enviadas pelo usuario informado como parametro.
     *
     * @param Usario cuja lista de mensagens enviadas deseja-se recuperar.
     * @return Lista de mensagens
     */
    public List<Message> findBySender(User u);

    /**
     * Busca lista de mensagens que podem ser visualizadas pelo usuario 'user'.
     */
    public List<Message> find(User user, String pattern);
}
