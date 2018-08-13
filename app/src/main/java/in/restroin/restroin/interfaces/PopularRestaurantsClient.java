package in.restroin.restroin.interfaces;

import java.util.List;

import in.restroin.restroin.models.PopularRestaurants;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PopularRestaurantsClient {
    @GET("/popular_restaurants")
    Call<List<PopularRestaurants>> getPopularRestaurants(

    );
}
