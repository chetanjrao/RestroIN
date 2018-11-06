package in.restroin.restroin.models;

public class BookingStepModel {
    private String time, name, email, guest, coupon, restaurant_name, phone;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BookingStepModel(String time, String name, String email, String guest, String coupon, String restaurant_name, String phone) {

        this.time = time;
        this.name = name;
        this.email = email;
        this.guest = guest;
        this.coupon = coupon;
        this.restaurant_name = restaurant_name;
        this.phone = phone;
    }
}
