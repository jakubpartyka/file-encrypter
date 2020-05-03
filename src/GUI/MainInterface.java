package GUI;

import Encryption.EncryptionKey;
import Encryption.IncorrectKeyException;
import Encryption.SecureByteShuffler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("FieldCanBeLocal")
public class MainInterface implements Runnable {
    private JFrame frame;
    private JTabbedPane tabbedPane1;
    private JPanel mainPanel;
    private JTextField fileInputField;
    private JButton browseButton;
    private JButton saveButton;
    private JTextField savePath;
    private JTextField a256TextField;
    private JButton generateButton;


    @Override
    public void run() {
        //initialize main frame
        frame = new JFrame("File Encrypter");
        frame.setResizable(false);
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);   //todo operation on widow close
        frame.add(mainPanel);

        addActionListeners();

        frame.setVisible(true);
    }

    private void addActionListeners(){
        //CHOOSE FILE FROM SYSTEM
        browseButton.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setMultiSelectionEnabled(false);
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);

            //filter bin files
            FileNameExtensionFilter filter = new FileNameExtensionFilter("binary files", "bin");
            jfc.setFileFilter(filter);

            File keyFile;
            int result = jfc.showOpenDialog(null);

            //get selected file and set key
            if(result == JFileChooser.APPROVE_OPTION){
                keyFile = jfc.getSelectedFile();                            //get selected file
                fileInputField.setText(keyFile.getAbsolutePath());          //set file path
                try {
                    SecureByteShuffler.setEncryptKey(new EncryptionKey(keyFile));
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null,"Failed to read key file!\nError: " + e1.getMessage(),"KEY READING FAILED",JOptionPane.WARNING_MESSAGE);
                }       //todo check if old key is not overwritten
                catch (IncorrectKeyException e2) {
                    JOptionPane.showMessageDialog(null,"Incorrect key file!\nError: " + e2.getMessage(),"INCORRECT ENCRYPTION KEY",JOptionPane.WARNING_MESSAGE);
                }
                //todo save choosen key location ???
            }
        });
    }
}
