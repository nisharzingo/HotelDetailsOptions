package details.hotel.app.hoteldetails.UI.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import details.hotel.app.hoteldetails.Adapter.HotelAmenityListAdapter;
import details.hotel.app.hoteldetails.Customs.CustomFonts.TextViewRobotoregular;
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

    TextViewRobotoregular mCar,mGuestService,mDining,mMiscellaneous,mProperty,mRecreation;
    LinearLayout mCarLay,mGuestLay,mDiningLay,mMisLay,mPropertyLay,mRecreationlay;

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

            mCar = (TextViewRobotoregular)view.findViewById(R.id.car_parking);
            mGuestService = (TextViewRobotoregular)view.findViewById(R.id.guest_services);
            mDining = (TextViewRobotoregular)view.findViewById(R.id.inhouse_dining);
            mMiscellaneous = (TextViewRobotoregular)view.findViewById(R.id.miscellaneous);
            mProperty = (TextViewRobotoregular)view.findViewById(R.id.property_features);
            mRecreation = (TextViewRobotoregular)view.findViewById(R.id.recreation_facilities);


            mCarLay = (LinearLayout)view.findViewById(R.id.car_lay);
            mGuestLay = (LinearLayout)view.findViewById(R.id.guest_lay);
            mDiningLay= (LinearLayout)view.findViewById(R.id.dining_lay);
            mMisLay = (LinearLayout)view.findViewById(R.id.mis_lay);
            mPropertyLay = (LinearLayout)view.findViewById(R.id.property_lay);
            mRecreationlay = (LinearLayout)view.findViewById(R.id.recreation_lay);

            mCarLay.setVisibility(View.GONE);
            mGuestLay.setVisibility(View.GONE);
            mDiningLay.setVisibility(View.GONE);
            mMisLay.setVisibility(View.GONE);
            mPropertyLay.setVisibility(View.GONE);
            mRecreationlay.setVisibility(View.GONE);


            mCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggle_contents(mCarLay);
                }
            });

            mGuestService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggle_contents(mGuestLay);
                }
            });

            mDining.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggle_contents(mDiningLay);
                }
            });

            mMiscellaneous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggle_contents(mMisLay);
                }
            });

            mProperty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggle_contents(mPropertyLay);
                }
            });

            mRecreation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggle_contents(mRecreationlay);
                }
            });


            //getHotel(1);
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

    public static void slide_down(Context ctx, View v) {

        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }


    public static void slide_up(Context ctx, View v) {

        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }


    public void toggle_contents(final LinearLayout layout){

        if(layout.isShown()){
            slide_up(getActivity(), layout);
            layout.setVisibility(View.GONE);
        }
        else{
            layout.setVisibility(View.VISIBLE);
            slide_down(getActivity(), layout);
        }
    }


}
