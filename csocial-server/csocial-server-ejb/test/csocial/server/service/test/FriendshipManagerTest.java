/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csocial.server.service.test;

import csocial.server.entity.Friendship;
import csocial.server.entity.FriendshipRequest;
import csocial.server.entity.FriendshipRequestStatus;
import csocial.server.entity.User;
import csocial.server.service.FriendshipManager;
import csocial.server.service.test.util.ServiceLocator;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static csocial.server.service.test.util.UserUtils.createUser;

/**
 *
 * @author mhack
 */
public class FriendshipManagerTest extends GenericManagerTest {
    private FriendshipManager fsManager;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        fsManager = ServiceLocator.getInstance().createFriendshipManager(
                getEntityManager());
    }

    /**
     * Test of findFriendship method, of class FriendshipManager.
     */
    @Test
    public void testFindFriendship() {
        // PRE: Criar uma relacao de amizade entre 'owner' e 'friend'
        startTransaction();
        User owner = createUser("owner_findFriendship", getEntityManager());
        User friend = createUser("friend_findFriendship", getEntityManager());
        Friendship f = new Friendship();
        f.setOwner(owner);
        f.setFriend(friend);
        f.setStartDate(GregorianCalendar.getInstance().getTime());
        fsManager.save(f);
        commit();

        // TESTE: Buscar por relacao de amizade entre 'owner' e 'friend'
        Friendship res = fsManager.findFriendship(owner, friend);

        // POS: Relacao encontrada
        assertNotNull("Relacao de amizade nao encontrada", res);
    }

    /**
     * Test of getFriendList method, of class FriendshipManager.
     */
    @Test
    public void testGetFriendList() {
        // PRE: Criar 'nFriends' relacoes de amizade do usuario 'owner'
        startTransaction();
        int nFriends = 5;
        User owner = createUser("owner_friendList", getEntityManager());
        for (int i = 0; i < nFriends; i++) {
            User f = createUser("friend" + i + "_friendList",
                    getEntityManager());
            Friendship fs = new Friendship();
            fs.setOwner(owner);
            fs.setFriend(f);
            fs.setStartDate(GregorianCalendar.getInstance().getTime());
            fsManager.save(fs);
        }
        commit();

        // TESTE: Resgatar lista de amigos
        List<User> friendList = fsManager.getFriendList(owner);

        // POS: Lista contendo os 'nFriends' amigos de 'owner'
        assertNotNull("Nao foi possivel resgatar lista de amigos", friendList);
        assertTrue("Tamanho incorreto da lista de amigos, esperava "
                + nFriends + " mas obtive " + friendList.size(), 
                nFriends == friendList.size());
    }

    /**
     * Test of addFriendshipRequest method, of class FriendshipManager.
     */
    @Test
    public void testAddFriendshipRequest() throws Exception {
        // PRE: Criar requisicao de amizade entre os usuarios 'from' e 'to'
        startTransaction();
        User from = createUser("from_addFriendshipReq", getEntityManager());
        User to = createUser("to_addFriendshipReq", getEntityManager());
        String msg = "testAddFriendshipRequest() request message";
        FriendshipRequest fsReq = new FriendshipRequest(from, to, msg);
        
        // TESTE: Adicionar requisicao de amizade
        fsManager.addFriendshipRequest(fsReq);
        commit();

        // POS: 1. ID da requisicao nao nula o que mostra que a mesma foi salva
        //      2. 'from' e 'to' atribuidos corretamente
        //      3. Status da requisicao igual a PENDING
        assertNotNull("Requisicao de amizade nao foi salva corretamente, ID " +
                " inexistente", fsReq.getId());

        assertEquals("Originador da requisicao de amizade incorreto",
                from, fsReq.getFromUser());

        assertEquals("Destino da requisicao de amizade incorreto",
                to, fsReq.getToUser());

        assertEquals("Estado da requisicao incorreto, esperava "
                + FriendshipRequestStatus.PENDING + " mas obtive "
                + fsReq.getStatus(), 
                FriendshipRequestStatus.PENDING, fsReq.getStatus());
    }

    /**
     * Test of acceptFriendshipRequest method, of class FriendshipManager.
     */
    @Test
    public void testAcceptFriendshipRequest() throws Exception {
        // PRE: Adicionar requisicao de amizade entre os usuarios 'from' e 'to'
        startTransaction();
        User from = createUser("from_acceptFsReq", getEntityManager());
        User to = createUser("to_acceptFsReq", getEntityManager());
        String msg = "testAcceptFriendshipRequest() request message";
        FriendshipRequest fsReq = new FriendshipRequest(from, to, msg);
        fsManager.addFriendshipRequest(fsReq);

        // TESTE: Aceitar requisicao
        fsManager.acceptFriendshipRequest(fsReq);
        commit();

        // POS: 1. Requisicao de amizade com status ACCEPTED
        //      2. Relacao de amizade bidirecional entre 'from' e 'to' exitir
        assertEquals("Estado da requisicao incorreto, esperava "
                + FriendshipRequestStatus.ACCEPTED + " mas obtive "
                + fsReq.getStatus(),
                FriendshipRequestStatus.ACCEPTED, fsReq.getStatus());

        assertNotNull("Friendship 'from' -> 'to' nao encontrada",
                fsManager.findFriendship(from, to));

        assertNotNull("Frienship 'to' -> 'from' nao encontrada",
                fsManager.findFriendship(to, from));
    }

    /**
     * Test of rejectFriendshipRequest method, of class FriendshipManager.
     */
    @Test
    public void testRejectFriendshipRequest() throws Exception {
        // PRE: Adicionar requisicao de amizade entre os usuarios 'from' e 'to'
        startTransaction();
        User from = createUser("from_rejectFsReq", getEntityManager());
        User to = createUser("to_rejectFsReq", getEntityManager());
        String msg = "testRejectFriendshipRequest() request message";
        FriendshipRequest fsReq = new FriendshipRequest(from, to, msg);
        fsManager.addFriendshipRequest(fsReq);

        // TESTE: Rejeitar requisicao
        fsManager.rejectFriendshipRequest(fsReq);
        commit();

        // POS: 1. Requisicao de amizade com status REJECTED
        //      2. Relacao de amizade entre 'from' e 'to' NAO EXISTIR
        assertEquals("Estado da requisicao incorreto, esperava "
                + FriendshipRequestStatus.REJECTED + " mas obtive "
                + fsReq.getStatus(),
                FriendshipRequestStatus.REJECTED, fsReq.getStatus());

        assertNull("Friendship 'from' -> 'to' nao deveria exitir",
                fsManager.findFriendship(from, to));

        assertNull("Frienship 'to' -> 'from' nao deveria exitir",
                fsManager.findFriendship(to, from));
    }

    /**
     * Test of ackFriendshipRequest method, of class FriendshipManager.
     */
    @Test
    public void testAckFriendshipRequest() throws Exception {
        // PRE: Adicionar requisicao de amizade entre os usuarios 'from' e 'to'
        //      com status ACCEPT, poderia ser PENDING tambem
        startTransaction();
        User from = createUser("from_ackFsReq", getEntityManager());
        User to = createUser("to_ackFsReq", getEntityManager());
        String msg = "testAckFriendshipRequest() request message";
        FriendshipRequest fsReq = new FriendshipRequest(from, to, msg);
        fsReq.setStatus(FriendshipRequestStatus.ACCEPTED);
        fsManager.addFriendshipRequest(fsReq);

        // TESTE: Reconhecer situacao da requisicao (to acknowledge)
        fsManager.ackFriendshipRequest(fsReq);
        commit();

        // POS: 1. Requisicao de amizade com status ACK
        //      2. Relacao de amizade entre 'from' e 'to' NAO EXISTIR
        assertEquals("Estado da requisicao incorreto, esperava "
                + FriendshipRequestStatus.ACK + " mas obtive "
                + fsReq.getStatus(),
                FriendshipRequestStatus.ACK, fsReq.getStatus());

    }

    /**
     * Test of getSentFriendshipRequestList method, of class FriendshipManager.
     */
    @Test
    public void testGetSentFriendshipRequestList() throws Exception {
        // PRE: Criar requisicoes de amizade de 'owner' para os 'nFriends'
        startTransaction();
        int nFriends = 5;
        User from = createUser("from_sentFsReqList", getEntityManager());
        for (int i = 0; i < nFriends; i++) {
            User to = createUser("to" + i + "_sentFsReqList",
                    getEntityManager());

            String msg = "testGetSentFriendshipRequestList() request message";
            FriendshipRequest fsReq = new FriendshipRequest(from, to, msg);
            fsManager.addFriendshipRequest(fsReq);
        }
        commit();

        // TESTE: Resgatar lista de requisicoes de amizade enviadas por 'from'
        List<FriendshipRequest>
                fsList = fsManager.getSentFriendshipRequestList(from);

        // POS: Lista contendo as requisicoes de 'from' para os 'nFriends'
        assertNotNull("Nao foi possivel resgatar lista de requisicoes", fsList);
        assertTrue("Tamanho incorreto da lista de requisicoes, esperava "
                + nFriends + " mas obtive " + fsList.size(),
                nFriends == fsList.size());
    }

    /**
     * Test of getPendingFriendshipRequestList method, of class
     * FriendshipManager.
     */
    @Test
    public void testGetPendingFriendshipRequestList() throws Exception {
        // PRE: Criar requisicoes de amizade de 'nFriends' para 'to'
        startTransaction();
        int nFriends = 5;
        User to = createUser("to_pendingFsReqList", getEntityManager());
        for (int i = 0; i < nFriends; i++) {
            User from = createUser("from" + i + "_pendingFsReqList",
                    getEntityManager());

            String msg = "testGetPendingFriendshipRequestList() request message";
            FriendshipRequest fsReq = new FriendshipRequest(from, to, msg);
            fsManager.addFriendshipRequest(fsReq);
        }
        commit();

        // TESTE: Resgatar lista de requisicoes de amizade enviadas para 'to'
        List<FriendshipRequest>
                fsList = fsManager.getPendingFriendshipRequestList(to);

        // POS: Lista contendo as requisicoes de 'from' para os 'nFriends'
        assertNotNull("Nao foi possivel resgatar lista de requisicoes", fsList);
        assertTrue("Tamanho incorreto da lista de requisicoes, esperava "
                + nFriends + " mas obtive " + fsList.size(),
                nFriends == fsList.size());
    }
}