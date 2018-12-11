package details.hotel.app.hoteldetails.WebAPI;

import java.util.ArrayList;

import details.hotel.app.hoteldetails.Model.Rates;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by ZingoHotels Tech on 11-12-2018.
 */

public interface RateApi {

    @GET("Rates/GetRatesByCategoryId/{CategoryId}")
    Call<ArrayList<Rates>> getRatesByCategoryId(@Header("Authorization") String authKey, @Path("CategoryId") int id);

}
