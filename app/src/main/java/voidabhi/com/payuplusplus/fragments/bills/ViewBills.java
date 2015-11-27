package voidabhi.com.payuplusplus.fragments.bills;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import voidabhi.com.payuplusplus.R;
import voidabhi.com.payuplusplus.adapters.BillAdapter;
import voidabhi.com.payuplusplus.models.Bill;

public class ViewBills extends Fragment {

    public ViewBills() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().invalidateOptionsMenu();
        View root = inflater.inflate(R.layout.fragment_view_bills, container, false);
        try {
            ListView lv = (ListView) root.findViewById(R.id.billList);
        ArrayList<Bill> bills = new ArrayList<Bill>();
        
        // Seeding with random bills
        bills.add(new Bill(getUri(getActivity(),R.drawable.bill),"Electricity Bill"));
        bills.add(new Bill(getUri(getActivity(),R.drawable.bill2),"Grocery Bill"));
        bills.add(new Bill(getUri(getActivity(),R.drawable.bill3),"Albany Bill"));
        bills.add(new Bill(getUri(getActivity(),R.drawable.bill3),"Cedars Bill"));
            //List<Bill> bills = Bill.listAll(Bill.class);
            if (bills == null)
                bills = new ArrayList<Bill>();
            final BillAdapter billAdapter = new BillAdapter(getActivity(), bills);
            lv.setAdapter(billAdapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent();
                    intent.setAction(android.content.Intent.ACTION_VIEW);
                    Uri hacked_uri = Uri.parse("file://" + billAdapter.getItem(position).getImageUri().getPath());
                    intent.setDataAndType(hacked_uri, "image/*");
                    startActivity(intent);
                }
            });
        }catch(Exception e){
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG);
        }

        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

       // fetch uri from resource
    public Uri getUri(Context context,int resId){
        Resources resources = context.getResources();
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(resId) + '/' + resources.getResourceTypeName(resId) + '/' + resources.getResourceEntryName(resId) );
    }

}
