import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Logger.readConfigFile();

        File file = new File("src/cat.jpg");

        Encrypter encrypter = new Encrypter();
        Decrypter decrypter = new Decrypter();

        encrypter.encrypt(file);

        decrypter.decrypt(new File("src/encrypted-cat.jpg"));

    }
}
