package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@RestController
public class Controller {


    CinemaRoom Cinema = new CinemaRoom();
    int row;
    int column;
    int total_rows = 9;
    int total_columns = 9;
    ConcurrentHashMap<UUID, Seats> tickets = new ConcurrentHashMap<>();


    @GetMapping("/seats")
    public CinemaRoom getInfoCinema() {

        return Cinema;
    }



    @PostMapping("/purchase")
    public Ticket purchase(@RequestBody Seats seat) {
        int row = seat.getRow();
        int column = seat.getColumn();
        if (isOutOfBounds(row, total_rows) || isOutOfBounds(column, total_columns)) {
            throw new PurchaseException(new Error("The number of a row or a column is out of bounds!"));
        }
        Seats available_seat = Cinema.getSeatWithSameRowColumn(seat);
        if (available_seat != null) {
            UUID token = UUID.randomUUID();
            tickets.put(token, available_seat);
            Cinema.deleteAvailableSeat(available_seat);
            return new Ticket(token, available_seat);
        }
        throw new PurchaseException(new Error("The ticket has been already purchased!"));
    }

    @PostMapping("/return")
    public Map.Entry<String, Seats> refund(@RequestBody Ticket ticket) {
        UUID token = ticket.getToken();
        if (tickets.containsKey(token)) {
            Seats returned_ticket = tickets.get(token);
            Cinema.addAvailableSeat(returned_ticket);
            tickets.remove(token);
            return new AbstractMap.SimpleEntry<>("returned_ticket", returned_ticket);
        }
        throw new PurchaseException(new Error("Wrong token!"));
    }

    @PostMapping("/stats")
    public Statistics getStats(@RequestParam(value = "password", required = false) String password) {
        if (password == null) throw new LoginException(new Error("The password is wrong!"));
        if (password.equals("super_secret")) {
            return new Statistics(getCurrentIncome(), Cinema.getAvailable_seats().size(), tickets.size());
        }
        throw new LoginException(new Error("The password is wrong!"));
    }

    private int getCurrentIncome() {
        int currentIncome = 0;
        for (Seats seat : tickets.values()) {
            currentIncome += seat.getPrice();
        }
        return currentIncome;
    }

    private boolean isOutOfBounds(int number, int max) {
        return number < 1 || number > max;
    }

    @ExceptionHandler(PurchaseException.class)
    public ResponseEntity<Error> handleTheatreException(PurchaseException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getError());
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<Error> handleTheatreException(LoginException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getError());
    }
}
