package igor.kos.est.exceptions;

public class RefreshTokenExpiredException extends RuntimeException {

    public RefreshTokenExpiredException(String message) {
        super(message);
    }

    public RefreshTokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public RefreshTokenExpiredException(Throwable cause) {
        super(cause);
    }

    public RefreshTokenExpiredException() {
        super();
    }
}
