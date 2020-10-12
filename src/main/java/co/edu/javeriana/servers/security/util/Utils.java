package co.edu.javeriana.servers.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Utils {

    public static void main(String[] args) {
        SecureRandom random = null;
        String clientId = "c56eceb0-88f7-4b41-a495-dc8c92e0f449";

        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println("Hash {} --> " + encoder.encode(clientId));
    }

}
