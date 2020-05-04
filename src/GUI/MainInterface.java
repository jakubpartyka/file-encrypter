package GUI;

import Encryption.EncryptionKey;
import Encryption.IncorrectKeyException;
import Encryption.SecureByteShuffler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
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
    private JTextField size;
    private JButton generateButton;
    private JPanel panel1;
    private JPanel decryptPanel;
    private JPanel encryptPanel;
    private JButton encryptButton;
    private JCheckBox deleteOriginalFilesCheckBox;
    private JPanel panel2;
    private JButton decryptButton;
    private JCheckBox deleteSourceFilesCheckBox;


    @Override
    public void run() {
        //initialize main frame
        frame = new JFrame("File Encrypter");
        frame.setResizable(false);
        frame.setSize(600,500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);   //todo operation on widow close
        frame.add(mainPanel);

        //todo remove
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        //FILE CHOOSER FOR ENCRYPT
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setApproveButtonText("Encrypt");
        jfc.setControlButtonsAreShown(false);
        jfc.setMultiSelectionEnabled(true);

        panel1.setLayout(new BorderLayout());
        panel1.add(new JLabel("Choose file(s) to encrypt"),BorderLayout.NORTH);
        panel1.add(jfc,BorderLayout.CENTER);



        //FILE CHOOSER FOR DECRYPT
        JFileChooser jfc2 = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc2.setApproveButtonText("Decrypt");
        jfc2.setControlButtonsAreShown(false);
        jfc2.setMultiSelectionEnabled(true);

        panel2.setLayout(new BorderLayout());
        panel2.add(new JLabel("Choose file(s) to decrypt"),BorderLayout.NORTH);
        panel2.add(jfc2,BorderLayout.CENTER);







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

        //SELECT KEY SAVE LOCATION
        saveButton.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setFileFilter(new FileNameExtensionFilter("binary files", "bin"));
            int result = jfc.showSaveDialog(null);

            if(result == JFileChooser.APPROVE_OPTION){
                File saveFile = jfc.getSelectedFile();

                if(!saveFile.getName().endsWith(".bin"))
                    saveFile = new File(saveFile.getAbsolutePath() + ".bin");

                savePath.setText(jfc.getSelectedFile().getAbsolutePath());

                EncryptionKey.setSaveFile(saveFile);
            }
        });

        //GENERATE NEW KEY
        generateButton.addActionListener(e -> {
            if(EncryptionKey.getSaveFile() == null){
                JOptionPane.showMessageDialog(null,"Choose save directory first","Choose destination",JOptionPane.INFORMATION_MESSAGE);
            }
            //save key to file
            if(EncryptionKey.generateNewKeyToFile(Integer.parseInt(size.getText())))
                JOptionPane.showMessageDialog(null,"Key generated successfully","Success",JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(null,"An error occurred while creating new encryption key","Failed to generate new key",JOptionPane.WARNING_MESSAGE);

            EncryptionKey.setSaveFile(null);
            savePath.setText("select save directory");
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
