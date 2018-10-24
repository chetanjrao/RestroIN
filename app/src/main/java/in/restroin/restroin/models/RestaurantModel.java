package in.restroin.restroin.models;

import java.util.ArrayList;
import java.util.List;

public class RestaurantModel {
    private String restaurant_id, restaurant_name, restaurant_region, restaurant_address, city_id, restaurant_about, restaurant_manager_name, restaurant_phone, restaurant_email, price_for_two, restaurant_seat_available, restaurant_speciality, restaurant_lat, restaurant_lng, restaurant_opening_time, restaurant_closing_time, restaurant_rating;
    private List<String> restaurant_cuisines_available, restaurant_features_amenities, front_image, menu_image;
    private ArrayList<CouponModel> restaurant_coupon_selected;
    private ArrayList<RestaurantDishesModel> popular_dishes;
    private ArrayList<ReviewsRatingModel> reviews;

    public RestaurantModel(String restaurant_id, String restaurant_name, String restaurant_region, String restaurant_address, String city_id, String restaurant_about, String restaurant_manager_name, String restaurant_phone, String restaurant_email, String price_for_two, String restaurant_seat_available, String restaurant_speciality, String restaurant_lat, String restaurant_lng, String restaurant_opening_time, String restaurant_closing_time, String restaurant_rating, List<String> restaurant_cuisines_available, List<String> restaurant_features_amenities, List<String> front_image, List<String> menu_image, ArrayList<CouponModel> restaurant_coupon_selected, ArrayList<RestaurantDishesModel> popular_dishes, ArrayList<ReviewsRatingModel> reviews) {
        this.restaurant_id = restaurant_id;
        this.restaurant_name = restaurant_name;
        this.restaurant_region = restaurant_region;
        this.restaurant_address = restaurant_address;
        this.city_id = city_id;
        this.restaurant_about = restaurant_about;
        this.restaurant_manager_name = restaurant_manager_name;
        this.restaurant_phone = restaurant_phone;
        this.restaurant_email = restaurant_email;
        this.price_for_two = price_for_two;
        this.restaurant_seat_available = restaurant_seat_available;
        this.restaurant_speciality = restaurant_speciality;
        this.restaurant_lat = restaurant_lat;
        this.restaurant_lng = restaurant_lng;
        this.restaurant_opening_time = restaurant_opening_time;
        this.restaurant_closing_time = restaurant_closing_time;
        this.restaurant_rating = restaurant_rating;
        this.restaurant_cuisines_available = restaurant_cuisines_available;
        this.restaurant_features_amenities = restaurant_features_amenities;
        this.front_image = front_image;
        this.menu_image = menu_image;
        this.restaurant_coupon_selected = restaurant_coupon_selected;
        this.popular_dishes = popular_dishes;
        this.reviews = reviews;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getRestaurant_region() {
        return restaurant_region;
    }

    public void setRestaurant_region(String restaurant_region) {
        this.restaurant_region = restaurant_region;
    }

    public String getRestaurant_address() {
        return restaurant_address;
    }

    public void setRestaurant_address(String restaurant_address) {
        this.restaurant_address = restaurant_address;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getRestaurant_about() {
        return restaurant_about;
    }

    public void setRestaurant_about(String restaurant_about) {
        this.restaurant_about = restaurant_about;
    }

    public String getRestaurant_manager_name() {
        return restaurant_manager_name;
    }

    public void setRestaurant_manager_name(String restaurant_manager_name) {
        this.restaurant_manager_name = restaurant_manager_name;
    }

    public String getRestaurant_phone() {
        return restaurant_phone;
    }

    public void setRestaurant_phone(String restaurant_phone) {
        this.restaurant_phone = restaurant_phone;
    }

    public String getRestaurant_email() {
        return restaurant_email;
    }

    public void setRestaurant_email(String restaurant_email) {
        this.restaurant_email = restaurant_email;
    }

    public String getPrice_for_two() {
        return price_for_two;
    }

    public void setPrice_for_two(String price_for_two) {
        this.price_for_two = price_for_two;
    }

    public String getRestaurant_seat_available() {
        return restaurant_seat_available;
    }

    public void setRestaurant_seat_available(String restaurant_seat_available) {
        this.restaurant_seat_available = restaurant_seat_available;
    }

    public String getRestaurant_speciality() {
        return restaurant_speciality;
    }

    public void setRestaurant_speciality(String restaurant_speciality) {
        this.restaurant_speciality = restaurant_speciality;
    }

    public String getRestaurant_lat() {
        return restaurant_lat;
    }

    public void setRestaurant_lat(String restaurant_lat) {
        this.restaurant_lat = restaurant_lat;
    }

    public String getRestaurant_lng() {
        return restaurant_lng;
    }

    public void setRestaurant_lng(String restaurant_lng) {
        this.restaurant_lng = restaurant_lng;
    }

    public String getRestaurant_opening_time() {
        return restaurant_opening_time;
    }

    public void setRestaurant_opening_time(String restaurant_opening_time) {
        this.restaurant_opening_time = restaurant_opening_time;
    }

    public String getRestaurant_closing_time() {
        return restaurant_closing_time;
    }

    public void setRestaurant_closing_time(String restaurant_closing_time) {
        this.restaurant_closing_time = restaurant_closing_time;
    }

    public String getRestaurant_rating() {
        return restaurant_rating;
    }

    public void setRestaurant_rating(String restaurant_rating) {
        this.restaurant_rating = restaurant_rating;
    }

    public List<String> getRestaurant_cuisines_available() {
        return restaurant_cuisines_available;
    }

    public void setRestaurant_cuisines_available(List<String> restaurant_cuisines_available) {
        this.restaurant_cuisines_available = restaurant_cuisines_available;
    }

    public List<String> getRestaurant_features_amenities() {
        return restaurant_features_amenities;
    }

    public void setRestaurant_features_amenities(List<String> restaurant_features_amenities) {
        this.restaurant_features_amenities = restaurant_features_amenities;
    }

    public List<String> getFront_image() {
        return front_image;
    }

    public void setFront_image(List<String> front_image) {
        this.front_image = front_image;
    }

    public List<String> getMenu_image() {
        return menu_image;
    }

    public void setMenu_image(List<String> menu_image) {
        this.menu_image = menu_image;
    }

    public ArrayList<CouponModel> getRestaurant_coupon_selected() {
        return restaurant_coupon_selected;
    }

    public void setRestaurant_coupon_selected(ArrayList<CouponModel> restaurant_coupon_selected) {
        this.restaurant_coupon_selected = restaurant_coupon_selected;
    }

    public ArrayList<RestaurantDishesModel> getPopular_dishes() {
        return popular_dishes;
    }

    public void setPopular_dishes(ArrayList<RestaurantDishesModel> popular_dishes) {
        this.popular_dishes = popular_dishes;
    }

    public ArrayList<ReviewsRatingModel> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<ReviewsRatingModel> reviews) {
        this.reviews = reviews;
    }
}
