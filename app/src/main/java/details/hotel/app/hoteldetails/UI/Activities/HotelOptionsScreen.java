package details.hotel.app.hoteldetails.UI.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import details.hotel.app.hoteldetails.Adapter.NavigationListAdapter;
import details.hotel.app.hoteldetails.Model.NavBarItems;
import details.hotel.app.hoteldetails.R;
import details.hotel.app.hoteldetails.UI.Fragments.AmenityFragment;
import details.hotel.app.hoteldetails.UI.Fragments.BookNowFragment;
import details.hotel.app.hoteldetails.UI.Fragments.ContactFragment;
import details.hotel.app.hoteldetails.UI.Fragments.GalleryFragment;
import details.hotel.app.hoteldetails.UI.Fragments.HomeScreenFragment;
import details.hotel.app.hoteldetails.UI.Fragments.LocationFragment;
import details.hotel.app.hoteldetails.UI.Fragments.RoomsFragment;

import static android.support.v4.view.GravityCompat.START;

public class HotelOptionsScreen extends AppCompatActivity {



    DrawerLayout baseLayout;
    ListView navBarListView;
    Toolbar mToolbar;


    String[] title = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{

            setContentView(R.layout.activity_hotel_options_screen);


            baseLayout = (DrawerLayout) findViewById(R.id.base_layout);
            mToolbar = (Toolbar) findViewById(R.id.toolbar);


            navBarListView = (ListView) findViewById(R.id.navbar_list);


            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, baseLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            baseLayout.setDrawerListener(toggle);
            toggle.syncState();

            title = getResources().getStringArray(R.array.hotel_menu);

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
                transaction.replace(R.id.hotel_fragment_view,homeFragment).commit();
                break;

            case "Contact Us":
                Fragment contactFragment = new ContactFragment();
                transaction.replace(R.id.hotel_fragment_view,contactFragment).commit();
                break;

            case "Gallery":
                Fragment galleryFragment = new GalleryFragment();
                transaction.replace(R.id.hotel_fragment_view,galleryFragment).commit();
                break;

            case "Amenities":
                Fragment amenityFragment = new AmenityFragment();
                transaction.replace(R.id.hotel_fragment_view,amenityFragment).commit();
                break;

            case "Rooms":
                Fragment RoomsFragment = new RoomsFragment();
                transaction.replace(R.id.hotel_fragment_view,RoomsFragment).commit();
                break;

            case "Location":
                Fragment locationFragment = new LocationFragment();
                transaction.replace(R.id.hotel_fragment_view,locationFragment).commit();
                break;

            case "Book Now":
                Fragment bookFragment = new BookNowFragment();
                transaction.replace(R.id.hotel_fragment_view,bookFragment).commit();
                break;

        }

    }

}
