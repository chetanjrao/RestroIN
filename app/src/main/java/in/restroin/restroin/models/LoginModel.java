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

    public LoginModel(String message, String status, String token, String id) {

        this.message = message;
        this.status = status;
        this.token = token;
        this.id = id;
    }
}
