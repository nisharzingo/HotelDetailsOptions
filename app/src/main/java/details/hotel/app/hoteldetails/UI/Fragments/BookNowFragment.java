package details.hotel.app.hoteldetails.UI.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import details.hotel.app.hoteldetails.Adapter.RoomCategoriesListAdapter;
import details.hotel.app.hoteldetails.Model.HotelMaps;
import details.hotel.app.hoteldetails.Model.RoomCategories;
import details.hotel.app.hoteldetails.R;
import details.hotel.app.hoteldetails.Utils.ThreadExecuter;
import details.hotel.app.hoteldetails.Utils.Util;
import details.hotel.app.hoteldetails.WebAPI.HotelsDetailsAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookNowFragment extends Fragment {

    RecyclerView mCategoryList;


    public BookNowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try{
            View view = inflater.inflate(R.layout.fragment_book_now,container,false);

            mCategoryList = (RecyclerView)view.findViewById(R.id.hotel_category_list);

            getRoomCategories();

            return view;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public void getRoomCategories()
    {

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {


                // String authenticationString = Util.getToken(HotelOptionsScreen.this);//"Basic " +  Base64.encodeToString(authentication.getBytes(), Base64.NO_WRAP);
                String authenticationString = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                HotelsDetailsAPI hotelOperation = Util.getClient().create(HotelsDetailsAPI.class);
                Call<ArrayList<RoomCategories>> response = hotelOperation.getCategoriesByHotelId(authenticationString,
                        1);

                response.enqueue(new Callback<ArrayList<RoomCategories>>() {
                    @Override
                    public void onResponse(Call<ArrayList<RoomCategories>> call, Response<ArrayList<RoomCategories>> response) {
                        System.out.println("GetHotelByProfileId = "+response.code());
                        ArrayList<RoomCategories> hotelCategories = response.body();

                        if(response.code() == 200)
                        {
                            try{
                                if(hotelCategories != null && hotelCategories.size() != 0)
                                {


                                    RoomCategoriesListAdapter adapter = new RoomCategoriesListAdapter(getActivity(),hotelCategories);
                                    mCategoryList.setAdapter(adapter);



                                }
                                else
                                {



                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }


                        }
                        else
                        {

                        }


                    }

                    @Override
                    public void onFailure(Call<ArrayList<RoomCategories>> call, Throwable t) {


                    }
                });
            }
        });
    }

}
