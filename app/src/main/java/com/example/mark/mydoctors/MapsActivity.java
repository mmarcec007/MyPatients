package com.example.mark.mydoctors;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.mark.mydoctors.CustomHelpers.Communicator;
import com.example.mark.mydoctors.DatabaseOperations.DBHelper;
import com.example.mark.mydoctors.Model.Patient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    Context context;
    DBHelper mydb;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(Communicator.getIsButtonPressed() == false) {
            LatLng sydney = (LatLng)Communicator.getComponent();

            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }

        else {
            mydb = new DBHelper(this);
            ArrayList<Patient> array_list = mydb.getAllPatients();

            LatLng sydney = new LatLng(0,0);

            for(int i = 0; i < array_list.size(); i++) {
                sydney = new LatLng(GetLatitude(array_list.get(i).getPlace() + ", " + array_list.get(i).getStreet()),
                        GetLongitude(array_list.get(i).getPlace() + ", " + array_list.get(i).getStreet()));
                mMap.addMarker(new MarkerOptions().position(sydney).title(array_list.get(i).getName() + " " + array_list.get(i).getSurname()));
            }

            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }




    }

    public double GetLatitude(String address) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        double l = 0;
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses.size() > 0) {
                l = addresses.get(0).getLatitude();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return l;
    }

    public double GetLongitude(String address) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        double l = 0;
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses.size() > 0) {
                l = addresses.get(0).getLongitude();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return l;
    }

    public LatLng GetLatitudeAndLongitude(String address) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        LatLng l = new LatLng(0, 0);
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses.size() > 0) {
                l = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return l;
    }

}
