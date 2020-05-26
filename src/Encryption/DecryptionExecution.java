package Encryption;

import GUI.Main;

import javax.swing.*;
import java.io.File;

public class DecryptionExecution extends SwingWorker {
    private File [] files;

    public DecryptionExecution(File[] files) {
        this.files = files;
    }


    @Override
    protected Object doInBackground() throws Exception {
        Main.setFileOperationInProgress(true);

        Decrypter decrypter = new Decrypter();
        int counter = 0;
        for (File file : files) {
            decrypter.decrypt(file);
            setProgress(++counter*100/files.length);
        }

        Main.setFileOperationInProgress(false);
        return null;
    }
}
