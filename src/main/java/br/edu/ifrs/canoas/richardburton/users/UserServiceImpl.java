package br.edu.ifrs.canoas.richardburton.users;

import br.edu.ifrs.canoas.richardburton.DAO;
import br.edu.ifrs.canoas.richardburton.EntityServiceImpl;
import br.edu.ifrs.canoas.richardburton.auth.Credentials;
import br.edu.ifrs.canoas.richardburton.auth.CredentialsBuilder;
import br.edu.ifrs.canoas.richardburton.auth.CredentialsService;
import br.edu.ifrs.canoas.richardburton.util.ServicePrimitive;
import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;
import br.edu.ifrs.canoas.richardburton.util.ServiceStatus;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.regex.Pattern;

@Stateless
public class UserServiceImpl extends EntityServiceImpl<User, Long> implements UserService {

    @Inject
    private UserDAO userDAO;

    @Inject
    private CredentialsService credentialsService;

    @Override
    protected DAO<User, Long> getDAO() {
        return userDAO;
    }

    @Override
    public ServiceResponse retrieve(String email) {

        if (email == null || !Pattern.matches(User.EMAIL_FORMAT, email))
            return ServiceStatus.INVALID_ENTITY;

        User user = userDAO.retrieve(email);
        return user == null ? ServiceStatus.NOT_FOUND : user;
    }

    @Override
    public ServiceResponse create(User user, String password) {

        ServiceResponse response = retrieve(user.getEmail());
        switch (response.status()) {

            case NOT_FOUND:

                Credentials credentials = new CredentialsBuilder()
                  .identifier(user.getEmail())
                  .secret(password)
                  .build();

                response = credentialsService.create(credentials);
                return response.ok()
                  ? super.create(user)
                  : response;

            case OK:
                return ServiceStatus.CONFLICT;
            default:
                return response;
        }
    }

    @Override
    public ServiceResponse getEmail(Long id) {

        String email = userDAO.getEmail(id);
        return email == null
          ? ServiceStatus.NOT_FOUND
          : new ServicePrimitive<>(email);
    }
}