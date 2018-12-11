package details.hotel.app.hoteldetails.UI.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import details.hotel.app.hoteldetails.Adapter.RoomCategoriesListAdapter;
import details.hotel.app.hoteldetails.Customs.CustomFonts.TextViewRobotoregular;
import details.hotel.app.hoteldetails.Model.AvailabiltyCheckPostData;
import details.hotel.app.hoteldetails.Model.RoomCategories;
import details.hotel.app.hoteldetails.R;
import details.hotel.app.hoteldetails.Utils.ThreadExecuter;
import details.hotel.app.hoteldetails.Utils.Util;
import details.hotel.app.hoteldetails.WebAPI.HotelsDetailsAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAvailablityScreen extends AppCompatActivity {

    TextViewRobotoregular mCategoryName,mAvailablity,mBook,mChangeCategoryName,mDateText;
    TextView mBed,mAdult,mChild;
    LinearLayout mChangeCategory,mDateChange;
    ImageView mBedAdd,mBedRemove,mAdultAdd,mAdultRemove,mChildAdd,mChildRemove;
    RoomCategories roomCategory;

    ArrayList<RoomCategories> roomCategoriesArrayList;

    String fromDate = "",toDate="",from="",to="";
    SimpleDateFormat sdf,sda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{

            setContentView(R.layout.activity_book_availablity_screen);

            mCategoryName = (TextViewRobotoregular)findViewById(R.id.category_name_book);
            mAvailablity = (TextViewRobotoregular)findViewById(R.id.room_avail);
            mBook = (TextViewRobotoregular)findViewById(R.id.book_category_text);
            mChangeCategoryName = (TextViewRobotoregular)findViewById(R.id.changeCategory);
            mDateText = (TextViewRobotoregular)findViewById(R.id.date_text);

            mBed = (TextView)findViewById(R.id.bed_count);
            mAdult = (TextView)findViewById(R.id.adult_count);
            mChild = (TextView)findViewById(R.id.child_count);

            mChangeCategory = (LinearLayout)findViewById(R.id.category_change);
            mDateChange = (LinearLayout)findViewById(R.id.date_layout);

            mBedAdd = (ImageView)findViewById(R.id.bed_add);
            mBedRemove = (ImageView)findViewById(R.id.bed_remove);
            mAdultAdd = (ImageView)findViewById(R.id.adult_add);
            mAdultRemove = (ImageView)findViewById(R.id.adult_remove);
            mChildAdd = (ImageView)findViewById(R.id.child_add);
            mChildRemove = (ImageView)findViewById(R.id.child_remove);

            Bundle bundle =getIntent().getExtras();

            roomCategoriesArrayList = new ArrayList<>();
            sdf = new SimpleDateFormat("MMM dd");
            sda = new SimpleDateFormat("MM/dd/yyyy");

            Calendar cal = Calendar.getInstance();
            from = sdf.format(cal.getTime());
            cal.add(Calendar.DATE,1);
            to = sdf.format(cal.getTime());

            mDateText.setText(""+from+" - "+to);


            AvailabiltyCheckPostData avpd = new AvailabiltyCheckPostData();
            avpd.setHotelId(1);
            //avpd.setFromDate();


            if(bundle!=null){

                roomCategory = (RoomCategories)bundle.getSerializable("Category");
            }

            if(roomCategory!=null){

                mCategoryName.setText(""+roomCategory.getCategoryName());

            }else{

            }



            mChangeCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    getRoomCategories();

                }
            });

            mBedAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String bedValue = mBed.getText().toString();

                    try{

                        int bedCount = Integer.parseInt(bedValue);

                        if(bedCount>=3){
                            Toast.makeText(BookAvailablityScreen.this, "Maximum bed", Toast.LENGTH_SHORT).show();
                        }else{

                            bedCount = bedCount+1;
                            mBed.setText(""+bedCount);
                            mBedRemove.setEnabled(true);

                            if(bedCount>=3){
                                mBedAdd.setEnabled(false);
                            }

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

            mBedRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String bedValue = mBed.getText().toString();

                    try{

                        int bedCount = Integer.parseInt(bedValue);

                        if(bedCount<2){
                            Toast.makeText(BookAvailablityScreen.this, "Minimum bed", Toast.LENGTH_SHORT).show();
                        }else{

                            bedCount = bedCount-1;
                            mBed.setText(""+bedCount);
                            mBedAdd.setEnabled(true);

                            if(bedCount<2){
                                mBedRemove.setEnabled(false);
                            }

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

            mAdultAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String adultValue = mAdult.getText().toString();

                    try{

                        int adultCount = Integer.parseInt(adultValue);

                        if(adultCount>=3){
                            Toast.makeText(BookAvailablityScreen.this, "Maximum bed", Toast.LENGTH_SHORT).show();
                        }else{

                            adultCount = adultCount+1;
                            mAdult.setText(""+adultCount);
                            mAdultRemove.setEnabled(true);

                            if(adultCount>=3){
                                mAdultAdd.setEnabled(false);
                            }

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

            mAdultRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String adultValue = mAdult.getText().toString();

                    try{

                        int adultCount = Integer.parseInt(adultValue);

                        if(adultCount<2){
                            Toast.makeText(BookAvailablityScreen.this, "Minimum bed", Toast.LENGTH_SHORT).show();
                        }else{

                            adultCount = adultCount-1;
                            mAdult.setText(""+adultCount);
                            mAdultAdd.setEnabled(true);

                            if(adultCount<2){
                                mAdultRemove.setEnabled(false);
                            }

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });

            mChildAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String childValue = mChild.getText().toString();

                    try{

                        int childCount = Integer.parseInt(childValue);

                        if(childCount>=3){
                            Toast.makeText(BookAvailablityScreen.this, "Maximum bed", Toast.LENGTH_SHORT).show();
                        }else{

                            childCount = childCount+1;
                            mChild.setText(""+childCount);
                            mChildRemove.setEnabled(true);

                            if(childCount>=3){
                                mChildAdd.setEnabled(false);
                            }

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }
            });

            mChildRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String childValue = mChild.getText().toString();

                    try{

                        int childCount = Integer.parseInt(childValue);

                        if(childCount<1){
                            Toast.makeText(BookAvailablityScreen.this, "Minimum bed", Toast.LENGTH_SHORT).show();
                        }else{

                            childCount = childCount-1;
                            mChild.setText(""+childCount);
                            mChildAdd.setEnabled(true);

                            if(childCount<1){
                                mChildRemove.setEnabled(false);
                            }

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

        }catch (Exception e){
            e.printStackTrace();
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

                                   roomCategoriesArrayList = hotelCategories;

                                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(BookAvailablityScreen.this);
                                    builderSingle.setIcon(R.drawable.om_group_logo);
                                    builderSingle.setTitle("Select Category:-");

                                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(BookAvailablityScreen.this, android.R.layout.select_dialog_singlechoice);

                                    for (RoomCategories rc: roomCategoriesArrayList) {

                                        arrayAdapter.add(rc.getCategoryName());

                                    }
                                    builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });

                                    builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String strName = arrayAdapter.getItem(which);
                                           mChangeCategoryName.setText(strName+"");
                                           mCategoryName.setText(strName+"");

                                           roomCategory = roomCategoriesArrayList.get(which);

                                        }
                                    });
                                    builderSingle.show();

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
