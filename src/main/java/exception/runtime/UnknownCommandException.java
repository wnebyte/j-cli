package exception.runtime;

public final class UnknownCommandException extends RuntimeException {

    public UnknownCommandException() {

    }

    public UnknownCommandException(String message) {
        super(message);
    }
}
