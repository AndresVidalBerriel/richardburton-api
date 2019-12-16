package br.edu.ifrs.canoas.transnacionalidades.richardburton.tests.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.controllers.UserController;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.entities.User;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.services.SessionService;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.util.JWT;
import io.jsonwebtoken.Claims;

@RunWith(MockitoJUnitRunner.class)
public class SessionServiceTest {

    private User user;
    private HashMap<String, String> successParams;
    private HashMap<String, String> failureParams;

    @Mock
    private UserController userController;

    @InjectMocks
    private SessionService sessionService = new SessionService();

    @Before
    public void init() {

        user = new User();
        user.setEmail("email");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setAuthenticationString("authenticationString");
        user.setAdmin(true);
        user.setAffiliation("affiliation");
        user.setOccupation("occupation");
        user.setNationality("nationality");

        when(userController.authenticate(user.getEmail(), user.getAuthenticationString())).thenReturn(user);
        when(userController.authenticate(eq(user.getEmail()),
                AdditionalMatchers.not(eq(user.getAuthenticationString())))).thenReturn(null);

        successParams = new HashMap<>();
        successParams.put("email", user.getEmail());
        successParams.put("authenticationString", user.getAuthenticationString());

        failureParams = new HashMap<>();
        failureParams.put("email", user.getEmail());
        failureParams.put("authenticationString", "");
    }

    @Test
    public void signInFailStatusCode() {

        Response response = sessionService.signIn(failureParams);
        int expectedStatus = Response.Status.UNAUTHORIZED.getStatusCode();
        int actualStatus = response.getStatus();
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void signInFailTokenNotPresent() {

        Response response = sessionService.signIn(failureParams);
        String authorizationHeader = response.getHeaderString(HttpHeaders.AUTHORIZATION);
        assertEquals(null, authorizationHeader);
    }

    @Test
    public void signInSuccessStatus() {

        Response response = sessionService.signIn(successParams);

        int expectedStatus = Response.Status.OK.getStatusCode();
        int actualStatus = response.getStatus();

        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void signInSuccessTokenIsValid() {

        Response response = sessionService.signIn(successParams);

        String authorizationHeader = response.getHeaderString(HttpHeaders.AUTHORIZATION);
        String authorizationToken = authorizationHeader.substring("Bearer".length()).trim();

        JWT.decodeToken(authorizationToken);
    }

    @Test
    public void signInSuccesAdminClaim() {

        Response response = sessionService.signIn(successParams);

        String authorizationHeader = response.getHeaderString(HttpHeaders.AUTHORIZATION);
        String authorizationToken = authorizationHeader.substring("Bearer".length()).trim();
        Claims claims = JWT.decodeToken(authorizationToken);

        assertEquals(user.isAdmin(), claims.get("admin"));
    }
}