package br.edu.ifrs.canoas.richardburton.util;

import javax.ws.rs.core.Response;

public enum ServiceStatus implements ServiceResponse {
    OK,
    CONFLICT,
    NOT_FOUND,
    UNAUTHORIZED,
    INVALID_ENTITY,
    EXPIRED_ENTITY;

    @Override
    public ServiceStatus status() {
        return this;
    }

    @Override
    public boolean ok() {
        return this == OK;
    }

    @Override
    public Object descriptor() {
        return this;
    }

    @Override
    public Object entity() {
        return this;
    }

    @Override
    public <T> T unwrap(Class<T> type) {
        throw new UnsupportedOperationException();
    }

    public Response.Status toHttpStatus() {
        if(this == INVALID_ENTITY) return Response.Status.BAD_REQUEST;
        return Response.Status.valueOf(this.name());
    }
}
