package details.hotel.app.hoteldetails.UI.Fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import details.hotel.app.hoteldetails.Adapter.HotelListAdapter;
import details.hotel.app.hoteldetails.Customs.CustomFonts.TextViewSFProDisplayRegular;
import details.hotel.app.hoteldetails.Model.HotelDetails;
import details.hotel.app.hoteldetails.Model.HotelMaps;
import details.hotel.app.hoteldetails.R;
import details.hotel.app.hoteldetails.UI.Activities.HotelOptionsScreen;
import details.hotel.app.hoteldetails.Utils.ThreadExecuter;
import details.hotel.app.hoteldetails.Utils.Util;
import details.hotel.app.hoteldetails.WebAPI.HotelsDetailsAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment {

    private GoogleMap mMap;
    MapView mapView;

    Marker marker;


    public LocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try{
            View view = inflater.inflate(R.layout.fragment_location,container,false);

            mapView = (MapView) view.findViewById(R.id.hotel_location_map_view);

            mapView.onCreate(savedInstanceState);
            mapView.onResume();

            try {
                MapsInitializer.initialize(getActivity());
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;


                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling

                        return;
                    }

                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    mMap.getProjection().getVisibleRegion().latLngBounds.getCenter();


                    try{
                        getHotelMaps();


                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });



            return view;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }



    public Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels) {
        Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        drawable.setBounds(0, 0, widthPixels, heightPixels);
        drawable.draw(canvas);

        return mutableBitmap;
    }

    public void getHotelMaps()
    {

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {


                // String authenticationString = Util.getToken(HotelOptionsScreen.this);//"Basic " +  Base64.encodeToString(authentication.getBytes(), Base64.NO_WRAP);
                String authenticationString = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                HotelsDetailsAPI hotelOperation = Util.getClient().create(HotelsDetailsAPI.class);
                Call<ArrayList<HotelMaps>> response = hotelOperation.getHotelMapByHotelId(authenticationString,
                        1);

                response.enqueue(new Callback<ArrayList<HotelMaps>>() {
                    @Override
                    public void onResponse(Call<ArrayList<HotelMaps>> call, Response<ArrayList<HotelMaps>> response) {
                        System.out.println("GetHotelByProfileId = "+response.code());
                        ArrayList<HotelMaps> hotelMaps = response.body();

                        if(response.code() == 200)
                        {
                            try{
                                if(hotelMaps != null && hotelMaps.size() != 0)
                                {



                                    mMap.clear();

                                   /* if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(convertToBitmap(getActivity().getDrawable(R.drawable.om_group_logo),100,100));


                                        LatLng latlng = new LatLng(Double.parseDouble(hotelMaps.get((hotelMaps.size()-1)).getLatitude()),Double.parseDouble(hotelMaps.get((hotelMaps.size()-1)).getLogitude()));
                                        marker = mMap.addMarker(new MarkerOptions()
                                                .position(latlng)
                                                .icon(icon));
                                        CameraPosition cameraPosition = new CameraPosition.Builder().zoom(14).target(latlng).build();
                                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                                    }else{
                                        LatLng latlng = new LatLng(Double.parseDouble(hotelMaps.get((hotelMaps.size()-1)).getLatitude()),Double.parseDouble(hotelMaps.get((hotelMaps.size()-1)).getLogitude()));
                                        marker = mMap.addMarker(new MarkerOptions()
                                                .position(latlng)
                                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                        CameraPosition cameraPosition = new CameraPosition.Builder().zoom(14).target(latlng).build();
                                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                    }*/
                                    LatLng latlng = new LatLng(Double.parseDouble(hotelMaps.get((hotelMaps.size()-1)).getLatitude()),Double.parseDouble(hotelMaps.get((hotelMaps.size()-1)).getLogitude()));
                                    marker = mMap.addMarker(new MarkerOptions()
                                            .position(latlng)
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                    CameraPosition cameraPosition = new CameraPosition.Builder().zoom(14).target(latlng).build();
                                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                                    //}
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
                    public void onFailure(Call<ArrayList<HotelMaps>> call, Throwable t) {


                    }
                });
            }
        });
    }

}
