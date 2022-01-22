package cinema;

import lombok.Data;

import java.util.UUID;


public class Ticket {
    UUID token;
    Seats ticket;

    public Ticket() {
    }

    public Ticket(UUID token, Seats ticket) {
        this.token = token;
        this.ticket = ticket;
    }

    public UUID getToken() {
        return token;
    }

    public Seats getTicket() {
        return ticket;
    }

    public void setTicket(Seats ticket) {
        this.ticket = ticket;
    }
}
