package br.edu.ifrs.canoas.richardburton;

import org.apache.commons.lang3.RandomStringUtils;

import javax.crypto.KeyGenerator;
import java.io.*;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Properties;

public class RichardBurton {

    private static String HOME = "richardburton.home";
    private static String INDEX_BASE = "hibernate.search.indexBase";

    private static String KEY_STORE_ALGORITHM = "keystore.algorithm";
    private static String KEY_STORE_LOCATION = "keystore.location";
    private static String KEY_STORE_NAME = "keystore.name";
    private static String KEY_STORE_INSTANCE_TYPE = "keystore.instanceType";

    private static String JWT_KEY_ALIAS = "keystore.keys.jwt";
    private static String JWT_TTL_MS = "jwt.ttlms";
    private static String JWT_TOKEN_ISSUER = "jwt.issuer";

    private static String USER_INVITATION_TTL_HOURS = "userInvitation.ttlhours";

    private static Properties properties = null;

    private static KeyStore keyStore;
    private static char[] keyStorePassword;

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

        return properties;
    }

    public static Key getJWTSecret() {

        Properties props = getProperties();

        try {
            String keyAlias = props.getProperty(JWT_KEY_ALIAS);
            return keyStore.getKey(keyAlias, keyStorePassword);

        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {

            throw new RuntimeException(e);
        }
    }

    public static long getJWTTokenTTLMS() {

        return Long.parseLong(getProperties().getProperty(JWT_TTL_MS));
    }

    public static String getJWTTokenIssuer() {

        return getProperties().getProperty(JWT_TOKEN_ISSUER);
    }

    public static double getUserInvitationTTLHours() {

        return Double.parseDouble(getProperties().getProperty(USER_INVITATION_TTL_HOURS));
    }

    public static void setup() {

        for (String property : new String[]{HOME, INDEX_BASE, KEY_STORE_LOCATION}) {

            File file = new File(getProperties().getProperty(property));

            if (!file.exists()) {
                boolean success = file.mkdirs();
                if (!success) throw new RuntimeException("Could not create directory: " + file.toString());
            }
        }

        String keyStoreLocation = getProperties().getProperty(KEY_STORE_LOCATION);
        String keyStoreName = getProperties().getProperty(KEY_STORE_NAME);

        File keyStoreFile = new File(keyStoreLocation + "/" + keyStoreName);
        if (keyStoreFile.exists() && !keyStoreFile.delete())
            throw new RuntimeException("Could not delete existing keystore");

        try {

            String keyAlgorithm = getProperties().getProperty(KEY_STORE_ALGORITHM);
            KeyGenerator keyGenerator = KeyGenerator.getInstance(keyAlgorithm);

            String keyStoreInstanceType = getProperties().getProperty(KEY_STORE_INSTANCE_TYPE);
            keyStore = KeyStore.getInstance(keyStoreInstanceType);

            keyStore.load(null, null);

            keyStorePassword = RandomStringUtils.randomAlphanumeric(256).toCharArray();

            String jwtKeyAlias = getProperties().getProperty(JWT_KEY_ALIAS);
            keyStore.setKeyEntry(jwtKeyAlias, keyGenerator.generateKey(), keyStorePassword, null);

            keyStore.store(new FileOutputStream(keyStoreFile), keyStorePassword);

        } catch (NoSuchAlgorithmException | KeyStoreException | IOException | CertificateException e) {

            throw new RuntimeException(e);
        }
    }
}