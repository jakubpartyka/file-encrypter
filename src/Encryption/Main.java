package Encryption;

import javax.swing.*;
import java.io.*;
import  GUI.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Logger.start();

        MainInterface mainInterface = new MainInterface();
        SwingUtilities.invokeLater(mainInterface);

    }
}
