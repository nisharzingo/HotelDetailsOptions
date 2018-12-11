package details.hotel.app.hoteldetails.UI.Fragments;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import details.hotel.app.hoteldetails.Customs.CustomFonts.TextViewSFProDisplayRegular;
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
public class ContactFragment extends Fragment {

    TextViewSFProDisplayRegular mAddress,mHotelName;
    LinearLayout mPhone,mEmail,mWhatsapp;


    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try{
            View view = inflater.inflate(R.layout.fragment_contact,container,false);

            mAddress = (TextViewSFProDisplayRegular)view.findViewById(R.id.hotel_address);
            mHotelName = (TextViewSFProDisplayRegular)view.findViewById(R.id.hotel_name);

            mPhone = (LinearLayout) view.findViewById(R.id.phone_number_lay);
            mEmail = (LinearLayout)view.findViewById(R.id.email_lay);
            mWhatsapp = (LinearLayout)view.findViewById(R.id.whatsapp_open);


            mPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    if (ActivityCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:+919741125630"));
                        startActivity(callIntent);
                    }else{
                        Toast.makeText(getActivity(), "Permission denied.Please give access in Settings", Toast.LENGTH_SHORT).show();
                    }

                }


            });

            mEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(Intent.ACTION_SENDTO);
                    i.setType("message/rfc822");
                    i.setData(Uri.parse("mailto:"));
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"info@omgroupgokarna.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, "Inquiry");

                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            mWhatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String digits = "\\d+";
                    String mob_num = "9741125630";
                    if (mob_num.matches(digits))
                    {
                        try {

                            Uri uri = Uri.parse("whatsapp://send?phone=+91" + mob_num);
                            Intent i = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(i);
                        }
                        catch (ActivityNotFoundException e){
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "WhatsApp not installed.", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });
           // getHotel(98);
            return view;
        }catch (Exception e){
            e.printStackTrace();
            return null;
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


                                mAddress.setText(hotelDetails.getHotelStreetAddress()+",\n"+hotelDetails.getCity()+",\n"+hotelDetails.getState()+"-"+hotelDetails.getPincode()+",\n"+hotelDetails.getCountry());
                                mHotelName.setText(hotelDetails.getHotelName());

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
