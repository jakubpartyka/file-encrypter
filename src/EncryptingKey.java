import java.io.*;

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

    byte [] getKey(){
        return key;
    }

    int getKeyLength(){
        return key.length;
    }


}
