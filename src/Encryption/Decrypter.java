package Encryption;

import java.io.*;

public class Decrypter extends FileAccessor{

    private byte [] bytes;
    private File output;
    private File input;

    public Decrypter() {
        super("DECR");
    }

    public void decrypt(File input) throws IncorrectKeyException {
        //check if key is set
        if(SecureByteShuffler.keyEmpty())
            throw new IncorrectKeyException("No key selected");
        try {
            this.input = input;
            String filename = input.getName();
            filename = filename.substring(0,input.getName().lastIndexOf(".")).replace("encrypted-","");

            output = new File(input.getParent() + "/" + filename);

            clearFileContent(output);

            readInput();

            SecureByteShuffler.encrypted = bytes.clone();

            SecureByteShuffler.decrypt(bytes);

            writeOutput();
        } catch (Exception e){
            e.printStackTrace();
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
            log(inputStream.read(bytes) + " bytes read from source file: " + input.getName());
            inputStream.close();
        }
        catch (IOException e){
            e.printStackTrace();
            //todo
        }
    }


}
