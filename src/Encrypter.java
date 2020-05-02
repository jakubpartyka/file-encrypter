import java.io.*;

class Encrypter extends FileAccessor{

    private byte [] bytes;
    private File output;
    private File input;

    Encrypter() {
        super("ENCR");
    }

    boolean encrypt(File input){
        try {
            //encrypt method
            output = new File("src/encrypted-" + input.getName());
            this.input = input;

            clearFileContent(output);

            bytes = new byte[(int) input.length()];
            InputStream inputStream = new FileInputStream(input);
            System.out.println(inputStream.read(bytes) + " bytes read and encrypted from file: " + input.getName());

            encrypt();

            saveEncryptedFile();

            inputStream.close();

        } catch (Exception e){
            log("failed to encrypt file: " + input.getName() + " due to error: " + e.getMessage());
        }
        return true;
    }

    private void encrypt() {
        byte start = bytes[0];
        System.arraycopy(bytes, 1, bytes, 0, bytes.length - 1);
        bytes[bytes.length - 1] = start;
    }

    private void saveEncryptedFile(){
        try {
            //save output
            OutputStream outputStream = new FileOutputStream(output);
            outputStream.write(bytes,0,bytes.length);
            outputStream.close();

            //delete input
            if(!input.delete())
                log("failed to delete source file!");

        } catch (IOException e) {
            log("failed to save encrypted file: " + output.getName() + " due to error: " + e.getMessage());
        }
    }

    private static void clearFileContent(File file){
        PrintWriter writer;
        try {
            writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            Logger.log("an error occurred while clearing file " + file.getPath(),"FCLR");
        }
    }
}
