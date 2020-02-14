package br.edu.ifrs.canoas.richardburton.util;

import org.jboss.resteasy.util.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Strings {

    public static String digest(String string) {

        try {

            MessageDigest md = MessageDigest.getInstance("SHA3-512");
            byte[] bytes = Hex.decodeHex(string);
            byte[] hashed = md.digest(bytes);
            return Hex.encodeHex(hashed);

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
            return null;
        }

    }

}