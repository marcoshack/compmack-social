/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csocial.server.ws.provider;

import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;
import com.sun.jersey.spi.service.ComponentContext;
import com.sun.jersey.spi.service.ComponentProvider.Scope;
import java.lang.reflect.Type;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author mhack
 */
@Provider
public class EJBProvider implements InjectableProvider<EJB, Type> {

    public Scope getScope() {
        return Scope.Singleton;
    }

    public Injectable getInjectable(ComponentContext cc, EJB ejb, Type t) {
        if (!(t instanceof Class)) {
            return null;
        }

        try {
            Class c = (Class) t;
            Context ic = new InitialContext();

            final Object o = ic.lookup(c.getName());

            return new Injectable<Object>() {

                public Object getValue(HttpContext c) {
                    return o;
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
