package Encryption;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class EncryptionKey {
    private byte [] key;
    static private File saveFile = null;

    public EncryptionKey(File keyFile) throws IOException, IncorrectKeyException {
        try {
            InputStream inputStream = new FileInputStream(keyFile);
            byte[] data = new byte[(int) keyFile.length()];
            inputStream.read(data);
            this.key = data;
            inputStream.close();
        }
        catch (Exception e){
            if(e.getClass().equals(IOException.class))
                throw new IOException(e.getMessage());
            key = new byte[0];
            throw new IncorrectKeyException("Incorrect data detected in key file");
        }
    }

    EncryptionKey(byte[] key) {
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

    public static boolean generateNewKeyToFile(int size){
        try {
            File destination = saveFile;
            destination.createNewFile();
            byte [] key = generateNewKey(size);
            OutputStream stream = new FileOutputStream(destination);
            stream.write(key);
            stream.close();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    static public void setSaveFile(File file) {
        saveFile = file;
    }

    public static File getSaveFile() {
        return saveFile;
    }
}
