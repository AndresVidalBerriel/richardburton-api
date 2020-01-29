package br.edu.ifrs.canoas.richardburton.tests.users;

import br.edu.ifrs.canoas.richardburton.users.EmailFormatException;
import br.edu.ifrs.canoas.richardburton.users.User;
import br.edu.ifrs.canoas.richardburton.users.UserDAO;
import br.edu.ifrs.canoas.richardburton.users.UserService;
import br.edu.ifrs.canoas.richardburton.util.Strings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceRetrieveTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService = new UserService();

    private User user;

    @Before
    public void setup() {
        user = new User();
        user.setEmail("user@example.com");
        user.setAuthenticationString(Strings.digest("abcd"));

        when(userDAO.retrieve(eq(user.getEmail()))).thenReturn(user);
    }

    @Test(expected = EmailFormatException.class)
    public void failEmailInvalid() throws EmailFormatException {

        userService.retrieve("a");
    }

    @Test
    public void success() throws EmailFormatException {

        User retrieved = userService.retrieve(this.user.getEmail());
        assertEquals(this.user, retrieved);
    }

}
