package br.edu.ifrs.canoas.richardburton.util;

public class ServiceError implements ServiceResponse {

    private ServiceStatus status;
    private Object descriptor;

    public ServiceError(ServiceStatus status, Object descriptor) {
        this.status = status;
        this.descriptor = descriptor == null ? status : descriptor;
    }

    @Override
    public ServiceStatus status() {
        return status;
    }

    @Override
    public boolean ok() {
        return false;
    }

    @Override
    public Object descriptor() {
        return descriptor;
    }

    @Override
    public Object entity() {
        return descriptor;
    }
}
