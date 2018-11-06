package in.restroin.restroin.models;

import java.util.ArrayList;

public class DiningModel {
    private String dine_count, name, first_name, last_name;
    private ArrayList<BookingStatusModel> bookings;

    public DiningModel(String dine_count, String name, String first_name, String last_name, ArrayList<BookingStatusModel> bookings) {
        this.dine_count = dine_count;
        this.name = name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.bookings = bookings;
    }

    public String getDine_count() {

        return dine_count;
    }

    public void setDine_count(String dine_count) {
        this.dine_count = dine_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public ArrayList<BookingStatusModel> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<BookingStatusModel> bookings) {
        this.bookings = bookings;
    }
}
