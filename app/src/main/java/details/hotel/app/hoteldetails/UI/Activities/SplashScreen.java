package details.hotel.app.hoteldetails.UI.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import details.hotel.app.hoteldetails.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{

            setContentView(R.layout.activity_splash_screen);

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {


                    Intent i = new Intent(SplashScreen.this, HotelOptionsScreen.class);
                    startActivity(i);
                    finish();

                }
            }, 1000);


        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
