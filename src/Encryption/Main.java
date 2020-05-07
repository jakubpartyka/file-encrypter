package Encryption;

import javax.swing.*;
import java.io.*;
import  GUI.*;

public class Main {
    private static boolean fileOperationInProgress = false;
    //todo progress bar
    //todo choose if source files should be deleted

    public static void main(String[] args) throws IOException {
        Logger.start();

        MainInterface mainInterface = new MainInterface();
        SwingUtilities.invokeLater(mainInterface);
    }

    public static boolean isFileOperationInProgress() {
        return fileOperationInProgress;
    }

    public static void setFileOperationInProgress(boolean fileOperationInProgress) {
        Main.fileOperationInProgress = fileOperationInProgress;
    }
}
