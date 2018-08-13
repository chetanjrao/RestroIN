package in.restroin.restroin.interfaces;

import java.util.List;

import in.restroin.restroin.models.CusineGridModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CusinesClient {
    @GET("cusines")
    Call<List<CusineGridModel>> getCusines(

    );
}
