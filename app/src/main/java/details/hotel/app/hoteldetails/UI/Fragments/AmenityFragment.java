package details.hotel.app.hoteldetails.UI.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import details.hotel.app.hoteldetails.Adapter.HotelAmenityListAdapter;
import details.hotel.app.hoteldetails.Model.HotelDetails;
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
public class AmenityFragment extends Fragment {

    RecyclerView mAmenityList;


    public AmenityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try{

            View view = inflater.inflate(R.layout.fragment_amenity,container,false);
            mAmenityList = (RecyclerView)view.findViewById(R.id.hotel_amenity_list);

            getHotel(1);
            return  view;

        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }

    private void getHotel(final int id){

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                //String auth_string = Util.getToken(getActivity());
                String auth_string = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                HotelsDetailsAPI apiService = Util.getClient().create(HotelsDetailsAPI.class);
                Call<HotelDetails> call = apiService.getHotelByHotelId(auth_string,id);

                call.enqueue(new Callback<HotelDetails>() {
                    @Override
                    public void onResponse(Call<HotelDetails> call, Response<HotelDetails> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                        int statusCode = response.code();
                        if (statusCode == 200) {

                            if (progressDialog!=null)
                                progressDialog.dismiss();
                            HotelDetails hotelDetails =  response.body();

                            if(hotelDetails != null)
                            {

                                if(hotelDetails.getPaidAmenityList() != null && hotelDetails.getPaidAmenityList().size() != 0)
                                {

                                    HotelAmenityListAdapter adapter = new HotelAmenityListAdapter(getActivity(), hotelDetails.getPaidAmenityList());
                                    mAmenityList.setAdapter(adapter);

                                }


                            }




                        }else {
                            if (progressDialog!=null)
                                progressDialog.dismiss();
                            Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<HotelDetails> call, Throwable t) {
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        Log.e("TAG", t.toString());
                    }
                });
            }


        });
    }

}
