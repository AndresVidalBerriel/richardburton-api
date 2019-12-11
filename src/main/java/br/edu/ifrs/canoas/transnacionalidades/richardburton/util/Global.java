package br.edu.ifrs.canoas.transnacionalidades.richardburton.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Global {

    static private byte[] secret = null;

    public static byte[] getSecret() {

        if (secret == null) {
            try {

                secret = Files.readAllBytes(Paths.get("/opt/richardburton/.secret"));

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        return secret;
    }
}