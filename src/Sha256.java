import java.security.MessageDigest;

public class Sha256 {
    /**
     * Applies SHA256 (Secure Hash Algorithm) to the input string.
     * @param input  the string to be hashed
     * @return  the hashed string
     */
    public static String applySha256(String input) {
        // apply SHA256 (Secure Hash Algorithm) to the input string
        try {
            MessageDigest digest = MessageDigest.getInstance("Sha-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer(); // hexadecimal hash

            for (byte hash1 : hash) {
                String hex = Integer.toHexString(0xff & hash1);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}