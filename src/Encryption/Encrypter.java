package Encryption;

import java.io.*;
public class Encrypter extends FileAccessor{

    private byte [] bytes;
    private File output;
    private File input;

    public Encrypter() {
        super("ENCR");
    }

    public void encrypt(File input) throws IncorrectKeyException, IOException{
        //check if key is set
        if(SecureByteShuffler.keyEmpty())
            throw new IncorrectKeyException("No key selected");

        this.input = input;
        String filename = "encrypted-" + input.getName() + ".bin";
        output = new File(input.getParent() + "/" + filename);

        clearFileContent(output);

        readInput();

        SecureByteShuffler.original = bytes.clone();

        SecureByteShuffler.encrypt(bytes);

        writeOutput();

        log("encryption of file " + input.getName() + " finished successfully");
    }

    @Override
    protected void writeOutput() throws IOException{
        //save output
        OutputStream outputStream = new FileOutputStream(output);
        outputStream.write(bytes,0,bytes.length);
        outputStream.close();
        //delete input
        if(!input.delete())
            log("failed to delete source file!");
    }

    @Override
    protected void readInput() throws IOException {
        bytes = new byte[(int) input.length()];
        InputStream inputStream = new FileInputStream(input);
        log(inputStream.read(bytes) + " bytes read from file: " + input.getName());
        inputStream.close();
    }

    //todo delete input file only after output was saved successfully
}
