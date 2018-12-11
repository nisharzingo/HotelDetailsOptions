package details.hotel.app.hoteldetails.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import details.hotel.app.hoteldetails.Customs.CustomFonts.TextViewRobotoregular;
import details.hotel.app.hoteldetails.Model.HotelDetails;
import details.hotel.app.hoteldetails.R;

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mHotelName.setText(hotelDetailsArrayList.get(position).getHotelDisplayName());
        holder.mHotelLocality.setText(hotelDetailsArrayList.get(position).getLocalty());
    }

    @Override
    public int getItemCount() {
        return hotelDetailsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextViewRobotoregular mHotelName,mHotelLocality;
        public ViewHolder(View itemView) {
            super(itemView);
            mHotelName = (TextViewRobotoregular)itemView.findViewById(R.id.adapter_hotel_name);
            mHotelLocality = (TextViewRobotoregular) itemView.findViewById(R.id.adapter_hotel_location);
        }
    }
}
