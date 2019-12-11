package br.edu.ifrs.canoas.transnacionalidades.richardburton.util.http;

import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

public class RBHttpStatusType implements StatusType {

    private int statusCode;
    private Family family;
    private String reasonPhrase;

    public RBHttpStatusType(int statusCode, Family family, String reasonPhrase) {
        this.statusCode = statusCode;
        this.family = family;
        this.reasonPhrase = reasonPhrase;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public Family getFamily() {
        return family;
    }

    @Override
    public String getReasonPhrase() {
        return reasonPhrase;
    }
}