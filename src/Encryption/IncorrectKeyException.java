package Encryption;

public class IncorrectKeyException extends Exception {
    IncorrectKeyException(String message) {
        super(message);
    }
}
