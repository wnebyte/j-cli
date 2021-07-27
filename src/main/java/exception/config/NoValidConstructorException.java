package exception.config;

public final class NoValidConstructorException extends ConfigException {

    public NoValidConstructorException(String message) {
        super(message);
    }
}
