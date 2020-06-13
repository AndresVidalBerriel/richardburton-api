package br.edu.ifrs.canoas.richardburton.session;

import br.edu.ifrs.canoas.richardburton.*;
import br.edu.ifrs.canoas.richardburton.users.User;
import br.edu.ifrs.canoas.richardburton.users.UserResource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import java.io.File;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class SessionClientCreateIT {

    private User user;

    @Deployment
    public static WebArchive createDeployment() {

        return ShrinkWrap.create(WebArchive.class).addClass(Session.class)
                .addClasses(ApplicationResource.class, CORSFilter.class).addClasses(DAO.class, DAOImpl.class)
                .addClasses(EntityService.class, EntityServiceImpl.class)
                .addClasses(DuplicateEntityException.class, EntityValidationException.class,
                        EntityNotFoundException.class)
                .addPackage(UserResource.class.getPackage()).addPackage(SessionResource.class.getPackage())
                .addAsLibraries(Maven.resolver().loadPomFromFile(new File("pom.xml"))
                        .resolve("io.jsonwebtoken:jjwt:0.9.1").withTransitivity().asFile())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("app.properties", "app.properties");
    }

    @Before
    public void init() {

        // Initialize a valid user

        user = new User();

        user.setEmail("andres.vidal@canoas.ifrs.edu.br");
        user.setFirstName("Andrés");
        user.setLastName("Vidal");
        user.setAuthenticationString(Session.digest("123456"));
    }

    @Test
    @InSequence(1)
    @Cleanup(phase = TestExecutionPhase.AFTER)
    public void cleanupBefore() {
    }

    @Test
    @RunAsClient
    @InSequence(2)
    public void success(@ArquillianResteasyResource("api/v1") UserResource userResource,
            @ArquillianResteasyResource("api/v1") SessionResource sessionResource) {

        assert userResource != null;
        assert sessionResource != null;

        Response response = userResource.create(user, user.getAuthenticationString());

        assertEquals(Status.CREATED, response.getStatusInfo());

        assert Status.CREATED == response.getStatusInfo();

        response = sessionResource.create("Basic " + user.getBasicAuthToken());

        assertEquals(Status.OK, response.getStatusInfo());

    }
}
