package br.edu.ifrs.canoas.richardburton.auth;

import java.util.EnumSet;

public class AuthorizationException extends Exception {

    private static final long serialVersionUID = 1L;

    private EnumSet<Permissions> missingPermissions;

    public AuthorizationException(EnumSet<Permissions> missingPermissions) {
        super("Authorization Failed. Missing permissions: " + missingPermissions.toString());
        this.missingPermissions = missingPermissions;
    }

    public EnumSet<Permissions> getMissingPermissions() {
        return missingPermissions;
    }
}
