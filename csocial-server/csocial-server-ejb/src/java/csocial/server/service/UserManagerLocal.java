/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csocial.server.service;

import csocial.server.entity.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author mhack
 */
@Local
public interface UserManagerLocal {

    /**
     * Insere ou atualiza (caso ja exista) o objeto User no banco de dados.
     * 
     * @param user
     * @throws UserAlreadyExistException se ja existir um usuario com mesmo
     * username ou endereco de e-mail.
     */
    public void save(User user) throws UserAlreadyExistException;

    /**
     * Busca objeto User pelo 'id' informado.
     *
     * @param id
     * @return
     */
    public User getUser(long id);

    /**
     * Retorna objeto User cujo 'username' eh exatamente igual ao informado como
     * parametro.
     *
     * @param username
     * @return User ou nulo nao exista usuario o username informado.
     */
    public User getByUsername(String username);


    /**
     * Retorna objeto User cujo endereco de e-mail eh exatamente igual ao
     * informado no parametro 'email'.
     *
     * @param email
     * @return
     */
    public User getByEmail(String email);


    /**
     * Retorna lista de usuarios cujo o padrao informado no parametro 'pattern'
     * case com o conteudo de um ou mais dos seguintes campos: username, 
     * endereco de e-mail ou nome real do usuario.
     * 
     * @param Padrao a ser buscado
     * @return Lista de usuarios
     */
    public List<User> find(String pattern);
}
