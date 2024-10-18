package ai.smartassets.challenge.exception;

public class NotFoundFieldException extends RuntimeException {
    public NotFoundFieldException(String errorMessage) {
        super(errorMessage);
    }
}
