package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


public class CinemaRoom {

    @JsonProperty("total_rows")
    Integer total_rows = 9;

    @JsonProperty("total_columns")
    Integer total_columns = 9;

    @JsonProperty("available_seats")
    List<Seats> available_seats = new ArrayList<Seats>();


    public CinemaRoom() {
        for (int i = 1; i <= total_rows; i++ ) {
            for (int j = 1; j <= total_columns; j++ ) {
                if (i <= 4) {
                    available_seats.add(new Seats(i, j, 10, false));
                } else available_seats.add(new Seats(i, j, 8, false));
            }
        }
    }

    public Seats getSeatWithSameRowColumn(Seats seat) {
        for (Seats available_seat : available_seats) {
            if (available_seat.getRow() == seat.getRow() &&
                    available_seat.getColumn() == seat.getColumn()) {
                return available_seat;
            }
        }
        return null;
    }

    public void deleteAvailableSeat(Seats seat) {
        available_seats.remove(seat);
    }

    public void addAvailableSeat(Seats seat) {
        available_seats.add(seat);
    }

    public List<Seats> getAvailable_seats() {
        return available_seats;
    }
}
