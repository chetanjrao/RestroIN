package in.restroin.restroin.models;

public class RestaurantDishesModel {
    private String menu_name, menu_image, menu_price, menu_details;

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_image() {
        return menu_image;
    }

    public void setMenu_image(String menu_image) {
        this.menu_image = menu_image;
    }

    public String getMenu_price() {
        return menu_price;
    }

    public void setMenu_price(String menu_price) {
        this.menu_price = menu_price;
    }

    public String getMenu_details() {
        return menu_details;
    }

    public void setMenu_details(String menu_details) {
        this.menu_details = menu_details;
    }

    public RestaurantDishesModel(String menu_name, String menu_image, String menu_price, String menu_details) {

        this.menu_name = menu_name;
        this.menu_image = menu_image;
        this.menu_price = menu_price;
        this.menu_details = menu_details;
    }
}
