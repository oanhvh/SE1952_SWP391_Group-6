/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 *
 * @author NHThanh
 */
public class PasswordValidatorService {

    public boolean validatePassword(String storedHash, String submittedPlain) { //storeHash mk trong database
        if (storedHash == null || submittedPlain == null) {
            return false;
        }
        try {
            String hash = sha256(submittedPlain);
            return storedHash.equalsIgnoreCase(hash);
        } catch (Exception e) {
            return false;
        }
    }

    private static String sha256(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder hex = new StringBuilder();
        for (byte b : hash) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }
    
}
