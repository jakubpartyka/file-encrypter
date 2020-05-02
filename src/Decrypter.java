import java.io.*;
import java.util.Arrays;

@SuppressWarnings("ALL")
public class Decrypter {
    public static void main(String[] args) throws IOException {
        File input = new File("src/encrypted.jpg");
        File output = new File("src/decrypted.jpg");
        byte [] bytes = new byte[(int) input.length()];
        InputStream inputStream = new FileInputStream(input);
        OutputStream outputStream = new FileOutputStream(output);
        inputStream.read(bytes);
        System.out.println(Arrays.toString(bytes));

        byte tmp = bytes[bytes.length - 1];

        for (int i = bytes.length - 1; i > 0; i--) {
            bytes[i] = bytes[i-1];
        }


        bytes[0] = tmp;

        outputStream.write(bytes);

        System.out.println(Arrays.toString(bytes));

        inputStream.close();
        outputStream.close();






    }
}
