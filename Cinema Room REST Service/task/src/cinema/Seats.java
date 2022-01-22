package cinema;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class Seats {

    int row;
    int column;
    int price;
    Boolean status;

    public Seats() {}

    public Seats(int row, int column, int price, Boolean status) {
        this.row = row;
        this.column = column;
        this.price = price;
        this.status = status;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
