package ua.com.foxminded.webuniversity.exception;

public class StudentsLimitExceededGroupException extends GroupException {

    public StudentsLimitExceededGroupException() {
        super();
    }

    public StudentsLimitExceededGroupException(String message) {
        super(message);
    }
    
    public StudentsLimitExceededGroupException(String message, Throwable cause) {
        super(message, cause);
    }

}
