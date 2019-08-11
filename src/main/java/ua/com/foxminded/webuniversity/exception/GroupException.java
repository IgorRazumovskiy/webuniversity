package ua.com.foxminded.webuniversity.exception;

public class GroupException extends RuntimeException {

    public GroupException() {
        super();
    }

    public GroupException(String message) {
        super(message);
    }

    public GroupException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
