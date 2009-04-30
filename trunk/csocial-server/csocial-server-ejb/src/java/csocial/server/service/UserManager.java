/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package csocial.server.service;

import csocial.server.entity.User;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author mhack
 */
@Remote
public interface UserManager extends GenericManager<User, Long> {

    /**
     * Retorna objeto User cujo 'username' eh exatamente igual ao informado como
     * parametro.
     *
     * @param username
     * @return User ou nulo nao exista usuario o username informado.
     */
    public User findByUsername(String username);


    /**
     * Retorna objeto User cujo endereco de e-mail eh exatamente igual ao
     * informado no parametro 'email'.
     *
     * @param email
     * @return
     */
    public User findByEmail(String email);


    /**
     * Retorna lista de usuarios cujo o padrao informado no parametro 'pattern'
     * casa com o conteudo de um ou mais dos seguintes campos: username,
     * endereco de e-mail ou nome real do usuario.
     * 
     * @param Padrao a ser buscado
     * @return Lista de usuarios
     */
    public List<User> find(String pattern);
}
