import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SAES {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION_ECB = "AES/ECB/NoPadding";
    private static final String TRANSFORMATION_CTR = "AES/CTR/NoPadding";

    public static void main(String[] args) {
        try {
            // Per te importuar nga text file, vendoset path-i
            String celesi = "0123456789abcdef";
            String mesazhi = "0123456789abcdef";

            System.out.println("Mesazhi: " + mesazhi);

            byte[] encryptedBytes = encrypt(mesazhi, celesi);
            System.out.println("Mesazhi i enkriptuar: " + bytesToHex(encryptedBytes));

            String decryptedMessage = decrypt(encryptedBytes, celesi);
            System.out.println("Mesazhi i dekriptuar: " + decryptedMessage);

            byte[] encryptedBytesCTR = encryptCTR(mesazhi, celesi);
            System.out.println("Mesazhi i enkriptuar (CTR): " + bytesToHex(encryptedBytesCTR));

            String decryptedMessageCTR = decryptCTR(encryptedBytesCTR, celesi);
            System.out.println("Mesazhi i dekriptuar (CTR): " + decryptedMessageCTR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] encrypt(String mesazhi, String celesi) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(celesi.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION_ECB);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] mesazhiBytes = mesazhi.getBytes();
        byte[] encryptedBytes = cipher.doFinal(mesazhiBytes);

        return encryptedBytes;
    }

    public static String decrypt(byte[] encryptedBytes, String celesi) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(celesi.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION_ECB);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedMessage = new String(decryptedBytes);

        return decryptedMessage;
    }

    public static byte[] encryptCTR(String mesazhi, String celesi) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(celesi.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION_CTR);

        byte[] ivBytes = new byte[16]; // Initialization Vector (IV) for CTR mode
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

        byte[] mesazhiBytes = mesazhi.getBytes();
        byte[] encryptedBytes = cipher.doFinal(mesazhiBytes);

        return encryptedBytes;
    }

    public static String decryptCTR(byte[] encryptedBytes, String celesi) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(celesi.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION_CTR);

        byte[] ivBytes = new byte[16]; // IV vektori per CTR
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedMessage = new String(decryptedBytes);

        return decryptedMessage;
    }

    public static String bytesToHex(byte[] bytes) {
        // Konverteri
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
