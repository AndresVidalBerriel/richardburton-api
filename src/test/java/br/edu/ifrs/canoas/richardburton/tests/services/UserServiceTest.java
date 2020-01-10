package br.edu.ifrs.canoas.richardburton.tests.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import br.edu.ifrs.canoas.richardburton.users.EmailFormatException;
import br.edu.ifrs.canoas.richardburton.users.UserDAO;
import br.edu.ifrs.canoas.richardburton.users.UserService;

public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService = new UserService();

    @Before
    public void setup() {

    }

    @Test(expected = EmailFormatException.class)
    public void EmailInvalid() throws EmailFormatException {

        userService.authenticate("a", "");
    }

}