package br.edu.ifrs.canoas.richardburton.auth;

public class CredentialsBuilder {

    private String credentialsIdentifier;
    private String credentialsToken;
    private String credentialsSecret;

    public CredentialsBuilder identifier(String identifier) {
        credentialsIdentifier = identifier;
        return this;
    }

    public CredentialsBuilder token(String token) {
        credentialsToken = token;
        return this;
    }

    public CredentialsBuilder secret(String secret) {
        credentialsSecret = secret;
        return this;
    }

    public Credentials build() {
        Credentials credentials = new Credentials();
        credentials.setIdentifier(credentialsIdentifier);
        credentials.setSecret(credentialsSecret);
        credentials.setToken(credentialsToken);
        return credentials;
    }
}
