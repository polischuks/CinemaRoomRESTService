package cinema;


import lombok.Getter;

public class PurchaseException extends RuntimeException {


    Error error;

    public PurchaseException(Error error) {
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}