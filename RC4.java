public class RC4 {
    private byte[] S;

    public RC4() {
        S = new byte[256];
    }

    private void initialize(byte[] key) {
        byte[] K = new byte[256];

        for (int i = 0; i < 256; i++) {
            S[i] = (byte) i;
            K[i] = key[i % key.length];
        }

        int j = 0;
        for (int i = 0; i < 256; i++) {
            j = (j + S[i] + K[i]) & 0xFF;
            swap(i, j);
        }
    }

    private void swap(int i, int j) {
        byte temp = S[i];
        S[i] = S[j];
        S[j] = temp;
    }

    public byte[] encrypt(byte[] plaintext, byte[] key) {
        initialize(key);
        byte[] ciphertext = new byte[plaintext.length];
	int i = 0, j = 0;
        for (int k = 0; k < plaintext.length; k++) {
            i = (i + 1) & 0xFF;
            j = (j + S[i]) & 0xFF;
            swap(i, j);
            int t = (S[i] + S[j]) & 0xFF;
            ciphertext[k] = (byte) (plaintext[k] ^ S[t]);
        }
        return ciphertext;
    }

    public static void main(String[] args) {
        String keyString = "VlasenkoKNU";
        String plaintext = "Test text";
	byte[] key = keyString.getBytes();
        System.out.println("Plaintext original: " + new String(plaintext.getBytes()));

        RC4 rc4 = new RC4();
        byte[] encrypted = rc4.encrypt(plaintext.getBytes(),key);

        byte[] decrypted = rc4.encrypt(encrypted,key);

        System.out.println("Encrypted: " + new String(encrypted));
    	System.out.println("Decrypted: " + new String(decrypted));

        System.out.println("OriginalSHEX: " + bytesToHex(plaintext.getBytes()));
        System.out.println("DecryptedHEX: " + bytesToHex(decrypted));
        System.out.println("EncryptedHEX: " + bytesToHex(encrypted));

    }

    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes();
    public static String bytesToHex(byte[] bytes) {
	byte[] hexChars = new byte[bytes.length * 2];
	for (int j = 0; j < bytes.length; j++) {
	    int v = bytes[j] & 0xFF;
	    hexChars[j * 2] = HEX_ARRAY[v >>> 4];
	    hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
	}
	return new String(hexChars);
    }
}
