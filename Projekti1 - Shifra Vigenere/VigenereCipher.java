public class VigenereCipher {
	
	// Metoda per enkriptim
	    public static String encrypt(String text, final String key)
	    {
	        String res = "";
	        text = text.toUpperCase();
	        for (int i = 0, j = 0; i < text.length(); i++)
	        {
	            char c = text.charAt(i);
	            if (c < 'A' || c > 'Z')
	                continue;
	            res += (char) ((c + key.charAt(j) - 2 * 'A') % 26 + 'A');
	            j = ++j % key.length();
	        }
	        return res;
	    }
	 
	    // Metoda per dekriptim
	    public static String decrypt(String text, final String key)
	    {
	        String res = "";
	        text = text.toUpperCase();
	        for (int i = 0, j = 0; i < text.length(); i++)
	        {
	            char c = text.charAt(i);
	            if (c < 'A' || c > 'Z')
	                continue;
	            res += (char) ((c - key.charAt(j) + 26) % 26 + 'A');
	            j = ++j % key.length();
	        }
	        return res;
	    }
	 
	    public static void main(String[] args)
	    {
	        String key = "FSHMN";
	        String message = "DATASECURITY";
	        String encryptedMsg = encrypt(message, key);
	        System.out.println("Mesazhi: " + message);
	        System.out.println("Mesazhi i enkriptuar: " + encryptedMsg);
	        System.out.println("Mesazhi i dekriptuar: " + decrypt(encryptedMsg, key));
	    }
	}