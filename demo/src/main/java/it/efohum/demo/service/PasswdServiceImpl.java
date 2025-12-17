package it.efohum.demo.service;

import org.springframework.stereotype.Service;

@Service
public class PasswdServiceImpl implements PasswdService {
    @Override
    public String hashPassword(String plainPassword) {
        // Simple hashing example (not secure, for demonstration purposes only)
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(plainPassword.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
}
