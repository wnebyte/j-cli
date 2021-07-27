package exception.config;

public final class NoDefaultConstructorException extends ConfigException {

    public NoDefaultConstructorException(String message) {
        super(message);
    }
}
