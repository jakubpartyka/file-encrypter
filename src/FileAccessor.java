abstract class FileAccessor {
    private static String IDENTIFIER = "NONE";

    FileAccessor(String identifier) {
        IDENTIFIER = identifier;
    }

    static void log(String message){
        Logger.log(message, IDENTIFIER);
    }
}

