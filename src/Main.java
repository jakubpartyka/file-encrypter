import java.io.*;
import java.util.Arrays;

public class Main {
    static byte [] before;
    static byte [] after;

    public static void main(String[] args) throws IOException {
        Logger.readConfigFile();

        File file = new File("src/cat.jpg");
        EncryptingKey key = new EncryptingKey(EncryptingKey.generateNewKey(256));
        SecureByteShuffler.setEncryptKey(key);
        System.out.println(key);

        Encrypter encrypter = new Encrypter();
        Decrypter decrypter = new Decrypter();

        encrypter.encrypt(file);

        decrypter.decrypt(new File("src/encrypted-cat.jpg"));

        boolean match = Arrays.equals(before, after);
        System.out.println("\narrays match: " + match);
        if(!match){
            System.out.println(Arrays.toString(before));
            System.out.println(Arrays.toString(after));
        }
    }
}
