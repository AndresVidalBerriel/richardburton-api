package br.edu.ifrs.canoas.richardburton.util;

public abstract class ServiceEntity implements ServiceResponse {

    @Override
    public ServiceStatus status() {
        return ServiceStatus.OK;
    }

    @Override
    public boolean ok() {
        return true;
    }

    @Override
    public Object entity() {
        return this;
    }

    @Override
    public Object descriptor() {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> type) {
        throw new UnsupportedOperationException();
    }
}
