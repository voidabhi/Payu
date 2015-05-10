package voidabhi.com.payuplusplus;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.plus.model.people.Person;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.soundcloud.android.crop.Crop;

import java.io.File;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import voidabhi.com.payuplusplus.activities.bills.SearchMapActivity;
import voidabhi.com.payuplusplus.fragments.bills.AddBill;
import voidabhi.com.payuplusplus.fragments.bills.ViewBills;


public class MainActivity extends MaterialNavigationDrawer {

    NotificationManager mNotifyManager;
    int notificationId = 123;
    NotificationCompat.Builder mBuilder;

    @Override
    public void init(Bundle savedInstanceState) {

        setTitle("PayU++");
        setFirstAccountPhoto(getResources().getDrawable(R.drawable.web_hi_res_512));
        setDrawerHeaderImage(new ColorDrawable(getResources().getColor(R.color.theme_color)));
        // create sections
        this.addSection(customStyle(newSection("Bills", new ViewBills())));
        this.addSection(customStyle(newSection("PayuBuddy", new ViewBills())));
        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        menu.clear();

        String title = this.getCurrentSection().getTitle();

        if (title.equals("Bills")) {
            getMenuInflater().inflate(R.menu.menu_bills, menu);
        }
        if (title.equals("Add New Bill")){
            getMenuInflater().inflate(R.menu.menu_add_bill, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                this.setFragmentChild(new AddBill(), "Add New Bill");
                break;
            case R.id.action_search:
                startActivity(new Intent(MainActivity.this, SearchMapActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    // Image

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }


    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK && AddBill.resultView != null) {
            AddBill.resultView.setImageURI(Crop.getOutput(result));
            try {
                AddBill.uploadFile(this,Crop.getOutput(result));
            }catch (Exception e){
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public MaterialSection customStyle(MaterialSection section){
        TextView sectionView = (TextView)section.getView().findViewById(R.id.section_text);
        sectionView.setTextAppearance(this,R.style.TextAppearance_SectionText);
        return section;
    }

}
