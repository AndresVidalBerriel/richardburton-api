package br.edu.ifrs.canoas.richardburton.session;

import io.jsonwebtoken.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.Properties;

public class JWT {

    private static Key secret = null;

    private static Key getSecret() {

        if (secret == null)
            try {

                InputStream propertiesStream = JWT.class.getClassLoader().getResourceAsStream("app.properties");
                Properties properties = new Properties();

                assert propertiesStream != null;
                properties.load(propertiesStream);

                char[] ksPassword = properties.getProperty("keystorepsw").toCharArray();
                String ksLocation = properties.getProperty("keystoreloc");
                String keyAlias = properties.getProperty("jwtkeyalias");
                KeyStore ks = KeyStore.getInstance("PKCS12");
                ks.load(new FileInputStream(ksLocation), ksPassword);
                secret = ks.getKey(keyAlias, ksPassword);

            } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException
                    | UnrecoverableKeyException e) {

                e.printStackTrace();
            }

        return secret;
    }

    private static final long TOKEN_TTL_MS = 3600000;
    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS512;

    public static String issueToken(String subject, String issuer, boolean admin) {

        JwtBuilder builder = Jwts.builder();

        Date now = new Date();
        Date exp = new Date(now.getTime() + TOKEN_TTL_MS);

        builder.setSubject(subject);
        builder.setIssuer(issuer);
        builder.setIssuedAt(now);
        builder.setExpiration(exp);
        builder.signWith(ALGORITHM, getSecret());
        builder.claim("admin", admin);

        return builder.compact();
    }

    public static Claims decodeToken(String token) throws JwtException {

        return Jwts.parser().setSigningKey(getSecret()).parseClaimsJws(token).getBody();
    }

}