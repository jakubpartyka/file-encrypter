import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("ALL")
public class Main {
    private static ArrayList<Byte> data = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        File input = new File("src/cat.jpg");
        File output = new File("src/encrypted.jpg");
        output.delete();
        output.createNewFile();
        byte [] bytes = new byte[(int) input.length()];
        InputStream inputStream = new FileInputStream(input);

        inputStream.read(bytes);
        System.out.println(Arrays.toString(bytes));

        byte start = bytes[0];
        for (int i = 0; i < bytes.length - 1; i++) {
            bytes[i] = bytes[i+1];
        }
        bytes[bytes.length - 1] = start;
        System.out.println(Arrays.toString(bytes));

        OutputStream outputStream = new FileOutputStream(output);
        outputStream.write(bytes,0,bytes.length);

        inputStream.close();
        outputStream.close();
    }
}
