package in.restroin.restroin.models;

import com.google.gson.annotations.SerializedName;

public class MessageModel {
    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private String status;

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

    public MessageModel(String message, String status) {

        this.message = message;
        this.status = status;
    }
}
