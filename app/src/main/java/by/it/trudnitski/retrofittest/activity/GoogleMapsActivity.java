package by.it.trudnitski.retrofittest.activity;

import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

import by.it.trudnitski.retrofittest.R;

public class GoogleMapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String ERROR_MESSAGE_GPS = "Please turn ON your GPS, and continue";
    private GoogleMap mMap;
    private int radius = 10000;
    private Boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationProvider;
    private CircleOptions circle;
    private Location currentLocation;
    private List<LatLng> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(GoogleMapsActivity.this);
        init();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getLocation();
        mMap.setMyLocationEnabled(true);
    }

    public void getLocation() {
        mFusedLocationProvider = LocationServices.getFusedLocationProviderClient(this);
        try {
            Task location = mFusedLocationProvider.getLastLocation();
            location.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (task.getResult() != null) {
                        currentLocation = (Location) task.getResult();
                        mLocationPermissionGranted = true;
                        LatLng myLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                        moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 15f);
                        mMap.addMarker(new MarkerOptions().position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())));
                        circle = new CircleOptions();
                        circle.center(myLocation).radius(10000);
                        mMap.addCircle(circle);
                        for (int i = 0; i < list.size(); i++) {
                            double distance = SphericalUtil.computeDistanceBetween(myLocation, list.get(i));
                            if (distance<radius){
                                mMap.addMarker(new MarkerOptions().position(list.get(i)).title("new place"+ i));
                            }
                        }
                    } else {
                        Toast.makeText(GoogleMapsActivity.this, ERROR_MESSAGE_GPS, Toast.LENGTH_LONG).show();
                        getLocation();
                    }
                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    public void init(){
        list = new ArrayList();
        list.add(new LatLng(53.898434, 27.547920));
        list.add(new LatLng(53.896722, 27.550648));
        list.add(new LatLng(53.882377, 27.499018));
        list.add(new LatLng(53.880850, 27.590395));
        list.add(new LatLng(53.889987, 27.614492));
        list.add(new LatLng(53.907163, 27.527724));
        list.add(new LatLng(53.885798, 27.587847));
        list.add(new LatLng(53.933128, 27.651936));
        list.add(new LatLng(53., 27.9));
        list.add(new LatLng(53.12, 27.12));

    }
}
