package in.restroin.restroin.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class SaveSharedPreferences {
    private Context context;
    private String auth_header = "AUTH_CREDENTIALS";
    private SharedPreferences sharedPreferences;
    private String email, access_token, user_id, mobile_no, device_uid,image, first_name, last_name;
    private boolean firstLoginRejected;

    public String getFirst_name(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(auth_header, Context.MODE_PRIVATE);
        return sharedPreferences.getString("first_name", "");
    }

    public void setFirst_name(String first_name, Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(auth_header, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("first_name", first_name);
        editor.apply();
        this.first_name = first_name;
    }

    public String getLast_name(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(auth_header, Context.MODE_PRIVATE);
        return sharedPreferences.getString("last_name", "");
    }

    public void setLast_name(String last_name, Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(auth_header, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("last_name", last_name);
        editor.apply();
        this.last_name = last_name;
    }


    public String getImage(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(auth_header, Context.MODE_PRIVATE);
        return sharedPreferences.getString("image", "https://www.restroin.in/developers/api/images/blank.png");
    }

    public void setImage(String image, Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(auth_header, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("image", image);
        editor.apply();
        this.image = image;
    }

    public boolean getFirstLoginRejected(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(auth_header, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("firstLoginRejected", false);
    }

    public void setFirstLoginRejected(boolean firstLoginRejected, Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(auth_header, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstLoginRejected", firstLoginRejected);
        editor.apply();
        this.firstLoginRejected = firstLoginRejected;
    }

    public String getEmail(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(auth_header, Context.MODE_PRIVATE);
        return sharedPreferences.getString("username", null);
    }

    public void setEmail(String email, Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(auth_header, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", email);
        editor.apply();
        this.email = email;
    }

    public String getAccess_token(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(auth_header, Context.MODE_PRIVATE);
        return sharedPreferences.getString("access_token", null);
    }

    public void setAccess_token(String access_token, Context context) {
        this.context = context;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("access_token", access_token);
        editor.apply();
        this.access_token = access_token;
    }

    public String getUser_id(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(auth_header, Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_id", null);
    }

    public void setUser_id(String user_id, Context context) {
        this.context = context;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_id", user_id);
        editor.apply();
        this.user_id = user_id;
    }

    public String getMobile_no(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(auth_header, Context.MODE_PRIVATE);
        return sharedPreferences.getString("mobile_no", null);
    }

    public void setMobile_no(String mobile_no, Context context) {
        this.context = context;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mobile_no", mobile_no);
        editor.apply();
        this.mobile_no = mobile_no;
    }

    public String getDevice_uid(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(auth_header, Context.MODE_PRIVATE);
        return sharedPreferences.getString("device_uid", null);
    }

    public void setDevice_uid(String device_ui, Context context) {
        this.context = context;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("device_uid", device_uid);
        editor.apply();
        this.device_uid = device_uid;
    }

    public void setSharedPreferences(@NonNull Context context, String email, String access_token, String mobile_number, String user_id, String device_uid, String image){
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(auth_header, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", email);
        editor.putString("access_token", access_token);
        editor.putString("user_id", user_id);
        editor.putString("mobile_no", mobile_number);
        editor.putString("device_uid", device_uid);
        editor.putString("image", image);
        editor.apply();
    }

    public void removeSharedPrefernces(@NonNull Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(auth_header, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.remove("access_token");
        editor.remove("user_id");
        editor.remove("mobile_no");
        editor.remove("device_uid");
        editor.remove("image");
        editor.apply();
    }



}
