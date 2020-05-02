import java.io.*;

class Decrypter extends FileAccessor{

    private byte [] bytes;
    private File output;
    private File input;

    Decrypter() {
        super("DECR");
    }

    void decrypt(File input){
        try {
            this.input = input;
            String filename = input.getName().replace("encrypted-","");
            System.out.println(filename);
            output = new File("src/" + filename);

            clearFileContent(output);

            readSourceFile();

            System.out.println("before decryption");
            for (int i = 0; i < 100; i++) {
                System.out.print(bytes[i] + ", ");
            }

            SecureByteShuffler.decrypt(bytes);

            Main.after = bytes.clone();
            //
            System.out.println("after decryption");
            for (int i = 0; i < 100; i++) {
                System.out.print(bytes[i] + ", ");
            }

            saveDecryptedFile();

        } catch (Exception e){
            log("failed to decrypt file: " + input.getName() + " due to error: " + e.getMessage());
        }
        log("decryption of file " + input.getName() + " finished successfully");
    }

    private void saveDecryptedFile(){
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

    private void readSourceFile() throws IOException {
        bytes = new byte[(int) input.length()];
        InputStream inputStream = new FileInputStream(input);
        System.out.println(inputStream.read(bytes) + " bytes read from source file: " + input.getName());
        inputStream.close();
    }


}
