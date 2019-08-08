package ua.com.foxminded.webuniversity.exception;

public class ExcessNumberStudentsInGroupException extends RuntimeException {

    public ExcessNumberStudentsInGroupException() {
        super();
    }

    public ExcessNumberStudentsInGroupException(String message) {
        super(message);
    }

    public ExcessNumberStudentsInGroupException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
