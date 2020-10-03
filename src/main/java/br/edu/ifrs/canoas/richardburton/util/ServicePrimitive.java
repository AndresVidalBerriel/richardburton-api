package br.edu.ifrs.canoas.richardburton.util;


public class ServicePrimitive<T> extends ServiceEntity {

    private T data;

    public ServicePrimitive(T data) {
        this.data = data;
    }

    public T unwrap() {
        return data;
    }

    @Override
    public <DT> DT unwrap(Class<DT> type) {
        return type.cast(data);
    }
}
