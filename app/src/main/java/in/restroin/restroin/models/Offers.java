package in.restroin.restroin.models;

import android.net.Uri;

public class Offers {
    private String coupon_id, coupon_code, description, coupon_image;

    public Offers(String coupon_id, String coupon_code, String description, String coupon_image) {
        this.coupon_id = coupon_id;
        this.coupon_code = coupon_code;
        this.description = description;
        this.coupon_image = coupon_image;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoupon_image() {
        return coupon_image;
    }

    public void setCoupon_image(String coupon_image) {
        this.coupon_image = coupon_image;
    }
}
