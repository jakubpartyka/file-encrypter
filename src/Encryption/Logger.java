package Encryption;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

class Logger {
    private static String logFilePath;
    private static File logFile;

    static void log(String message, String identifier){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("[dd-MM-YYYY-HH:mm:ss]");
            String ts = sdf.format(Calendar.getInstance().getTime());
            FileWriter fileWriter = new FileWriter(logFile,true);
            String logMessage = ts + "[" + identifier + "]:" + message;
            fileWriter.write( logMessage + "\n");

            fileWriter.close();
        } catch (IOException ignored) {}
    }

    static void start() throws IOException {
        readConfigFile();
        logNewSession();
    }

    private static void logNewSession() throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("[dd-MM-YYYY-HH:mm:ss]");
        String ts = sdf.format(Calendar.getInstance().getTime());
        FileWriter fileWriter = new FileWriter(logFile,true);
        String logMessage = "==================\n" + ts + "[LOG]:" + "NEW SESSION STARTED";
        fileWriter.write( logMessage + "\n");
        fileWriter.close();
    }

    private static void readConfigFile() throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(".config"));
        String line = reader.readLine();
        while (line != null){
            String [] data = line.split("=");

            switch (data[0]) {
                case "log_file" :
                    logFilePath = data[1];
                    break;
            }

            line = reader.readLine();
        }

        logFile = new File(logFilePath);
    }
}
