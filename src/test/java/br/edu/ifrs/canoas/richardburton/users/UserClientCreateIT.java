package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.*;
import br.edu.ifrs.canoas.richardburton.session.Session;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class UserClientCreateIT {

    private User user;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class).addClass(Session.class)
                .addClasses(ApplicationResource.class, CORSFilter.class).addClasses(DAO.class, DAOImpl.class)
                .addClasses(EntityService.class, EntityServiceImpl.class)
                .addClasses(DuplicateEntityException.class, EntityValidationException.class,
                        EntityNotFoundException.class)
                .addPackage(UserResource.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
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
    public void success(@ArquillianResteasyResource("api/v1") UserResource userResource) {

        assert userResource != null;

        final Response response = userResource.create(user, user.getAuthenticationString());

        assertEquals(Status.CREATED, response.getStatusInfo());

        User created = response.readEntity(User.class);

        assertTrue(created.getId() != null);
        assertEquals(user.getFirstName(), created.getFirstName());
        assertEquals(user.getLastName(), created.getLastName());
        assertEquals(user.getEmail(), created.getEmail());
        assertEquals(user.isAdmin(), created.isAdmin());
        assertEquals(null, created.getAuthenticationString());

        assertEquals(1, userResource.retrieve().readEntity(User[].class).length);
    }

    @Test
    @InSequence(3)
    @Cleanup(phase = TestExecutionPhase.AFTER)
    public void cleanupAfterSuccess() {
    }

    @Test
    @InSequence(4)
    @RunAsClient
    public void failureUserMissing(@ArquillianResteasyResource("api/v1") UserResource userResource) {

        assert userResource != null;

        final Response response = userResource.create(null, "abcdef");
        assertEquals(Status.BAD_REQUEST, response.getStatusInfo());

        assertEquals(0, userResource.retrieve().readEntity(User[].class).length);
    }

    @Test
    @InSequence(5)
    @Cleanup(phase = TestExecutionPhase.AFTER)
    public void cleanupAfterFailureUserMissing() {
    }

    @Test
    @InSequence(6)
    @RunAsClient
    public void failureAuthenticationStringMissing(@ArquillianResteasyResource("api/v1") UserResource userResource) {

        assert userResource != null;

        final Response response = userResource.create(user, null);
        assertEquals(Status.BAD_REQUEST, response.getStatusInfo());

        assertEquals(0, userResource.retrieve().readEntity(User[].class).length);
    }

    @Test
    @InSequence(7)
    @Cleanup(phase = TestExecutionPhase.AFTER)
    public void cleanupAfterFailureMissingUser() {
    }

    @Test
    @InSequence(8)
    @RunAsClient
    public void failureAuthenticationStringEmpty(@ArquillianResteasyResource("api/v1") UserResource userResource) {

        assert userResource != null;

        final Response response = userResource.create(user, "");
        assertEquals(Status.BAD_REQUEST, response.getStatusInfo());

        assertEquals(0, userResource.retrieve().readEntity(User[].class).length);
    }

    @Test
    @InSequence(9)
    @Cleanup(phase = TestExecutionPhase.AFTER)
    public void cleanupAfterFailureAuthenticationStringEmpty() {
    }

    @Test
    @InSequence(10)
    @RunAsClient
    public void failureAuthenticationStringBlank(@ArquillianResteasyResource("api/v1") UserResource userResource) {

        assert userResource != null;

        final Response response = userResource.create(user, "   ");
        assertEquals(Status.BAD_REQUEST, response.getStatusInfo());

        assertEquals(0, userResource.retrieve().readEntity(User[].class).length);
    }

    @Test
    @InSequence(11)
    @Cleanup(phase = TestExecutionPhase.AFTER)
    public void cleanupAfterFailureAuthenticationStringBlank() {
    }

    @Test
    @InSequence(12)
    @RunAsClient
    public void failureEmailMissing(@ArquillianResteasyResource("api/v1") UserResource userResource) {

        assert userResource != null;

        user.setEmail(null);

        final Response response = userResource.create(user, user.getAuthenticationString());
        assertEquals(Status.BAD_REQUEST, response.getStatusInfo());

        assertEquals(0, userResource.retrieve().readEntity(User[].class).length);
    }

    @Test
    @InSequence(13)
    @Cleanup(phase = TestExecutionPhase.AFTER)
    public void cleanupAfterFailureEmailMissing() {
    }

    @Test
    @InSequence(14)
    @RunAsClient
    public void failureEmailEmpty(@ArquillianResteasyResource("api/v1") UserResource userResource) {

        assert userResource != null;

        user.setEmail("");

        final Response response = userResource.create(user, user.getAuthenticationString());
        assertEquals(Status.BAD_REQUEST, response.getStatusInfo());

        assertEquals(0, userResource.retrieve().readEntity(User[].class).length);
    }

    @Test
    @InSequence(15)
    @Cleanup(phase = TestExecutionPhase.AFTER)
    public void cleanupAfterFailureEmailEmpty() {
    }

    @Test
    @InSequence(16)
    @RunAsClient
    public void failureEmailBlank(@ArquillianResteasyResource("api/v1") UserResource userResource) {

        assert userResource != null;

        user.setEmail(" ");

        final Response response = userResource.create(user, user.getAuthenticationString());
        assertEquals(Status.BAD_REQUEST, response.getStatusInfo());

        assertEquals(0, userResource.retrieve().readEntity(User[].class).length);
    }
}
