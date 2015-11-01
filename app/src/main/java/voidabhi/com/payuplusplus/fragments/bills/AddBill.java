package voidabhi.com.payuplusplus.fragments.bills;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.soundcloud.android.crop.Crop;
import com.soundcloud.android.crop.CropImageActivity;
import com.wefika.flowlayout.FlowLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import voidabhi.com.payuplusplus.MainActivity;
import voidabhi.com.payuplusplus.R;
import voidabhi.com.payuplusplus.models.Bill;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBill extends Fragment{

    public static ImageView resultView;
    public static FlowLayout flowContainer;
    private static MaterialEditText titleEdit;
    private static Handler handler = new Handler();

    public AddBill() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_bill, container, false);
        final MaterialEditText tagsEdit = (MaterialEditText)root.findViewById(R.id.tagsedit);
        titleEdit = (MaterialEditText)root.findViewById(R.id.titleedit);
        flowContainer = (FlowLayout)root.findViewById(R.id.flow);
        resultView = (ImageView) root.findViewById(R.id.result_image);
        final Button addImageButton = (Button)root.findViewById(R.id.addImageButton);
        tagsEdit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                {
                    Button tag = (Button)View.inflate(getActivity(),R.layout.tag_button,null);
                    tag.setText(tagsEdit.getText());
                    tag.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            return false;
                        }
                    });
                    if(tag.getText().length()>0){
                        flowContainer.addView(tag);
                        tagsEdit.setText("");
                    }

                }
                return false;
            }
        });

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultView.setImageDrawable(null);
                resultView.setVisibility(View.VISIBLE);
                Crop.pickImage(getActivity());
            }
        });
        return root;
    }

    public static void uploadFile(final Context context,final Uri fileUri) {
        // mBuilder.setContentTitle("Uploading...").setSmallIcon(R.drawable.ic_launcher);
        final File fileToUpload = new File(fileUri.getPath());
        Toast.makeText(context,"File Uploading of size.... "+fileToUpload.length(), Toast.LENGTH_LONG).show();
        Ion.with(context)
                .load("http://sanchitgoel.net78.net/ocr/load.php")
                .uploadProgressHandler(new ProgressCallback() {
                    @Override
                    public void onProgress(long uploaded, long total) {

                    }
                })
                .setTimeout(60 * 60 * 1000)
                .setMultipartFile("fileToUpload", "image/jpeg", fileToUpload)
                .asJsonObject()
                        // run a callback on completion
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        if (e != null) {
                            Toast.makeText(context, "Error uploading file "+e.getMessage(), Toast.LENGTH_LONG).show();
                            return;
                        }
                       Toast.makeText(context, "Tags fetched...", Toast.LENGTH_LONG).show();
                        flowContainer.removeAllViews();
                       addTags(context,result.getAsJsonArray("tags"),fileUri);

                    }
                });

    }

    // adding tags from json objs
    public static void addTags(final Context context,final JsonArray tags,final Uri imageUri){

        final String title = titleEdit.getText().toString();

       Runnable addingTagsRunnable = new Runnable() {
           @Override
           public void run() {

               String tagStrings = "";
               for(int i=0;i<tags.size();i++) {
                   Button tag = (Button)View.inflate(context,R.layout.tag_button, null);
                   tag.setText(tags.get(i).getAsString());
                   tagStrings+=","+tags.get(i).getAsString();
                   tag.setOnLongClickListener(new View.OnLongClickListener() {
                       @Override
                       public boolean onLongClick(View v) {
                           return false;
                       }
                   });
                   if (tag.getText().length() > 0) {
                       flowContainer.addView(tag);
                   }
               }

              Location location = getCurrentLocation(context);
//               try {
//                   Bill newBill = new Bill(title, imageUri.getPath(), tagStrings, location.getLatitude() + "," + location.getLongitude());
//                   newBill.save();
//               }catch(Exception e){
//                   Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
//               }
           }
       };

        handler.post(addingTagsRunnable);
    }

    // helper to get current location
    public static Location getCurrentLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location location = null;
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!(isGPSEnabled || isNetworkEnabled)) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            context.startActivity(intent);
        } else {
            if (location == null) {
                if (isGPSEnabled) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }

                if (isNetworkEnabled) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }
        }
        return location;
    }
}
