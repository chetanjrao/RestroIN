package in.restroin.restroin.interfaces;

import java.util.List;

import in.restroin.restroin.models.PopularLocations;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PopularLocationClient {
    @GET("popular_locations")
    Call<List<PopularLocations>> getPopularLocations(

    );
}
