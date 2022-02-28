package errors;

public class NotFound extends HttpError {
    public NotFound(String message) {
        super("NOT_FOUND", message);
    }
}
