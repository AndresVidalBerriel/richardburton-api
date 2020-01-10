package br.edu.ifrs.canoas.richardburton.tests.integration.data;

import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.edu.ifrs.canoas.richardburton.users.User;
import br.edu.ifrs.canoas.richardburton.users.UserDAO;
import br.edu.ifrs.canoas.richardburton.util.BaseDAO;

@RunWith(Arquillian.class)
public class UserDAOIT {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class).addClasses(BaseDAO.class, UserDAO.class, User.class)
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml");
    }

    @Inject
    private UserDAO userDAO;

    @Test
    public void test() {
        assertTrue(true);
    }

}