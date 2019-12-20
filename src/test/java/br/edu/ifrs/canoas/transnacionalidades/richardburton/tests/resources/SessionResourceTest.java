package br.edu.ifrs.canoas.transnacionalidades.richardburton.tests.resources;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.services.UserService;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.User;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.resources.SessionResource;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.util.JWT;
import io.jsonwebtoken.Claims;

@RunWith(MockitoJUnitRunner.class)
public class SessionResourceTest {

    private Dispatcher dispatcher;

    private static User user;
    private static String successHeader;
    private static String failureHeader;

    @Mock
    private UserService userService = new UserService();

    @InjectMocks
    private SessionResource sessionResource = new SessionResource();

    @BeforeClass
    public static void init() {

        user = new User();
        user.setEmail("email");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setAuthenticationString("authenticationString");
        user.setAdmin(true);
        user.setAffiliation("affiliation");
        user.setOccupation("occupation");
        user.setNationality("nationality");

        successHeader = "Basic " + user.getEmail() + ":" + user.getAuthenticationString();
        failureHeader = "Basic " + user.getEmail() + ":" + "";
    }

    @Before
    public void setup() {

        dispatcher = MockDispatcherFactory.createDispatcher();
        dispatcher.getRegistry().addSingletonResource(sessionResource);

        String email = user.getEmail();
        String authenticationString = user.getAuthenticationString();

        when(userService.authenticate(email, authenticationString)).thenReturn(user);
        when(userService.authenticate(eq(email), AdditionalMatchers.not(eq(authenticationString)))).thenReturn(null);
    }

    @Test
    public void signInFailNoAuthorizationHeader() throws URISyntaxException {

        MockHttpRequest request = MockHttpRequest.post("session");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        int expectedStatus = Response.Status.BAD_REQUEST.getStatusCode();
        int actualStatus = response.getStatus();
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void signInFailStatusCode() throws URISyntaxException {

        MockHttpRequest request = MockHttpRequest.post("session");
        MockHttpResponse response = new MockHttpResponse();
        request.header(HttpHeaders.AUTHORIZATION, failureHeader);

        dispatcher.invoke(request, response);

        int expectedStatus = Response.Status.UNAUTHORIZED.getStatusCode();
        int actualStatus = response.getStatus();
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void signInFailTokenNotPresent() throws URISyntaxException {

        MockHttpRequest request = MockHttpRequest.post("session");
        MockHttpResponse response = new MockHttpResponse();
        request.header(HttpHeaders.AUTHORIZATION, failureHeader);

        dispatcher.invoke(request, response);

        String authorizationHeader = (String) response.getOutputHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        assertEquals(null, authorizationHeader);
    }

    @Test
    public void signInSuccessStatus() throws URISyntaxException {

        MockHttpRequest request = MockHttpRequest.post("session");
        MockHttpResponse response = new MockHttpResponse();
        request.header(HttpHeaders.AUTHORIZATION, successHeader);

        dispatcher.invoke(request, response);

        int expectedStatus = Response.Status.OK.getStatusCode();
        int actualStatus = response.getStatus();
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void signInSuccessTokenIsValid() throws URISyntaxException {

        MockHttpRequest request = MockHttpRequest.post("session");
        MockHttpResponse response = new MockHttpResponse();
        request.header(HttpHeaders.AUTHORIZATION, successHeader);

        dispatcher.invoke(request, response);

        String authorizationHeader = response.getOutputHeaders().getFirst(HttpHeaders.AUTHORIZATION).toString();
        String authorizationToken = authorizationHeader.substring("Bearer".length()).trim();
        JWT.decodeToken(authorizationToken);
    }

    @Test
    public void signInSuccesAdminClaimTrue() throws URISyntaxException {

        MockHttpRequest request = MockHttpRequest.post("session");
        MockHttpResponse response = new MockHttpResponse();
        request.header(HttpHeaders.AUTHORIZATION, successHeader);

        dispatcher.invoke(request, response);

        String authorizationHeader = response.getOutputHeaders().getFirst(HttpHeaders.AUTHORIZATION).toString();
        String authorizationToken = authorizationHeader.substring("Bearer".length()).trim();
        Claims claims = JWT.decodeToken(authorizationToken);

        assertEquals(user.isAdmin(), claims.get("admin"));
    }

    @Test
    public void signInSuccesAdminClaimFalse() throws URISyntaxException {

        user.setAdmin(false);

        MockHttpRequest request = MockHttpRequest.post("session");
        MockHttpResponse response = new MockHttpResponse();
        request.header(HttpHeaders.AUTHORIZATION, successHeader);

        dispatcher.invoke(request, response);

        String authorizationHeader = response.getOutputHeaders().getFirst(HttpHeaders.AUTHORIZATION).toString();
        String authorizationToken = authorizationHeader.substring("Bearer".length()).trim();
        Claims claims = JWT.decodeToken(authorizationToken);

        assertEquals(user.isAdmin(), claims.get("admin"));

        user.setAdmin(true);
    }
}