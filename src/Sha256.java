import java.security.MessageDigest;

public class Sha256 {
    public static String applySha256(String input){
        // apply SHA256 (Secure Hash Algorithm) to the input string
        try {
            MessageDigest digest = MessageDigest.getInstance("Sha-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer(); // hexadecimal hash

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}