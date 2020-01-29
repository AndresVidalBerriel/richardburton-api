package br.edu.ifrs.canoas.richardburton.tests.users;

import br.edu.ifrs.canoas.richardburton.users.UserDAO;
import br.edu.ifrs.canoas.richardburton.users.UserResource;
import br.edu.ifrs.canoas.richardburton.util.BaseDAO;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.client.WebTarget;

import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class UserResourceIT {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class).addClass(BaseDAO.class).addPackage(UserResource.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private UserDAO userDAO;

    @Test
    @RunAsClient
    public void test(@ArquillianResteasyResource final WebTarget webTarget) {
        assertTrue(true);
    }

}
