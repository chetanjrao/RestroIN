package in.restroin.restroin.interfaces;

import in.restroin.restroin.models.HangoutRestaurants;
import in.restroin.restroin.models.LoginModel;
import in.restroin.restroin.models.MessageModel;
import in.restroin.restroin.models.ProfileModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RestroINAuthClient {
    @FormUrlEncoded
    @POST("v1/authorization/userCreator")
    Call<MessageModel> registerUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("dev_uid") String device_uid,
            @Field("mob_no") String mobile_number,
            @Field("action") String action
    );

    @FormUrlEncoded
    @POST("v1/authorization/authenticator")
    Call<LoginModel> authenticateUser(@Field("device_uid") String device_uid);

    @FormUrlEncoded
    @POST("v1/authorization/bookingsManager")
    Call<MessageModel> ManageReservation(
            @Header("Authorization: Bearer ") String access_token,
            @Field("actionStep") String actionStep,
            @Field("restaurant_id") String restaurant_id,
            @Field("user_id") String user_id,
            @Field("visiting_date") String visiting_date,
            @Field("visiting_time") String visiting_time,
            @Field("number_of_male") String number_of_male,
            @Field("guest_name") String guest_name,
            @Field("guest_phone") String guest_phone,
            @Field("guest_email") String guest_email,
            @Field("couponSelected") String couponSelected
    );

    @GET("v1/authorization/getProfile")
    Call<ProfileModel> getUserProfile();

    @FormUrlEncoded
    @POST("v1/authorization/searchApi")
    Call<HangoutRestaurants> getRestaurants(
            @Field("filter_name") String filter_name
    );


}
