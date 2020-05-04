package Encryption;

import javax.swing.*;
import java.io.*;
import  GUI.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Logger.start();

        MainInterface mainInterface = new MainInterface();
        SwingUtilities.invokeLater(mainInterface);

        //start of test
        try {
            Thread.sleep(500000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(1);

        //end of test

        File file = new File("src/cat.jpg");
        EncryptionKey key = new EncryptionKey(EncryptionKey.generateNewKey(100));
        SecureByteShuffler.setEncryptKey(key);

        Encrypter encrypter = new Encrypter();
        Decrypter decrypter = new Decrypter();

        encrypter.encrypt(file);

        decrypter.decrypt(new File("src/encrypted-cat.jpg"));

        System.out.println();
        System.out.println(SecureByteShuffler.getSimilarity());


    }
}
