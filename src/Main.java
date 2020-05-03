import java.io.*;

public class Main {


    public static void main(String[] args) throws IOException {
        Logger.start();

        File file = new File("src/cat.jpg");
        EncryptingKey key = new EncryptingKey(EncryptingKey.generateNewKey(100));
        SecureByteShuffler.setEncryptKey(key);

        Encrypter encrypter = new Encrypter();
        Decrypter decrypter = new Decrypter();

        encrypter.encrypt(file);

        decrypter.decrypt(new File("src/encrypted-cat.jpg"));

        System.out.println();
        System.out.println(SecureByteShuffler.getSimilarity());


    }
}
