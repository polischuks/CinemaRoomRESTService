package cinema;

public class LoginException extends RuntimeException {
    Error error;

    public LoginException(Error error) {
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}
