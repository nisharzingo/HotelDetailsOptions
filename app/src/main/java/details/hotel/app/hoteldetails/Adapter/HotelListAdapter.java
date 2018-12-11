package details.hotel.app.hoteldetails.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import details.hotel.app.hoteldetails.Customs.CustomFonts.TextViewRobotoregular;
import details.hotel.app.hoteldetails.Model.HotelDetails;
import details.hotel.app.hoteldetails.R;
import details.hotel.app.hoteldetails.UI.Activities.HotelOptionsScreen;
import details.hotel.app.hoteldetails.Utils.PreferenceHandler;

/**
 * Created by ZingoHotels Tech on 11-12-2018.
 */

public class HotelListAdapter extends RecyclerView.Adapter<HotelListAdapter.ViewHolder> {

    Context context;
    ArrayList<HotelDetails> hotelDetailsArrayList;

    public HotelListAdapter(Context context, ArrayList<HotelDetails> hotelDetailsArrayList)
    {
        this.context = context;
        this.hotelDetailsArrayList = hotelDetailsArrayList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_hotel_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try{

            holder.mHotelName.setText(hotelDetailsArrayList.get(position).getHotelDisplayName());
            holder.mHotelLocality.setText(hotelDetailsArrayList.get(position).getLocalty());

            holder.mBaseHotel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PreferenceHandler.getInstance(context).setHotelId(hotelDetailsArrayList.get(position).getHotelId());
                    PreferenceHandler.getInstance(context).setHotelName(hotelDetailsArrayList.get(position).getHotelName());
                    //Intent sameActivity = new Intent(context, HotelOptionsScreen.class);
                    ((Activity)context).recreate();


                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return hotelDetailsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextViewRobotoregular mHotelName,mHotelLocality;
        LinearLayout mBaseHotel;
        public ViewHolder(View itemView) {
            super(itemView);
            mHotelName = (TextViewRobotoregular)itemView.findViewById(R.id.adapter_hotel_name);
            mHotelLocality = (TextViewRobotoregular) itemView.findViewById(R.id.adapter_hotel_location);
            mBaseHotel = (LinearLayout) itemView.findViewById(R.id.base_hotel_layout);
        }
    }
}
