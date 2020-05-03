package GUI;

import javax.swing.*;

@SuppressWarnings("FieldCanBeLocal")
public class MainInterface implements Runnable {
    private JFrame frame;
    private JTabbedPane tabbedPane1;
    private JPanel panel1;


    @Override
    public void run() {
        //initialize main frame
        frame = new JFrame("File Encrypter");
        frame.setResizable(false);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);   //todo operation on widow close


        frame.setVisible(true);
    }
}