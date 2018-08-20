package encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Enigmo {

    private static final byte[] IV = new byte[] {-119, -45, 23, -63, 127, -74, 63, 51, 18, -116, -10, -87, -22, -115, 58, 96};
    private static final IvParameterSpec IV_SPEC = new IvParameterSpec(IV);
    public static final int SECRET_SIZE = 64;
    private static final String CIPHER_ALGORITHM = "AES/CBC/NoPadding";
    private static final String KEYGEN_ALGORITHM = "AES";

    private Cipher cipher;
    private MessageDigest sha;
    private SecretFerry ferry;

    public Enigmo() {
        //Canstructs all the needed Objects
        try {
            this.cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            this.sha = MessageDigest.getInstance("SHA-256");
            this.ferry = new SecretFerry(SECRET_SIZE * 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] encodeString(String key, String decoded) {
        try {
            //Converts the #decoded# string with SecretFerry to a byte[] the doubled size
            byte[] converted = this.ferry.encode(decoded);
            return encodeBytes(getKey(key), converted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decodeString(String key, byte[] encoded) {
        try {
            byte[] decoded = decodeBytes(getKey(key), encoded);
            return this.ferry.decode(decoded);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] encodeBytes(SecretKeySpec keySpec, byte[] decoded) {
        try {
            this.cipher.init(Cipher.ENCRYPT_MODE, keySpec, IV_SPEC);
            return this.cipher.doFinal(decoded);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] decodeBytes(SecretKeySpec keySpec, byte[] encoded) {
        try {
            this.cipher.init(Cipher.DECRYPT_MODE, keySpec, IV_SPEC);
            return this.cipher.doFinal(encoded);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private SecretKeySpec getKey(String string) {
        return new SecretKeySpec(this.sha.digest(string.getBytes(StandardCharsets.UTF_8)), KEYGEN_ALGORITHM);
    }

}
