package br.edu.ifrs.canoas.richardburton.tests.session;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.when;
import static org.mockito.AdditionalMatchers.not;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Base64.Encoder;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.edu.ifrs.canoas.richardburton.session.JWT;
import br.edu.ifrs.canoas.richardburton.session.SessionResource;
import br.edu.ifrs.canoas.richardburton.users.EmailFormatException;
import br.edu.ifrs.canoas.richardburton.users.User;
import br.edu.ifrs.canoas.richardburton.users.UserService;
import io.jsonwebtoken.Claims;

@RunWith(MockitoJUnitRunner.class)
public class SessionResourceTest {

    private Dispatcher dispatcher;

    private static User user;
    private static String successHeader;
    private static String failureHeader;
    private static String invalidHeader;

    @Mock
    private UserService userService = new UserService();

    @InjectMocks
    private SessionResource sessionResource = new SessionResource();

    @BeforeClass
    public static void init() {

        user = new User();
        user.setEmail("email@example.com");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setAuthenticationString("authenticationString");
        user.setAdmin(true);
        user.setAffiliation("affiliation");
        user.setOccupation("occupation");
        user.setNationality("nationality");

        Encoder b64encoder = Base64.getEncoder();
        String credentials = user.getEmail() + ":" + user.getAuthenticationString();
        byte[] encoded = b64encoder.encode(credentials.getBytes());

        successHeader = "Basic " + new String(encoded);

        credentials = user.getEmail() + ":" + "";
        encoded = b64encoder.encode(credentials.getBytes());

        failureHeader = "Basic " + new String(encoded);

        credentials = "a" + ":" + "";
        encoded = b64encoder.encode(credentials.getBytes());

        invalidHeader = "Basic " + new String(encoded);

    }

    @Before
    public void setup() throws EmailFormatException {

        dispatcher = MockDispatcherFactory.createDispatcher();
        dispatcher.getRegistry().addSingletonResource(sessionResource);

        String email = user.getEmail();
        String authenticationString = user.getAuthenticationString();

        when(userService.authenticate(eq(email), eq(authenticationString))).thenReturn(user);
        when(userService.authenticate(eq(email), not(eq(authenticationString)))).thenReturn(null);
        when(userService.authenticate(not(matches(User.EMAIL_FORMAT)), anyString())).thenThrow(EmailFormatException.class);

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
    public void signInFailTokenNotPresent() throws URISyntaxException, UnsupportedEncodingException {

        MockHttpRequest request = MockHttpRequest.post("session");
        MockHttpResponse response = new MockHttpResponse();
        request.header(HttpHeaders.AUTHORIZATION, failureHeader);

        dispatcher.invoke(request, response);

        String responseContent = response.getContentAsString();

        assertEquals("", responseContent);
    }

    @Test
    public void signInFailInvalidEmail() throws URISyntaxException {

        MockHttpRequest request = MockHttpRequest.post("session");
        MockHttpResponse response = new MockHttpResponse();
        request.header(HttpHeaders.AUTHORIZATION, invalidHeader);

        dispatcher.invoke(request, response);

        int expectedStatus = Response.Status.BAD_REQUEST.getStatusCode();
        int actualStatus = response.getStatus();
        assertEquals(expectedStatus, actualStatus);
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
    @SuppressWarnings("unchecked")
    public void signInSuccessTokenIsValid() throws URISyntaxException, UnsupportedEncodingException, JsonProcessingException {

        MockHttpRequest request = MockHttpRequest.post("session");
        MockHttpResponse response = new MockHttpResponse();
        request.header(HttpHeaders.AUTHORIZATION, successHeader);

        dispatcher.invoke(request, response);

        String responseContent = response.getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> responseData;
        responseData = mapper.readValue(responseContent, HashMap.class);
        String token = responseData.get("token");

        JWT.decodeToken(token);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void signInSuccessAdminClaimTrue() throws URISyntaxException, JsonProcessingException, UnsupportedEncodingException {

        MockHttpRequest request = MockHttpRequest.post("session");
        MockHttpResponse response = new MockHttpResponse();
        request.header(HttpHeaders.AUTHORIZATION, successHeader);

        dispatcher.invoke(request, response);

        String responseContent = response.getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> responseData;
        responseData = mapper.readValue(responseContent, HashMap.class);
        String token = responseData.get("token");

        Claims claims = JWT.decodeToken(token);

        assertEquals(user.isAdmin(), claims.get("admin"));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void signInSuccessAdminClaimFalse() throws URISyntaxException, JsonProcessingException, UnsupportedEncodingException {

        user.setAdmin(false);

        MockHttpRequest request = MockHttpRequest.post("session");
        MockHttpResponse response = new MockHttpResponse();
        request.header(HttpHeaders.AUTHORIZATION, successHeader);

        dispatcher.invoke(request, response);

        String responseContent = response.getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> responseData;
        responseData = mapper.readValue(responseContent, HashMap.class);
        String token = responseData.get("token");

        Claims claims = JWT.decodeToken(token);

        assertEquals(user.isAdmin(), claims.get("admin"));

        user.setAdmin(true);
    }

}