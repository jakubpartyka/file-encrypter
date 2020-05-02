import java.io.*;
import java.util.ArrayList;

public class Main {
    private static ArrayList<Byte> data = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Logger.readConfigFile();

        File input = new File("src/cat.jpg");

        Encrypter encrypter = new Encrypter();
        if(encrypter.encrypt(input))
            System.out.println("encryption successful");
        else
            System.out.println("failed to encrypt");
    }
}
