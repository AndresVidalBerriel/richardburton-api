package br.edu.ifrs.canoas.transnacionalidades.richardburton.util.http;

import javax.ws.rs.core.Response.Status.Family;

public final class RBHttpStatus {

    public static final RBHttpStatusType SESSION_EXPIRED;
    public static final RBHttpStatusType UNPROCESSABLE_ENTITY;

    static {

        SESSION_EXPIRED = new RBHttpStatusType(419, Family.CLIENT_ERROR, "Session expired");
        UNPROCESSABLE_ENTITY = new RBHttpStatusType(422, Family.CLIENT_ERROR, "Unprocessable entity");
    }

    private RBHttpStatus() {
    }
}
