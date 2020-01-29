package br.edu.ifrs.canoas.richardburton.tests.users;

import br.edu.ifrs.canoas.richardburton.users.EmailNotUniqueException;
import br.edu.ifrs.canoas.richardburton.users.User;
import br.edu.ifrs.canoas.richardburton.users.UserDAO;
import br.edu.ifrs.canoas.richardburton.users.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceCreateTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService = new UserService();

    private User user;

    @Before
    public void setup() {

        user = new User();
        user.setEmail("user@example.com");
        when(userDAO.retrieve(eq(user.getEmail()))).thenReturn(user);
        when(userDAO.retrieve(not(eq(user.getEmail())))).thenReturn(null);
        when(userDAO.create(any())).then(AdditionalAnswers.returnsFirstArg());
    }

    @Test(expected = EmailNotUniqueException.class)
    public void failUserAlreadyRegistered() throws EmailNotUniqueException {

        User user = new User();
        user.setEmail(this.user.getEmail());
        userService.create(user);
    }

    @Test
    public void success() throws EmailNotUniqueException {

        User user = new User();
        user.setEmail("other@example.com");
        user.setAuthenticationString("abcd");
        user.setAffiliation("affiliation");
        user.setNationality("nationality");
        user.setOccupation("occupation");
        User created = userService.create(user);
        assertEquals(user, created);
    }
}
