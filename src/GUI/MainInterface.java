package GUI;

import Encryption.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("FieldCanBeLocal")
public class MainInterface implements Runnable {
    public JFrame frame;
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
    private JCheckBox deleteSourceFilesCheckBox;
    private JButton decryptButton;
    private JButton refresh;
    private JButton refresh2;
    public JProgressBar progressBar1;
    private JTextArea specifyPathToKeyTextArea;
    private JButton browseDefaultKey;
    private JFileChooser jfc;
    private JFileChooser jfc2;

    private HashSet<File> files = new HashSet<>();

    public MainInterface() {
        SecureByteShuffler.connectedInterface = this;
    }

    //progress counting
    private long totalSize = 0;
    private long currentSize = 0;
    public static AtomicInteger integer = new AtomicInteger();


    @Override
    public void run() {
        //initialize main frame
        frame = new JFrame("File Encrypter");
        frame.setResizable(false);
        frame.setSize(600,500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);   //todo operation on widow close
        frame.add(mainPanel);

        integer.set(0);

        //todo remove
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        //FILE CHOOSER FOR ENCRYPT
        jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setApproveButtonText("Encrypt");
        jfc.setControlButtonsAreShown(false);
        jfc.setMultiSelectionEnabled(true);
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        panel1.setLayout(new BorderLayout());
        panel1.add(new JLabel("Choose file(s) to encrypt"),BorderLayout.NORTH);
        panel1.add(jfc,BorderLayout.CENTER);

        //FILE CHOOSER FOR DECRYPT
        jfc2 = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc2.setApproveButtonText("Decrypt");
        jfc2.setControlButtonsAreShown(false);
        jfc2.setMultiSelectionEnabled(true);
        jfc2.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        panel2.setLayout(new BorderLayout());
        panel2.add(new JLabel("Choose file(s) to decrypt"),BorderLayout.NORTH);
        panel2.add(jfc2,BorderLayout.CENTER);

        specifyPathToKeyTextArea.setBackground(new Color(0,0,0,0));

        addActionListeners();

        frame.setVisible(true);
    }

    private void addActionListeners(){
        //WINDOW LISTENER FOR CLOSE OPERATION
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(Main.isFileOperationInProgress()){
                    JOptionPane.showMessageDialog(null,"Wait for encrypt/decrypt operation to finish.","ENCRYPTION IN PROGRESS",JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                this.windowClosed(e);
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                super.windowDeactivated(e);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });

        //RELOAD FILE VIEW BUTTONS
        refresh.addActionListener(e -> refreshFileChoosers());
        refresh2.addActionListener(e -> refreshFileChoosers());

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

        //ENCRYPT BUTTON
        encryptButton.addActionListener(e -> {
            File [] files = jfc.getSelectedFiles();

            //check if any files selected
            if(files.length == 0) {
                JOptionPane.showMessageDialog(null, "Please select files to encrypt", "EMPTY SELECTION", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            //prepare file list for encryption
            totalSize = prepareFiles(files);
            EncryptionExecution ex = new EncryptionExecution(files,totalSize);
            ex.addPropertyChangeListener(
                    evt -> {
                        if ("progress".equals(evt.getPropertyName())) {
                            progressBar1.setValue((Integer)evt.getNewValue());
                            refreshFileChoosers();
                        }
                    });
            progressBar1.setValue(0);
            ex.execute();

        });

        //DECRYPT BUTTON
        decryptButton.addActionListener(e -> {
            File [] files = jfc2.getSelectedFiles();

            //check if any files selected
            if(files.length == 0) {
                JOptionPane.showMessageDialog(null, "Please select files to decrypt", "EMPTY SELECTION", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            prepareFiles(files);

            DecryptionExecution ex = new DecryptionExecution(files);
            ex.addPropertyChangeListener(
                    evt -> {
                        if ("progress".equals(evt.getPropertyName())) {
                            progressBar1.setValue((Integer)evt.getNewValue());
                            refreshFileChoosers();
                        }
                    });
            progressBar1.setValue(0);
            ex.execute();

            refreshFileChoosers();
        });
    }

    private void refreshFileChoosers() {
        File tmp = jfc.getCurrentDirectory();
        jfc.setCurrentDirectory(new File("/"));
        jfc.setCurrentDirectory(tmp);

        File tmp2 = jfc2.getCurrentDirectory();
        jfc2.setCurrentDirectory(new File("/"));
        jfc2.setCurrentDirectory(tmp2);
    }

    private long prepareFiles(File [] input){
        long totalSize = 0;

        List<File> directories = new ArrayList<>();

        //fist loop for JFileChooser input array
        for (File file : input) {
            if(file.isFile()) {
                files.add(file);
            }
            else
                directories.add(file);
        }

        while (!directories.isEmpty()) {
            File file = directories.remove(0);

            try {
                for (File listedFile : file.listFiles()) {
                    if (listedFile.isDirectory())
                        directories.add(listedFile);
                    else {
                        files.add(listedFile);
                        directories.remove(listedFile);
                    }
                }
            }catch (NullPointerException e){
                continue;
            }
        }

        for (File file : files) {
            totalSize += file.length();
        }

        return totalSize;
    }

    //todo list of ignored files / extensions
}
