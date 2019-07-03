package ua.com.foxminded.webuniversity.dao;

public class EntryMissingException extends RuntimeException {

    public EntryMissingException() {
        super();
    }

    public EntryMissingException(String message) {
        super(message);
    }

    public EntryMissingException(String message, Throwable cause) {
        super(message, cause);
    }

}
