package ua.com.foxminded.webuniversity.exception;

public class StudentsLimitExceededException extends GroupException {

    public StudentsLimitExceededException() {
        super();
    }

    public StudentsLimitExceededException(String message) {
        super(message);
    }
    
    public StudentsLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }

}
