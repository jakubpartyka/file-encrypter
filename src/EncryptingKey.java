import java.io.*;
import java.util.Arrays;
import java.util.Random;

@SuppressWarnings("ResultOfMethodCallIgnored")
class EncryptingKey{
    private final byte [] key;

    EncryptingKey(File keyFile) throws IOException {
        InputStream inputStream = new FileInputStream(keyFile);
        byte [] data = new byte[(int) keyFile.length()];
        inputStream.read(data);
        this.key = data;
        inputStream.close();
    }

    EncryptingKey(byte[] key) {
        this.key = key;
    }

    @SuppressWarnings("SameParameterValue")
    static byte[] generateNewKey(int keySize) {
        byte [] key = new byte[keySize];
        new Random((long) (Math.random() * Long.MAX_VALUE)).nextBytes(key);
        return key;
    }

    byte [] getKey(){
        return key;
    }

    int getKeyLength(){
        return key.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(key);
    }
}
