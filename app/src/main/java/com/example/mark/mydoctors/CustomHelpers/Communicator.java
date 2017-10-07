package com.example.mark.mydoctors.CustomHelpers;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Mark on 23.05.2016..
 */
public class Communicator {
    private static Object o;
    private static boolean isButtonPressed = false;
    private static Context context;

    public static void setComponent(Object _o) {
        o = _o;
    }


    public static Object getComponent() {
        return o;
    }

    public static void setButtonPressed(boolean bool) {
        isButtonPressed = bool;
    }

    public static boolean getIsButtonPressed() {
        return isButtonPressed;
    }

    public static LatLng GetLatitudeAndLongitude(String address) {
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
