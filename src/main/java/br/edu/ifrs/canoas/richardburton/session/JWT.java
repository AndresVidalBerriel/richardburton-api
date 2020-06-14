package br.edu.ifrs.canoas.richardburton.session;

import io.jsonwebtoken.*;

import java.security.*;
import java.util.Date;

import br.edu.ifrs.canoas.richardburton.RichardBurton;

public class JWT {

    private static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS512;

    public static String issueToken(String subject, boolean admin) {

        JwtBuilder builder = Jwts.builder();

        Date now = new Date();
        Date exp = new Date(now.getTime() + RichardBurton.getJWTTokenTTLMS());

        builder.setSubject(subject);
        builder.setIssuer(RichardBurton.getJWTTokenIssuer());
        builder.setIssuedAt(now);
        builder.setExpiration(exp);
        builder.signWith(ALGORITHM, RichardBurton.getJWTSecret());
        builder.claim("admin", admin);

        return builder.compact();
    }

    public static Claims decodeToken(String token) throws JwtException {

        String issuer = RichardBurton.getJWTTokenIssuer();
        Key secret = RichardBurton.getJWTSecret();

        return Jwts.parser().requireIssuer(issuer).setSigningKey(secret).parseClaimsJws(token).getBody();
    }

}