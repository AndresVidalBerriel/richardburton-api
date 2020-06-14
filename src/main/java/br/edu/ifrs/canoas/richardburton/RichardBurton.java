package br.edu.ifrs.canoas.richardburton;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Properties;

public class RichardBurton {

    private static String KEY_STORE_PASSWORD = "keystore_password";
    private static String KEY_STORE_LOCATION = "keystore_location";
    private static String KEY_STORE_INSTANCE_TYPE = "keystore_instance_type";
    private static String JWT_KEY_ALIAS = "jwt_key_alias";
    private static String JWT_TTL_MS = "jwt_ttl_ms";
    private static String JWT_TOKEN_ISSUER = "jwt_issuer";

    private static Properties properties = null;

    private static Properties getProperties() {

        if (properties == null) {

            InputStream propertiesStream = RichardBurton.class.getClassLoader().getResourceAsStream("app.properties");

            properties = new Properties();

            assert propertiesStream != null;

            try {

                properties.load(propertiesStream);

            } catch (IOException e) {

                throw new RuntimeException(e);
            }
        }

        assert properties != null;

        return properties;
    }

    public static Key getJWTSecret() {

        Properties props = getProperties();

        char[] keyStorePassword = props.getProperty(KEY_STORE_PASSWORD).toCharArray();
        String keyStoreLocation = props.getProperty(KEY_STORE_LOCATION);
        String keyAlias = props.getProperty(JWT_KEY_ALIAS);

        try {

            KeyStore keyStore;
            keyStore = KeyStore.getInstance(props.getProperty(KEY_STORE_INSTANCE_TYPE));
            keyStore.load(new FileInputStream(keyStoreLocation), keyStorePassword);

            return keyStore.getKey(keyAlias, keyStorePassword);

        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException
                | UnrecoverableKeyException e) {

            throw new RuntimeException(e);
        }
    }

    public static long getJWTTokenTTLMS() {

        return Long.parseLong(getProperties().getProperty(JWT_TTL_MS));
    }

    public static String getJWTTokenIssuer() {

        return getProperties().getProperty(JWT_TOKEN_ISSUER);
    }

}