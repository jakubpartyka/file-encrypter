package Encryption;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

interface FileAccessor {

    void readInput() throws IOException;

    void writeOutput() throws IOException;

    void log(String message);

    static void clearFileContent(File file){
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

