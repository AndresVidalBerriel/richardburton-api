package br.edu.ifrs.canoas.transnacionalidades.richardburton.util;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWT {

    static private final long TOKEN_TTL_MS = 3600000;
    static private final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS512;
    static private final Key KEY = new SecretKeySpec(Global.getSecret(), ALGORITHM.getJcaName());

    static public String issueToken(String subject, String issuer, boolean admin) {

        JwtBuilder builder = Jwts.builder();

        Date now = new Date();
        Date exp = new Date(now.getTime() + TOKEN_TTL_MS);

        builder.setSubject(subject);
        builder.setIssuer(issuer);
        builder.setIssuedAt(now);
        builder.setExpiration(exp);
        builder.signWith(ALGORITHM, KEY);
        builder.claim("admin", admin);

        return builder.compact();
    }

    public static Claims decodeToken(String token) throws JwtException {

        return Jwts.parser().setSigningKey(Global.getSecret()).parseClaimsJws(token).getBody();
    }

}