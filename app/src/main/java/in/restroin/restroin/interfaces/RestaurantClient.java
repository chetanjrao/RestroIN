package in.restroin.restroin.interfaces;

import in.restroin.restroin.models.RestaurantModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestaurantClient {
    @POST("restaurants/{restaurant}")
    Call<RestaurantModel> getRestaurantData(
            @Path("restaurant") String restaurant
    );
}
