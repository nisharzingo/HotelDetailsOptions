package details.hotel.app.hoteldetails.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ZingoHotels Tech on 10-12-2018.
 */

public class HotelImage implements Serializable {

    @SerializedName("HotelImageId")
    private int HotelImageId;

    @SerializedName("HotelId")
    private int HotelId;

    @SerializedName("Images")
    private String Images;

    @SerializedName("Image")
    private byte[] Image;

    @SerializedName("Caption")
    private String Caption;

    public int getHotelImageId() {
        return HotelImageId;
    }

    public void setHotelImageId(int hotelImageId) {
        HotelImageId = hotelImageId;
    }

    public int getHotelId() {
        return HotelId;
    }

    public void setHotelId(int hotelId) {
        HotelId = hotelId;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String images) {
        Images = images;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    public String getCaption() {
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }
}
