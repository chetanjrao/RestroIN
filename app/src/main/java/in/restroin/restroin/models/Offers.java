package in.restroin.restroin.models;

import android.net.Uri;

public class Offers {
    private String offer, offer_filter_code, image;

    public Offers(String offer, String offer_filter_code, String image) {
        this.offer = offer;
        this.offer_filter_code = offer_filter_code;
        this.image = image;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getOffer_filter_code() {
        return offer_filter_code;
    }

    public void setOffer_filter_code(String offer_filter_code) {
        this.offer_filter_code = offer_filter_code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
