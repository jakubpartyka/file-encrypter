import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

abstract class FileAccessor {
    private static String IDENTIFIER = "NONE";

    FileAccessor(String identifier) {
        IDENTIFIER = identifier;
    }

    static void log(String message){
        Logger.log(message, IDENTIFIER);
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

