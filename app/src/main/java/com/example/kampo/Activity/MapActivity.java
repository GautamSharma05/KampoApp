package com.example.kampo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.kampo.R;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapActivity extends AppCompatActivity {
    SupportMapFragment smf;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    FusedLocationProviderClient client;
    double latitude,longitude;
    Button location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        location = findViewById(R.id.location);
        smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        client = LocationServices.getFusedLocationProviderClient(this);

        Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                getMyLocation();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
        location.setOnClickListener(v -> {

            try {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String finalAddress = addresses.get(0).getAddressLine(0);
                DocumentReference documentReference = fStore.collection("Users").document(mAuth.getUid());
                documentReference.update("Address",finalAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MapActivity.this, "Your Address is saved", Toast.LENGTH_SHORT).show();
                            Toast.makeText(MapActivity.this, "Press Back Button", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }


    private void getMyLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                smf.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull @org.jetbrains.annotations.NotNull GoogleMap googleMap) {
                        if(location != null) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                             latitude = location.getLatitude();
                             longitude = location.getLongitude();
                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("You are here....");
                            googleMap.addMarker(markerOptions);
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

                            DocumentReference documentReference = fStore.collection("Users").document(mAuth.getUid());
                            Map<String,Object> location = new HashMap<>();
                            location.put("Latitude",String.valueOf(latitude));
                            location.put("Longitude",String.valueOf(longitude));
                            documentReference.update(location).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Log.d("Location",latLng.toString());
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(MapActivity.this, "Location Not Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

}