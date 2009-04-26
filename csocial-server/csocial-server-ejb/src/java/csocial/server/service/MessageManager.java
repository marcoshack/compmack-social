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
public interface MessageManager {

    /**
     * Insere ou atualiza (caso ja exista) a mensagem no banco de dados.
     *
     * @param mensagem
     */
    public void save(Message m);

    /**
     * Busca mensagem pelo ID informado.
     *
     * @param ID da mensagem.
     * @return Mensagem ou nulo caso nao exista mensagem com esse ID.
     */
    public Message getMessage(Long id);

    /**
     * Busca lista de mensagens pelo dono.
     *
     * @param Dono da mensagem (destinatario)
     * @return Lista de mensagens.
     */
    public List<Message> getByOwner(User u);

    /**
     * Busca lista de mensagens enviadas pelo usuario informado como parametro.
     *
     * @param Usario cuja lista de mensagens enviadas deseja-se recuperar.
     * @return Lista de mensagens
     */
    public List<Message> getBySender(User u);

    /**
     * Busca lista de mensagens que podem ser visualizadas pelo usuario 'user'.
     */
    public List<Message> find(User user, String pattern);
}
