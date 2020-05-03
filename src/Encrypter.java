import java.io.*;
class Encrypter extends FileAccessor{

    private byte [] bytes;
    private File output;
    private File input;

    Encrypter() {
        super("ENCR");
    }

    void encrypt(File input){
        try {
            output = new File("src/encrypted-" + input.getName());
            this.input = input;

            clearFileContent(output);

            readInput();

            SecureByteShuffler.original = bytes.clone();

            SecureByteShuffler.encrypt(bytes);

            writeOutput();

        } catch (Exception e){
            e.printStackTrace();
            log("failed to encrypt file: " + input.getName() + " due to error: " + e.getMessage());
        }
        log("encryption of file " + input.getName() + " finished successfully");
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
            log(inputStream.read(bytes) + " bytes read from file: " + input.getName());
            inputStream.close();
        }
        catch (IOException e){
            e.printStackTrace();
            //todo
        }
    }

    //todo delete input file only after output was saved successfully
}
