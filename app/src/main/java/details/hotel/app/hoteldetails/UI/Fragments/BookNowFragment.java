package details.hotel.app.hoteldetails.UI.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import details.hotel.app.hoteldetails.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookNowFragment extends Fragment {


    public BookNowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_now, container, false);
    }

}
