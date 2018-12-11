package details.hotel.app.hoteldetails.UI.Activities;


import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import details.hotel.app.hoteldetails.Adapter.HotelListAdapter;
import details.hotel.app.hoteldetails.Adapter.NavigationListAdapter;
import details.hotel.app.hoteldetails.Customs.CustomFonts.TextViewRobotoregular;
import details.hotel.app.hoteldetails.Model.HotelDetails;
import details.hotel.app.hoteldetails.Model.NavBarItems;
import details.hotel.app.hoteldetails.Model.ProfileHotelTag;
import details.hotel.app.hoteldetails.R;
import details.hotel.app.hoteldetails.UI.Fragments.AmenityFragment;
import details.hotel.app.hoteldetails.UI.Fragments.BookNowFragment;
import details.hotel.app.hoteldetails.UI.Fragments.ContactFragment;
import details.hotel.app.hoteldetails.UI.Fragments.GalleryFragment;
import details.hotel.app.hoteldetails.UI.Fragments.HomeScreenFragment;
import details.hotel.app.hoteldetails.UI.Fragments.LocationFragment;
import details.hotel.app.hoteldetails.UI.Fragments.RoomsFragment;
import details.hotel.app.hoteldetails.Utils.Constants;
import details.hotel.app.hoteldetails.Utils.PreferenceHandler;
import details.hotel.app.hoteldetails.Utils.ThreadExecuter;
import details.hotel.app.hoteldetails.Utils.Util;
import details.hotel.app.hoteldetails.WebAPI.HotelsDetailsAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v4.view.GravityCompat.START;

public class HotelOptionsScreen extends AppCompatActivity {



    DrawerLayout baseLayout;
    ListView navBarListView;
    Toolbar mToolbar;
    TextViewRobotoregular mSelectHotel,mHotelName;
    LinearLayout mHotelListLay;
    RecyclerView mHotelList;

    Snackbar snackbar;


    String[] title = null;
    ArrayList<HotelDetails> hotelDetailsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{

            setContentView(R.layout.activity_hotel_options_screen);


            baseLayout = (DrawerLayout) findViewById(R.id.base_layout);
            mToolbar = (Toolbar) findViewById(R.id.toolbar);


            navBarListView = (ListView) findViewById(R.id.navbar_list);
            mSelectHotel = (TextViewRobotoregular) findViewById(R.id.select_hotel);
            mHotelName = (TextViewRobotoregular) findViewById(R.id.hotel_name_list);
            mHotelListLay = (LinearLayout) findViewById(R.id.hotel_list_layout);
            mHotelList = (RecyclerView) findViewById(R.id.hotel_name);
            mHotelList.setVisibility(View.GONE);



            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, baseLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            baseLayout.setDrawerListener(toggle);
            toggle.syncState();

            title = getResources().getStringArray(R.array.hotel_menu);

            init();

            setUpNavbar();
            displayMenu("Home");

            navBarListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    try{
                        displayMenu(title[position]);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

            if(PreferenceHandler.getInstance(HotelOptionsScreen.this).getHotelName()!=null&&!PreferenceHandler.getInstance(HotelOptionsScreen.this).getHotelName().isEmpty()){

                mHotelName.setText(""+PreferenceHandler.getInstance(HotelOptionsScreen.this).getHotelName());
            }

            mSelectHotel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggle_contents();
                }
            });




        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void setUpNavbar() throws Exception{

        if(title!=null&&title.length!=0){

            ArrayList<NavBarItems> navBarItemsList = new ArrayList<>();

            for (int i=0;i<title.length;i++)
            {
                NavBarItems navbarItem = new NavBarItems(title[i]);
                navBarItemsList.add(navbarItem);
            }

            NavigationListAdapter adapter = new NavigationListAdapter(getApplicationContext(),navBarItemsList);
            navBarListView.setAdapter(adapter);



        }
    }

    public void displayMenu(String option) throws Exception{

        if(baseLayout != null)
            baseLayout.closeDrawer(START);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (option)
        {

            case "Home":
                Fragment homeFragment = new HomeScreenFragment();
                transaction.replace(R.id.hotel_fragment_view,homeFragment)
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)

                        .commit();
                break;

            case "Contact Us":
                Fragment contactFragment = new ContactFragment();
                transaction.replace(R.id.hotel_fragment_view,contactFragment)
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
                break;

            case "Gallery":
                Fragment galleryFragment = new GalleryFragment();
                transaction.replace(R.id.hotel_fragment_view,galleryFragment)
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
                break;

            case "Amenities":
                Fragment amenityFragment = new AmenityFragment();
                transaction.replace(R.id.hotel_fragment_view,amenityFragment)
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
                break;

            case "Rooms":
                Fragment RoomsFragment = new RoomsFragment();
                transaction.replace(R.id.hotel_fragment_view,RoomsFragment)
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
                break;

            case "Location":
                Fragment locationFragment = new LocationFragment();
                transaction.replace(R.id.hotel_fragment_view,locationFragment)
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
                break;

            case "Book Now":
                Fragment bookFragment = new BookNowFragment();
                transaction.replace(R.id.hotel_fragment_view,bookFragment)
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
                break;

        }

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


    public void toggle_contents(){

        if(mHotelList.isShown()){
            slide_up(this, mHotelList);
            mHotelList.setVisibility(View.GONE);
        }
        else{
            mHotelList.setVisibility(View.VISIBLE);
            slide_down(this, mHotelList);
        }
    }


    public void getTaggedHotels()
    {

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {


               // String authenticationString = Util.getToken(HotelOptionsScreen.this);//"Basic " +  Base64.encodeToString(authentication.getBytes(), Base64.NO_WRAP);
                String authenticationString = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                HotelsDetailsAPI hotelOperation = Util.getClient().create(HotelsDetailsAPI.class);
                Call<ArrayList<ProfileHotelTag>> response = hotelOperation.getTaggedHotels(authenticationString,
                        Constants.PROFILE_DATA_ID);

                response.enqueue(new Callback<ArrayList<ProfileHotelTag>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ProfileHotelTag>> call, Response<ArrayList<ProfileHotelTag>> response) {
                        System.out.println("GetHotelByProfileId = "+response.code());
                        ArrayList<ProfileHotelTag> hotelDetailseResponse = response.body();

                        if(response.code() == 200 || response.code() == 201 || response.code() == 204)
                        {
                            try{
                                if(hotelDetailseResponse != null && hotelDetailseResponse.size() != 0)
                                {


                                    ArrayList<ProfileHotelTag> taggedProfiles = response.body();
                                    hotelDetailsArrayList = new ArrayList<>();
                                    if(hotelDetailsArrayList != null && hotelDetailsArrayList.size() != 0)
                                    {
                                        hotelDetailsArrayList.clear();
                                    }

                                    for (int i=0;i<taggedProfiles.size();i++)
                                    {
                                        if(taggedProfiles.get(i).getHotels() != null)
                                        {
                                            hotelDetailsArrayList.add(taggedProfiles.get(i).getHotels());
                                        }
                                    }
                                    HotelListAdapter adapter = new HotelListAdapter(HotelOptionsScreen.this,hotelDetailsArrayList);
                                    mHotelList.setAdapter(adapter);


                                }
                                else
                                {
                                    getHotelsByProfileId();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }


                        }
                        else
                        {

                            snackbar = Snackbar
                                    .make(baseLayout, "Something went wrong", Snackbar.LENGTH_INDEFINITE)
                                    .setAction("Retry", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            init();

                                        }
                                    });
                            snackbar.show();

                        }


                    }

                    @Override
                    public void onFailure(Call<ArrayList<ProfileHotelTag>> call, Throwable t) {

                        snackbar = Snackbar
                                .make(baseLayout, "Failed", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        init();

                                    }
                                });
                        snackbar.show();

                    }
                });
            }
        });
    }

    public void getHotelsByProfileId()
    {

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {


                // String authenticationString = Util.getToken(HotelOptionsScreen.this);//"Basic " +  Base64.encodeToString(authentication.getBytes(), Base64.NO_WRAP);
                String authenticationString = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                HotelsDetailsAPI hotelOperation = Util.getClient().create(HotelsDetailsAPI.class);
                Call<ArrayList<HotelDetails>> response = hotelOperation.getHotelByProfileId(authenticationString,
                        Constants.PROFILE_DATA_ID);

                response.enqueue(new Callback<ArrayList<HotelDetails>>() {
                    @Override
                    public void onResponse(Call<ArrayList<HotelDetails>> call, Response<ArrayList<HotelDetails>> response) {
                        System.out.println("GetHotelByProfileId = "+response.code());
                        ArrayList<HotelDetails> hotelDetailseResponse = response.body();

                        if(response.code() == 200)
                        {
                            try{
                                if(hotelDetailseResponse != null && hotelDetailseResponse.size() != 0)
                                {


                                    hotelDetailsArrayList = response.body();
                                    HotelListAdapter adapter = new HotelListAdapter(HotelOptionsScreen.this,hotelDetailsArrayList);
                                    mHotelList.setAdapter(adapter);
                                    //}
                                }
                                else
                                {

                                    snackbar = Snackbar
                                            .make(baseLayout, "No Hotels", Snackbar.LENGTH_INDEFINITE)
                                            .setAction("Retry", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    init();

                                                }
                                            });
                                    snackbar.show();

                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }


                        }
                        else
                        {

                            snackbar = Snackbar
                                    .make(baseLayout, "Something went wrong", Snackbar.LENGTH_INDEFINITE)
                                    .setAction("Retry", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            init();

                                        }
                                    });
                            snackbar.show();
                        }


                    }

                    @Override
                    public void onFailure(Call<ArrayList<HotelDetails>> call, Throwable t) {
                        snackbar = Snackbar
                                .make(baseLayout, "Failed", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        init();

                                    }
                                });
                        snackbar.show();

                    }
                });
            }
        });
    }

    public void init(){

        if(Util.isNetworkAvailable(HotelOptionsScreen.this)){

            if(snackbar!=null){
                snackbar.dismiss();
            }

            getTaggedHotels();

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
