package details.hotel.app.hoteldetails.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import details.hotel.app.hoteldetails.Customs.CustomFonts.TextViewRobotoregular;
import details.hotel.app.hoteldetails.Model.HotelDetails;
import details.hotel.app.hoteldetails.Model.Rates;
import details.hotel.app.hoteldetails.Model.RoomCategories;
import details.hotel.app.hoteldetails.R;
import details.hotel.app.hoteldetails.UI.Activities.BookAvailablityScreen;
import details.hotel.app.hoteldetails.Utils.Util;
import details.hotel.app.hoteldetails.WebAPI.RateApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ZingoHotels Tech on 11-12-2018.
 */

public class RoomCategoriesListAdapter extends RecyclerView.Adapter<RoomCategoriesListAdapter.ViewHolder> {

    Context context;
    ArrayList<RoomCategories> roomCategories;

    public RoomCategoriesListAdapter(Context context, ArrayList<RoomCategories> roomCategories)
    {
        this.context = context;
        this.roomCategories = roomCategories;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_room_category_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final RoomCategories roomCategory = roomCategories.get(position);

        if(roomCategory!=null){
            holder.mCategoryName.setText(roomCategories.get(position).getCategoryName());

            if(roomCategory.getRates()!=null){
                if(roomCategory.getRates().size()!=0){

                    holder.mRate.setText("₹ "+roomCategory.getRates().get(0).getSellRateForSingle()+"/-(incl GST)");
                }

            }else{
                getRatesByCategoryId(roomCategory.getRoomCategoryId(),holder.mRate);
            }


            holder.mBookNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent book = new Intent(context, BookAvailablityScreen.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Category",roomCategory);
                    book.putExtras(bundle);
                    context.startActivity(book);
                }
            });

        }else{

        }





    }

    @Override
    public int getItemCount() {
        return roomCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mCategoryName,mRate;
        ImageView mCategoryImage;
        Button mBookNow;
        public ViewHolder(View itemView) {
            super(itemView);
            mCategoryName = (TextView)itemView.findViewById(R.id.category_name);
            mRate = (TextView) itemView.findViewById(R.id.rate);
            mCategoryImage = (ImageView) itemView.findViewById(R.id.category_image);
            mBookNow = (Button) itemView.findViewById(R.id.book_category);
        }
    }

    private void getRatesByCategoryId(final int categoryId, final TextView sellRate){

        RateApi apiService = Util.getClient().create(RateApi.class);
        //String authenticationString = Util.getToken(context);
        String authenticationString = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
        Call<ArrayList<Rates>> call = apiService.getRatesByCategoryId(authenticationString,categoryId);

        call.enqueue(new Callback<ArrayList<Rates>>() {
            @Override
            public void onResponse(Call<ArrayList<Rates>> call, Response<ArrayList<Rates>> response) {
                try {
                    int statusCode = response.code();

                    if (statusCode == 200||statusCode==201||statusCode==204) {

                        if(response.body()!=null&&response.body().size()!=0){
                            sellRate.setText("₹ "+response.body().get(0).getSellRateForSingle()+"/-(incl GST)");
                        }




                    }else {

                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Rates>> call, Throwable t) {
                // Log error here since request failed

                Log.e("TAG", t.toString());
            }
        });


    }


}
