package voidabhi.com.payuplusplus.fragments.bills.buddy;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import voidabhi.com.payuplusplus.R;


public class PuBuddy extends Fragment {

    public PuBuddy(){

    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_bill, container, false);
        return root;
    }

}
