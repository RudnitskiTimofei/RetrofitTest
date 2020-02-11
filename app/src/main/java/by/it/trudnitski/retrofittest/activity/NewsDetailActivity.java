package by.it.trudnitski.retrofittest.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import by.it.trudnitski.retrofittest.R;
import by.it.trudnitski.retrofittest.util.CustomCircleView;

public class NewsDetailActivity extends AppCompatActivity {
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 0;
    private static final int REQUEST_ERROR = 0;
    private Boolean mLocationPermissionGranted = false;
    private String title;
    private String sourceName;
    private String description;
    private String imageUrl;
    private TextView titleView;
    private TextView sourceNameView;
    private TextView descriptioView;
    private ImageView picassoView;
    private Button buttonCustomView;
    private Intent intent;
    private Button buttonOpenMap;
    private GoogleApiClient mClient;
    private GoogleApiAvailability apiAvailability;
    private String[] permissions = {FINE_LOCATION, COURSE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        titleView = findViewById(R.id.title_detail);
        sourceNameView = findViewById(R.id.source_name_detail);
        descriptioView = findViewById(R.id.description_detail);
        picassoView = findViewById(R.id.picasso_image);
        buttonOpenMap = findViewById(R.id.button_map);
        buttonCustomView = findViewById(R.id.button_custom_view);
        mClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).build();

        intent = getIntent();
        title = intent.getExtras().getString("title");
        sourceName = intent.getExtras().getString("source:name");
        description = intent.getExtras().getString("description");
        imageUrl = intent.getExtras().getString("imageUrl");

        titleView.setText(title);
        sourceNameView.setText(sourceName);
        descriptioView.setText(description);
        Picasso.get().load(imageUrl).into(picassoView);
        openMapClick();
        openCustomView();
    }

    public void openMapClick() {
        buttonOpenMap.setOnClickListener(v -> {
            if (hasLocationPermission()) {
                locationRequest();
                Intent intent1 = new Intent(NewsDetailActivity.this, GoogleMapsActivity.class);
                startActivity(intent1);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permissions, LOCATION_PERMISSION_REQUEST_CODE);
                }
            }
        });
    }

    public void openCustomView() {
        buttonCustomView.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), ShowCustomViewActivity.class);
            startActivity(intent1);
        });
    }

    private boolean hasLocationPermission() {
        int result = ContextCompat.checkSelfPermission(this, permissions[0]);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public void locationRequest() {
        LocationRequest request = LocationRequest.create();
        request.setInterval(0);
        request.setNumUpdates(1);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (hasLocationPermission()) {
                Intent intent = new Intent(NewsDetailActivity.this, GoogleMapsActivity.class);
                startActivity(intent);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (errorCode != ConnectionResult.SUCCESS) {
            Dialog errorDialog = apiAvailability.getErrorDialog(this, errorCode, REQUEST_ERROR, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
            errorDialog.show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mClient.disconnect();
    }
}