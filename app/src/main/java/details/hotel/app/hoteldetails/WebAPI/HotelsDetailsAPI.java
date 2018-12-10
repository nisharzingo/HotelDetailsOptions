package details.hotel.app.hoteldetails.WebAPI;

import details.hotel.app.hoteldetails.Model.HotelDetails;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by ZingoHotels Tech on 10-12-2018.
 */

public interface HotelsDetailsAPI  {

    @GET("Hotels/{HotelId}")
    Call<HotelDetails> getHotelByHotelId(@Header("Authorization") String authKey, @Path("HotelId") int HotelId);

}
