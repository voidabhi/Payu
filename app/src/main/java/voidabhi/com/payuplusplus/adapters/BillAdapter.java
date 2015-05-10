package voidabhi.com.payuplusplus.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import voidabhi.com.payuplusplus.R;
import voidabhi.com.payuplusplus.models.Bill;

/**
 * Created by ABHIJEET on 09-05-2015.
 */
public class BillAdapter extends ArrayAdapter<Bill> {


    public BillAdapter(Context context, List<Bill> bills) {
        super(context, 0, bills);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bill bill = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_bill, parent, false);
        }

        TextView tvHeading = (TextView) convertView.findViewById(R.id.heading);
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.image);

        tvHeading.setText(bill.getTitle());
        if(bill.getImageUri()==null){
            ivImage.setImageResource(R.drawable.bill);
        }else
            ivImage.setImageURI(bill.getImageUri());

        return convertView;
    }

}


