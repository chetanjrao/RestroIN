package in.restroin.restroin.models;

public class BookingStatusModel {
    private String booking_id, guest_name, visiting_date_time, restaurant_name, status, no_of_guests;

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getGuest_name() {
        return guest_name;
    }

    public void setGuest_name(String guest_name) {
        this.guest_name = guest_name;
    }

    public String getVisiting_date_time() {
        return visiting_date_time;
    }

    public void setVisiting_date_time(String visiting_date_time) {
        this.visiting_date_time = visiting_date_time;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNo_of_guests() {
        return no_of_guests;
    }

    public void setNo_of_guests(String no_of_guests) {
        this.no_of_guests = no_of_guests;
    }

    public BookingStatusModel(String booking_id, String guest_name, String visiting_date_time, String restaurant_name, String status, String no_of_guests) {
        this.booking_id = booking_id;
        this.guest_name = guest_name;
        this.visiting_date_time = visiting_date_time;
        this.restaurant_name = restaurant_name;
        this.status = status;
        this.no_of_guests = no_of_guests;
    }
}
