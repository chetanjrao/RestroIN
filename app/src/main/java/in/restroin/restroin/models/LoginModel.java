package in.restroin.restroin.models;

import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private String status;

    @SerializedName("token")
    private String token;

    @SerializedName("id")
    private String id;

    @SerializedName("mobile_no")
    private String mobile_number;

    @SerializedName("image")
    private String image;

    @SerializedName("device_uid")
    private String device_uid;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDevice_uid() {
        return device_uid;
    }

    public void setDevice_uid(String device_uid) {
        this.device_uid = device_uid;
    }

    public LoginModel(String message, String status, String token, String id, String mobile_number, String image, String device_uid) {

        this.message = message;
        this.status = status;
        this.token = token;
        this.id = id;
        this.mobile_number = mobile_number;
        this.image = image;
        this.device_uid = device_uid;
    }
}
