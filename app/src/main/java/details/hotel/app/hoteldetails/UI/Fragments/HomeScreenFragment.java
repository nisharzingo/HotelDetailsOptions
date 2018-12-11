package details.hotel.app.hoteldetails.UI.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import details.hotel.app.hoteldetails.Adapter.HotelImageAdapter;
import details.hotel.app.hoteldetails.Customs.CustomAdapters.AutoScrollImageAdapter;
import details.hotel.app.hoteldetails.Model.HotelImage;
import details.hotel.app.hoteldetails.R;
import details.hotel.app.hoteldetails.UI.Activities.HotelOptionsScreen;
import details.hotel.app.hoteldetails.Utils.Constants;
import details.hotel.app.hoteldetails.Utils.PreferenceHandler;
import details.hotel.app.hoteldetails.Utils.ThreadExecuter;
import details.hotel.app.hoteldetails.Utils.Util;
import details.hotel.app.hoteldetails.WebAPI.HotelImagesAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeScreenFragment extends Fragment {

    AutoScrollImageAdapter hotelImagesScroller;
    ImageView mContactUs,mGallery,mAmenities,mRooms,mLocation,mBook,mLoader;

    Snackbar snackbar;
    CoordinatorLayout baseLayout;

    public HomeScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try{
            View view = inflater.inflate(R.layout.fragment_home_screen,container,false);

            baseLayout = (CoordinatorLayout) view.findViewById(R.id.hotel_home_base);
            hotelImagesScroller = (AutoScrollImageAdapter) view.findViewById(R.id.hotel_images_scroller);
            hotelImagesScroller.setStopScrollWhenTouch(true);

            mContactUs = (ImageView)view.findViewById(R.id.hotel_contact_image);
            mGallery = (ImageView)view.findViewById(R.id.hotel_gallery_image);
            mAmenities = (ImageView)view.findViewById(R.id.hotel_amenity_image);
            mRooms = (ImageView)view.findViewById(R.id.hotel_room_image);
            mLocation = (ImageView)view.findViewById(R.id.hotel_location_image);
            mBook = (ImageView)view.findViewById(R.id.hotel_book_image);

            mLoader = (ImageView)view.findViewById(R.id.loader);

            Glide.with(getActivity()).load(R.drawable.loader_square).into(mLoader);


            DrawableCompat.setTint(mContactUs.getDrawable(), ContextCompat.getColor(getActivity(), R.color.colorPrimary));
            DrawableCompat.setTint(mGallery.getDrawable(), ContextCompat.getColor(getActivity(), R.color.colorPrimary));
            DrawableCompat.setTint(mAmenities.getDrawable(), ContextCompat.getColor(getActivity(), R.color.colorPrimary));
            DrawableCompat.setTint(mRooms.getDrawable(), ContextCompat.getColor(getActivity(), R.color.colorPrimary));
            DrawableCompat.setTint(mLocation.getDrawable(), ContextCompat.getColor(getActivity(), R.color.colorPrimary));
            DrawableCompat.setTint(mBook.getDrawable(), ContextCompat.getColor(getActivity(), R.color.colorPrimary));


            mContactUs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fragment  contactFragment = new ContactFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.hotel_fragment_view, contactFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });

            mGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fragment  galleryFragment = new GalleryFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.hotel_fragment_view, galleryFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });

            mAmenities.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fragment  amenityFragment = new AmenityFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.hotel_fragment_view, amenityFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });

            mRooms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fragment  roomsFragment = new RoomsFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.hotel_fragment_view, roomsFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });

            mLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fragment  locationFrag = new LocationFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.hotel_fragment_view, locationFrag);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });

            mBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fragment  bookFragament = new BookNowFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.hotel_fragment_view, bookFragament);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });

            init();

            return view;


        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public void getHotelImages(final int hotelID)
    {


        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                HotelImagesAPI api = Util.getClient().create(HotelImagesAPI.class);
                // String auth = Util.getToken(HotelOptionsScreen.this);
                String auth = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                final Call<ArrayList<HotelImage>> HotelImagereaponse = api.getHotelImages(auth, hotelID);

                HotelImagereaponse.enqueue(new Callback<ArrayList<HotelImage>>() {
                    @Override
                    public void onResponse(Call<ArrayList<HotelImage>> call, Response<ArrayList<HotelImage>> response) {

                        if(response.code() == 200 || response.code() == 201)
                        {
                            try{
                                if(response.body() != null && response.body().size() != 0)
                                {

                                    ArrayList<String> hotelImages = new ArrayList<>();

                                    for (int i=0;i<response.body().size();i++)
                                    {

                                        hotelImages.add(response.body().get(i).getImages());
                                    }

                                    if(hotelImages!=null&&hotelImages.size()!=0){
                                        HotelImageAdapter activityImagesadapter = new HotelImageAdapter(getActivity(),hotelImages);
                                        hotelImagesScroller.setAdapter(activityImagesadapter);
                                        mLoader.setVisibility(View.GONE);

                                    }
                                }
                                else
                                {

                                    snackbar = Snackbar
                                            .make(baseLayout, "No Images", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }


                        }
                        else
                        {
                            snackbar = Snackbar
                                    .make(baseLayout, "Something went wrong", Snackbar.LENGTH_LONG);
                            snackbar.show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<HotelImage>> call, Throwable t) {
                        System.out.println(t.getMessage());

                        snackbar = Snackbar
                                .make(baseLayout, "Network Error", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });
            }
        });
    }

    public void init(){

        if(Util.isNetworkAvailable(getActivity())){

            if(snackbar!=null){
                snackbar.dismiss();
            }

            if(PreferenceHandler.getInstance(getActivity()).getHotelID()!=0){
                getHotelImages(PreferenceHandler.getInstance(getActivity()).getHotelID());

            }else{
                getHotelImages(Constants.HOTEL_DATA_ID);
            }

        }else{
            snackbar = Snackbar
                    .make(baseLayout, "No Connection", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            init();

                        }
                    });
            snackbar.show();
        }

    }
}
