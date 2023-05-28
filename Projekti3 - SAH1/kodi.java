public class kodi {
    public static void main(String[] args) {
        String input = "Hello, world!";
        String sha1Hash = sha1(input);
        System.out.println("SHA-1 Hash: " + sha1Hash);
    }

    public static String sha1(String input) {
        int[] words = prepareMessage(input);
        int[] state = initialState();

        int[] messageSchedule = new int[80];
        for (int i = 0; i < words.length; i += 16) {
            int[] w = new int[80];
            System.arraycopy(words, i, w, 0, 16);

            for (int t = 16; t < 80; t++) {
                w[t] = leftRotate(w[t - 3] ^ w[t - 8] ^ w[t - 14] ^ w[t - 16], 1);
            }

            int[] temp = new int[5];
            System.arraycopy(state, 0, temp, 0, 5);

            for (int t = 0; t < 80; t++) {
                int f, k;
                if (t < 20) {
                    f = (temp[1] & temp[2]) | (~temp[1] & temp[3]);
                    k = 0x5A827999;
                } else if (t < 40) {
                    f = temp[1] ^ temp[2] ^ temp[3];
                    k = 0x6ED9EBA1;
                } else if (t < 60) {
                    f = (temp[1] & temp[2]) | (temp[1] & temp[3]) | (temp[2] & temp[3]);
                    k = 0x8F1BBCDC;
                } else {
                    f = temp[1] ^ temp[2] ^ temp[3];
                    k = 0xCA62C1D6;
                }

                int tempVal = leftRotate(temp[0], 5) + f + temp[4] + k + w[t];
                temp[4] = temp[3];
                temp[3] = temp[2];
                temp[2] = leftRotate(temp[1], 30);
                temp[1] = temp[0];
                temp[0] = tempVal;
            }

            for (i = 0; i < 5; i++) {
                state[i] += temp[i];
            }
        }

        StringBuilder hexString = new StringBuilder();
        for (int val : state) {
            hexString.append(String.format("%08x", val));
        }

        return hexString.toString();
    }

    private static int[] prepareMessage(String input) {
        byte[] message = input.getBytes();
        int messageLength = message.length;
        int paddedLength = ((messageLength + 8) / 64 + 1) * 64;

        byte[] paddedMessage = new byte[paddedLength];
        System.arraycopy(message, 0, paddedMessage, 0, messageLength);
        paddedMessage[messageLength] = (byte) 0x80;

        long messageBits = (long) messageLength * 8;
        for (int i = 0; i < 8; i++) {
            paddedMessage[paddedLength - 8 + i] = (byte) (messageBits >>> (56 - i * 8));
        }

        int[] words = new int[paddedLength / 4];
        for (int i = 0; i < words.length; i++) {
            int word = 0;
            for (int j = 0; j < 4; j++) {
                word |= (paddedMessage[i * 4 + j] & 0xFF) << (24 - j * 8);
            }
            words[i] = word;
        }

        return words;
    }

    private static int[] initialState() {
        int[] state = new int[5];
        state[0] = 0x67452301;
        state[1] = 0xEFCDAB89;
        state[2] = 0x98BADCFE;
        state[3] = 0x10325476;
        state[4] = 0xC3D2E1F0;
        return state;
    }

    private static int leftRotate(int value, int shift) {
        return (value << shift) | (value >>> (32 - shift));
    }
}
