package in.restroin.restroin.models;

public class PopularLocations {
    private String id, location_name, locations_restaurants, location_rating, location_image;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getLocations_restaurants() {
        return locations_restaurants;
    }

    public void setLocations_restaurants(String locations_restaurants) {
        this.locations_restaurants = locations_restaurants;
    }

    public String getLocation_rating() {
        return location_rating;
    }

    public void setLocation_rating(String location_rating) {
        this.location_rating = location_rating;
    }

    public String getLocation_image() {
        return location_image;
    }

    public void setLocation_image(String location_image) {
        this.location_image = location_image;
    }

    public PopularLocations(String id, String location_name, String locations_restaurants, String location_rating, String location_image) {
        this.id = id;
        this.location_name = location_name;
        this.locations_restaurants = locations_restaurants;
        this.location_rating = location_rating;
        this.location_image = location_image;
    }
}
