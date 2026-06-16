package DataAccess;

import java.security.MessageDigest;

public class Criptografia {

    public static String sha256(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(senha.getBytes("UTF-8"));

            StringBuilder resultado = new StringBuilder();

            for (byte b : bytes) {
                resultado.append(String.format("%02x", b));
            }

            return resultado.toString();

        } catch (Exception erro) {
            throw new RuntimeException("Erro ao criptografar senha: " + erro.getMessage());
        }
    }
}   