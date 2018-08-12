package in.restroin.restroin.interfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import in.restroin.restroin.models.Offers;

public interface OfferClient {

    @GET("/getoffers")
    Call<List<Offers>> getOffers();
}
