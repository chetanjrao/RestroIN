package in.restroin.restroin.models;

import com.google.gson.annotations.SerializedName;

public class ProfileModel {

    @SerializedName("id")
    private String id;

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("last_name")
    private String last_name;

    @SerializedName("email_id")
    private String email_id;

    @SerializedName("mob_no")
    private String mob_no;

    @SerializedName("dev_uid")
    private String dev_uid;

    @SerializedName("image")
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getMob_no() {
        return mob_no;
    }

    public void setMob_no(String mob_no) {
        this.mob_no = mob_no;
    }

    public String getDev_uid() {
        return dev_uid;
    }

    public void setDev_uid(String dev_uid) {
        this.dev_uid = dev_uid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ProfileModel(String id, String first_name, String last_name, String email_id, String mob_no, String dev_uid, String image) {

        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email_id = email_id;
        this.mob_no = mob_no;
        this.dev_uid = dev_uid;
        this.image = image;
    }
}
