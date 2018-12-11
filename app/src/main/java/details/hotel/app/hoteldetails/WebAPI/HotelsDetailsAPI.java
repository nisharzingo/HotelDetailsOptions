package details.hotel.app.hoteldetails.WebAPI;

import com.google.android.gms.games.multiplayer.realtime.Room;

import java.util.ArrayList;

import details.hotel.app.hoteldetails.Model.HotelDetails;
import details.hotel.app.hoteldetails.Model.HotelMaps;
import details.hotel.app.hoteldetails.Model.ProfileHotelTag;
import details.hotel.app.hoteldetails.Model.RoomCategories;
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

    @GET("Profiles/GetHotelsByProfileId/{ProfileId}")
    Call<ArrayList<HotelDetails>> getHotelByProfileId(@Header("Authorization") String authKey,@Path("ProfileId") int ProfileId);

    @GET("RoomCategories/GetRoomCategoriesByHotelId/{HotelId}")
    Call<ArrayList<RoomCategories>> getCategoriesByHotelId(@Header("Authorization") String authKey, @Path("HotelId") int HotelId);

    @GET("Hotels/GetMapByHotelId/{HotelId}")
    Call<ArrayList<HotelMaps>> getHotelMapByHotelId(@Header("Authorization") String authKey, @Path("HotelId") int HotelId);

    @GET("ProfileHotelMapping/GetHotelIdByProfileId/{ProfileId}")
    Call<ArrayList<ProfileHotelTag>> getTaggedHotels(@Header("Authorization") String authKey, @Path("ProfileId") int ProfileId);

}
