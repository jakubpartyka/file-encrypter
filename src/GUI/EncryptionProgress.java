package GUI;

import javax.swing.*;
import java.io.File;

public class EncryptionProgress {
    private JProgressBar progressBar1;
    private JProgressBar progressBar2;
    private JLabel totalProgress;
    private JPanel fileProgress;
    private JLabel header;
    private JLabel currentlyProcessing;
    private JLabel usingKey;
    private Thread dotAnimation;

    private File currentFile;
    private long totalBytes;

    private boolean animate = true;

    public static void main(String[] args) {
        new EncryptionProgress("xd",10);
    }


    EncryptionProgress(String keyUsed,long totalBytes){
        this.totalBytes = totalBytes;
        usingKey.setText("using key: " + keyUsed);

        JFrame frame = new JFrame("Encryption in progress");
        frame.setSize(400,250);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);

        dotAnimation = new Thread(()->{
            int counter = 1;

            while (animate){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {e.printStackTrace();}

                if(counter%5 == 0){
                    header.setText("Encryption in progress .");
                    counter = 1;
                    continue;
                }

                header.setText(header.getText() + '.');
                counter++;
            }
        });
        dotAnimation.start();

        frame.add(fileProgress);
        frame.setVisible(true);
    }

    public void setCurrentFile(File currentFile) {
        this.currentFile = currentFile;
        currentlyProcessing.setText("Currently processing: " + currentFile.getAbsolutePath());
    }

    public void stopAnimation(){
        animate = false;
    }
}
