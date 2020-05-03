package Encryption;

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
            output = new File("src/" + filename);

            clearFileContent(output);

            readInput();

            SecureByteShuffler.encrypted = bytes.clone();

            SecureByteShuffler.decrypt(bytes);

            writeOutput();

        } catch (Exception e){
            log("failed to decrypt file: " + input.getName() + " due to error: " + e.getMessage());
        }
        log("decryption of file " + input.getName() + " finished successfully");
    }

    @Override
    protected void writeOutput(){
        try {
            //save output
            OutputStream outputStream = new FileOutputStream(output);
            outputStream.write(bytes,0,bytes.length);
            outputStream.close();

            //delete input
            if(!input.delete())
                log("failed to delete source file!");

        } catch (IOException e) {
            e.printStackTrace();
            //todo
        }
    }

    @Override
    protected void readInput() {
        try {
            bytes = new byte[(int) input.length()];
            InputStream inputStream = new FileInputStream(input);
            System.out.println(inputStream.read(bytes) + " bytes read from source file: " + input.getName());
            inputStream.close();
        }
        catch (IOException e){
            e.printStackTrace();
            //todo
        }
    }


}