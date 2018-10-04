package in.restroin.restroin.models;

public class CouponModel {
private String coupon_code, description;

    public CouponModel(String coupon_code, String description) {
        this.coupon_code = coupon_code;
        this.description = description;
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
}
