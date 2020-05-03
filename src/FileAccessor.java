import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

abstract class FileAccessor {
    private String identifier;

    FileAccessor(String identifier) {
        this.identifier = identifier;
    }

    abstract void readInput();

    abstract void writeOutput();

    void log(String message){
        Logger.log(message, identifier);
    }

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

