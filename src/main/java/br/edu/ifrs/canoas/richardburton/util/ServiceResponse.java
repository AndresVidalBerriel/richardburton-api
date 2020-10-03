package br.edu.ifrs.canoas.richardburton.util;

public interface ServiceResponse {

    ServiceStatus status();

    boolean ok();

    Object descriptor();

    Object entity();

    <T> T unwrap(Class<T> type);
}
