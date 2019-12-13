package br.edu.ifrs.canoas.transnacionalidades.richardburton.integrationtests.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.GenericType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.controllers.UserController;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.dao.BaseDAO;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.dao.UserDAO;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.User;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.services.ApplicationService;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.services.UserService;

@RunWith(Arquillian.class)
public class UserServiceIT {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(ApplicationService.class, UserService.class, UserController.class, BaseDAO.class,
                        UserDAO.class, User.class)
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml");
    }

    @Inject
    private UserService userService;

    @Test
    public void test() {

        GenericType<List<User>> type = new GenericType<List<User>>() {
        };

        List<User> users = (List<User>) userService.retrieveAll().readEntity(type);
        assertTrue(users.isEmpty());
    }

}