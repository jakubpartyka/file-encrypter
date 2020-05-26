package GUI;

import javax.swing.*;
import java.io.File;

public class EncryptionProgress extends JFrame implements Runnable {
    private JProgressBar progressBar1;
    private JProgressBar progressBar2;
    private JLabel totalProgress;
    private JPanel mainPanel;
    private JLabel header;
    private JLabel currentlyProcessing;
    private JLabel usingKey;
    private JLabel currentFileLabel;

    private long currentFileLength;
    private long totalBytesToProcess;
    private long totalBytesProcessed;
    private long lastCurrentPosition = 0;

    private boolean animate = true;

    @Override
    public void run() {
        //prepare frame
        this.setSize(400,250);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.add(mainPanel);

        this.setVisible(false);

    }

    public EncryptionProgress(String keyUsed, long totalBytesToProcess) {
        this.totalBytesToProcess = totalBytesToProcess;
        usingKey.setText("using key: " + keyUsed);
    }

    public void setCurrentFile(File currentFile) {
        currentFileLength = currentFile.length();
        currentlyProcessing.setText("Currently processing: " + currentFile.getAbsolutePath());
        lastCurrentPosition = 0;
    }

    void stopAnimation(){
        animate = false;
    }

    public void setProgress(long currentFilePosition){
        int currentPercent = (int) (100*currentFilePosition/currentFileLength);
        totalBytesProcessed += (currentFilePosition - lastCurrentPosition);
        int totalPercent = (int) (100 * totalBytesProcessed / totalBytesToProcess) ;

        progressBar1.setValue(currentPercent);
        currentFileLabel.setText("Current file progress: " + (currentPercent > 9 ? (currentPercent + '%') : (" " + currentPercent + '%') ));

        progressBar2.setValue(totalPercent);
        totalProgress.setText("Total progress: " + (totalPercent > 9 ? (totalPercent + '%') : (" " + totalPercent + '%') ));

        this.repaint();
    }

    public void setTotalBytesToProcess(long totalBytesToProcess) {
        this.totalBytesToProcess = totalBytesToProcess;
    }

    public void setUsingKey(String usingKey) {
        this.usingKey.setText("Using key: " + usingKey);
    }

    private void runAnimation(){
        int counter = 1;
        while (animate) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //reset dot
            if (counter % 5 == 0) {
                header.setText("Encryption in progress .");
                counter = 1;
                continue;
            }

            header.setText(header.getText() + '.');
            counter++;
        }
    };
}
