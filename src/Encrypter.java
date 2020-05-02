import java.io.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

class Encrypter {
    private final static Logger LOGGER = Logger.getLogger(Encrypter.class.getName());

    boolean encrypt(File input){
        try {
            //encrypt method
            File output = new File("src/encrypted-" + input.getName());

            clearFileContent(output);

            byte [] bytes = new byte[(int) input.length()];
            InputStream inputStream = new FileInputStream(input);
            System.out.println(inputStream.read(bytes) + " bytes read and encrypted from file: " + input.getName());

            byte start = bytes[0];
            System.arraycopy(bytes, 1, bytes, 0, bytes.length - 1);
            bytes[bytes.length - 1] = start;

            OutputStream outputStream = new FileOutputStream(output);
            outputStream.write(bytes,0,bytes.length);

            inputStream.close();
            outputStream.close();

        } catch (Exception e){
            LOGGER.setLevel(Level.SEVERE);
            LOGGER.log(new LogRecord(LOGGER.getLevel(),"Failed to encrypt file " + input.getPath() + " due to error: \n" + e.getMessage() + "\n"));
        }
        return true;
    }

    private static void clearFileContent(File file){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            LOGGER.setLevel(Level.WARNING);
            LOGGER.log(new LogRecord(LOGGER.getLevel(),"an error occurred while clearing file " + file.getPath()));
        }
    }
}
