/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csocial.server.service;

import java.io.Serializable;

/**
 * Interface comum a todos os gerenciadores de entidades (*Manager).
 * 
 * @author Marcos Hack <marcoshack@gmail.com>
 */
public interface GenericManager<T, ID extends Serializable> {

    T findById(ID id);

    T save(T entity);
}
