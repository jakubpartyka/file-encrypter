import java.io.*;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class Main {
    private static ArrayList<Byte> data = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        File input = new File("src/C_073.mtgl");
        File output = new File("src/copy.mtgl");
        FileReader reader = new FileReader(input);
        byte [] bytes = new byte[(int) input.length()];
        System.out.println(bytes.length);
        InputStream inputStream = new FileInputStream(input);

        inputStream.read(bytes);

        OutputStream outputStream = new FileOutputStream(output);
        outputStream.write(bytes,0,bytes.length);

        inputStream.close();
        outputStream.close();

    }
}
