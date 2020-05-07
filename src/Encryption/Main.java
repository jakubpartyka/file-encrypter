package Encryption;

import javax.swing.*;
import java.io.*;
import  GUI.*;

public class Main {
    //todo progress bar
    //todo choose if source files should be deleted

    public static void main(String[] args) throws IOException {
        Logger.start();

        MainInterface mainInterface = new MainInterface();
        SwingUtilities.invokeLater(mainInterface);
    }
}
