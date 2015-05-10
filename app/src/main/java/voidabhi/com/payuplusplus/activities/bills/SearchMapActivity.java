package voidabhi.com.payuplusplus.activities.bills;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import voidabhi.com.payuplusplus.R;

public class SearchMapActivity extends AppCompatActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private static final float DEFAULT_MAP_ZOOM = 14.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_map);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {
        mMap.setMyLocationEnabled(true);
        Location location = getCurrentLocation(this);
        if (location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            LatLng coordinate = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(coordinate).title("Albany Bill").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_loc)));
            mMap.addMarker(new MarkerOptions().position(new LatLng(23.4354839,77.037736)).title("Electricity Bill").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_loc)));
            mMap.addMarker(new MarkerOptions().position(new LatLng(28.4985653,77.0426667)).title("Grocery Bill").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_loc)));
           // mMap.addMarker(new MarkerOptions().position(coordinate).title("Vendor").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_loc)));
           // mMap.addMarker(new MarkerOptions().position(coordinate).title("Vendor").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_loc)));
            CameraUpdate update  = CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(coordinate, DEFAULT_MAP_ZOOM));
            mMap.animateCamera(update);
        }
    }

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

    public List<LatLng> getCordinates(){
        List<LatLng> coordinates = new ArrayList<LatLng>();
        coordinates.add(new LatLng(23.4354839,77.037736));
        coordinates.add(new LatLng(28.4985653,77.0426667));
        return coordinates;
    }
}
