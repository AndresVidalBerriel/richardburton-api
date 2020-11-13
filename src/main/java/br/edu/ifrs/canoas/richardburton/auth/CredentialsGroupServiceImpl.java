package br.edu.ifrs.canoas.richardburton.auth;

import br.edu.ifrs.canoas.richardburton.EntityServiceImpl;
import br.edu.ifrs.canoas.richardburton.util.ServiceResponse;
import br.edu.ifrs.canoas.richardburton.util.ServiceStatus;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Stateless
public class CredentialsGroupServiceImpl extends EntityServiceImpl<CredentialsGroup, String> implements CredentialsGroupService{

    @Inject
    private CredentialsGroupDAO credentialsGroupDAO;

    @Inject
    private CredentialsService credentialsService;

    @Override
    protected CredentialsGroupDAO getDAO() {
        return credentialsGroupDAO;
    }

    @Override
    public ServiceResponse create(CredentialsGroup credentialsGroup) {

        ServiceResponse response = retrieve(credentialsGroup.getName());
        switch (response.status()) {
            case NOT_FOUND:
                return super.create(credentialsGroup);
            case OK:
                return ServiceStatus.CONFLICT;
            default:
                return response;
        }
    }

    @Override
    public ServiceResponse addMember(String groupName, String memberId) {

        ServiceResponse response = retrieve(groupName);
        if(!response.ok()) return response;
        CredentialsGroup group = (CredentialsGroup) response;

        response = credentialsService.retrieve(memberId);
        if(!response.ok()) return response;
        Credentials member = (Credentials) response;

        group.add(member);
        credentialsGroupDAO.update(group);
        return ServiceStatus.OK;
    }

    @Override
    public ServiceResponse removeMember(String groupName, String memberId) {

        ServiceResponse response = retrieve(groupName);
        if(!response.ok()) return response;
        CredentialsGroup group = (CredentialsGroup) response;

        response = credentialsService.retrieve(memberId);
        if(!response.ok()) return response;
        Credentials member = (Credentials) response;

        group.remove(member);
        credentialsGroupDAO.update(group);
        return ServiceStatus.OK;
    }

    @Override
    public List<String> getNames() {
        return credentialsGroupDAO.getNames();
    }

    @Override
    public ServiceResponse setPermissions(String groupName, Set<Permissions> permissions) {
        ServiceResponse response = retrieve(groupName);
        if(!response.ok()) return response;
        CredentialsGroup group = (CredentialsGroup) response;

        group.setPermissions(permissions);
        credentialsGroupDAO.update(group);
        return ServiceStatus.OK;
    }
}
