package br.edu.ifrs.canoas.transnacionalidades.richardburton.tests.services;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import br.edu.ifrs.canoas.transnacionalidades.richardburton.dao.UserDAO;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.exceptions.InvalidEmailFormatException;
import br.edu.ifrs.canoas.transnacionalidades.richardburton.services.UserService;

public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService = new UserService();

    @Before
    public void setup() {

    }

    @Test(expected = InvalidEmailFormatException.class)
    public void EmailInvalid() throws InvalidEmailFormatException {

        userService.authenticate("a", "");
    }

}