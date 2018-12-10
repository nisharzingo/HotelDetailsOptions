package details.hotel.app.hoteldetails.UI.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import details.hotel.app.hoteldetails.Adapter.HotelGalleryAdapter;
import details.hotel.app.hoteldetails.Adapter.HotelImageAdapter;
import details.hotel.app.hoteldetails.Model.HotelImage;
import details.hotel.app.hoteldetails.R;
import details.hotel.app.hoteldetails.Utils.ThreadExecuter;
import details.hotel.app.hoteldetails.Utils.Util;
import details.hotel.app.hoteldetails.WebAPI.HotelImagesAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {

    ViewPager hotelImagepager;
    Button mClose;


    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try{
            View view = inflater.inflate(R.layout.fragment_gallery,container,false);

            hotelImagepager = (ViewPager) view.findViewById(R.id.pager_image);
            getHotelImages();
            return view;


        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public void getHotelImages()
    {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please Loading");
        dialog.show();

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                HotelImagesAPI api = Util.getClient().create(HotelImagesAPI.class);
                // String auth = Util.getToken(HotelOptionsScreen.this);
                String auth = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                final Call<ArrayList<HotelImage>> HotelImagereaponse = api.getHotelImages(auth, 1);

                HotelImagereaponse.enqueue(new Callback<ArrayList<HotelImage>>() {
                    @Override
                    public void onResponse(Call<ArrayList<HotelImage>> call, Response<ArrayList<HotelImage>> response) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
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
                                        HotelGalleryAdapter activityImagesadapter = new HotelGalleryAdapter(getActivity(),hotelImages);
                                        hotelImagepager.setAdapter(activityImagesadapter);

                                    }
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
                    public void onFailure(Call<ArrayList<HotelImage>> call, Throwable t) {
                        System.out.println(t.getMessage());
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        Toast.makeText(getActivity(),"Please Check your data connection",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
