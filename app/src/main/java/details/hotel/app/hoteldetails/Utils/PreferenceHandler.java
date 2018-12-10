package details.hotel.app.hoteldetails.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by ZingoHotels Tech on 10-12-2018.
 */

public class PreferenceHandler {

    private SharedPreferences sh;

    private PreferenceHandler() {

    }

    private PreferenceHandler(Context mContext) {
        sh = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    private static PreferenceHandler instance = null;

    /**
     *
     * @param mContext
     * @return {@link PreferenceHandler}
     */
    public synchronized static PreferenceHandler getInstance(Context mContext) {
        if (instance == null) {
            instance = new PreferenceHandler(mContext);
        }
        return instance;
    }

    public void setUserId(int id)
    {
        sh.edit().putInt(Constants.USER_ID,id).apply();
    }

    public int getUserId()
    {
        return sh.getInt(Constants.USER_ID,0);
    }

    public void setListSize(int id)
    {
        sh.edit().putInt("List",id).apply();
    }

    public int getListSize()
    {
        return sh.getInt("List",0);
    }

    public void setHotelId(int id)
    {
        sh.edit().putInt(Constants.HOTEL_ID,id).apply();
    }

    public int getHotelID()
    {
        return sh.getInt(Constants.HOTEL_ID,0);
    }


    public void setToken(String token)
    {
        sh.edit().putString(Constants.TOKEN,token).apply();
    }

    public String getToken()
    {
        return sh.getString(Constants.TOKEN,"");
    }


    public void setUserName(String username)
    {
        sh.edit().putString(Constants.USER_NAME,username).apply();
    }

    public String getUserName()
    {
        return sh.getString(Constants.USER_NAME,"");
    }

    public void setPhoneNumber(String phonenumber)
    {
        sh.edit().putString(Constants.USER_PHONENUMER,phonenumber).apply();
    }

    public String getPhoneNumber()
    {
        return sh.getString(Constants.USER_PHONENUMER,"");
    }

    public void setHotelName(String id)
    {
        sh.edit().putString(Constants.HOTEL_NAME,id).apply();
    }

    public String getHotelName()
    {
        return sh.getString(Constants.HOTEL_NAME,"");
    }


    public void clear(){
        sh.edit().clear().apply();

    }


}
