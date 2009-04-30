/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csocial.server.service;

import csocial.server.entity.Friendship;
import csocial.server.entity.FriendshipRequest;
import csocial.server.entity.User;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Marcos Hack <marcoshack@gmail.com>
 */
@Remote
public interface FriendshipManager extends GenericManager<Friendship, Long> {

    /**
     * Retorna o objeto Friendship que representa a relacao de amizade entre os
     * usuarios 'owner' e 'friend'.
     *
     * @param owner
     * @param friend
     * @return
     */
    public Friendship findFriendship(User owner, User friend);

    /**
     * Retorna lista de amigos de 'user'.
     *
     * @param Usuario que deseja-se resgatar a lista de amigos
     * @return Lista de amigos de 'user'
     */
    public List<User> getFriendList(User user);

    /**
     * Adiciona a requisicao de amizade.
     *
     * @param Requisicao a ser adicionada
     */
    public void addFriendshipRequest(FriendshipRequest request)
        throws FriendshipRequestAlreadyExistException;

    /**
     * Aceita a requisicao de amizade.
     *
     * Cria duas entradas de relacao de amizade, request.from (owner) e
     * request.to (friend), e o contrario request.to (owner) e request.from
     * (friend). Isso eh necessario para permitir que cada usuario adicione
     * atributos privados (somente o usuario tem acesso a tais atributos) em uma
     * relacao de amizade.
     *
     * @param Requisicao a ser aceita
     */
    public void acceptFriendshipRequest(FriendshipRequest request);

    /**
     * Rejeita a requisicao de amizade.
     *
     * @param Requisicao a ser rejeitada.
     */
    public void rejectFriendshipRequest(FriendshipRequest request);

    /**
     * Marca a requisicao de amizade como "ciente" (ACK), o que significa que o
     * usuario que fez a requisicao esta ciente da situacao desta, seja por ter
     * sido aceita ou rejeitada.
     *
     * @param Requisicao a ser marcada como ciente.
     */
    public void ackFriendshipRequest(FriendshipRequest request);

    /**
     * Retorna lista de requisicoes de amizade em aberto enviadas pelo usuario
     * 'user'. Em aberto significa que o usuario alvo da amizade ainda nao
     * aceitou ou rejeitou a requisicao.
     *
     * @param user
     * @return
     */
    public List<FriendshipRequest> getSentFriendshipRequestList(User user);

    /**
     * Retorna lista de requisicoes de amizade pendentes do usuario 'user'.
     * Pendente significa requisicoes que o usuario 'user' precisa responder.
     *
     * @param user
     * @return
     */
    public List<FriendshipRequest> getPendingFriendshipRequestList(User user);
}
