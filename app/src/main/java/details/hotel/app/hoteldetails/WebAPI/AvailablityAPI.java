package details.hotel.app.hoteldetails.WebAPI;

import details.hotel.app.hoteldetails.Model.AvailabiltyCheckGetData;
import details.hotel.app.hoteldetails.Model.AvailabiltyCheckPostData;
import details.hotel.app.hoteldetails.Model.HotelImage;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by ZingoHotels Tech on 11-12-2018.
 */

public interface AvailablityAPI {

    @POST("Agent/GetAvailabilityOfCategoryBetweenDatesOfParticularHotel")
    Call<AvailabiltyCheckGetData> checkAvailablityofHotel(@Header("Authorization") String authKey, @Body AvailabiltyCheckPostData body);

}
