package Encryption;

import GUI.Main;

import javax.swing.*;
import java.io.File;

public class EncryptionExecution extends SwingWorker {
    private File [] files;
    private long totalSize;

    public EncryptionExecution(File[] files, long totalSize) {
        this.files = files;
        this.totalSize = totalSize;
    }


    @Override
    protected Object doInBackground() throws Exception {
        Main.setFileOperationInProgress(true);

        Encrypter encrypter = new Encrypter();
        int counter = 0;
        for (File file : files) {
            encrypter.encrypt(file);
            setProgress(++counter*100/files.length);
        }

        Main.setFileOperationInProgress(false);
        return null;
    }
}
