package com.example.rose.zoo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.example.rose.zoo.R;
import com.example.rose.zoo.models.Pin;
import com.example.rose.zoo.utils.PinsApiInterface;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rose on 12/28/2016.
 */

public class ZooMapFragment extends SupportMapFragment {
    public static ZooMapFragment getInstance(){
        ZooMapFragment fragment = new ZooMapFragment();

        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target( new LatLng(39.7500, -104.9500))
                .zoom( 16f )
                .bearing( 0.0f )
                .tilt( 0.0f )
                .build();

        getMap().animateCamera(CameraUpdateFactory.newCameraPosition( cameraPosition ), null);
        getMap().setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //to turn on the traffic
        getMap().setTrafficEnabled( true );

        //add a button of plus and minus, to make zoom in and out in the map possible
        getMap().getUiSettings().setZoomControlsEnabled( true );

        //add a mark to the zoo
        MarkerOptions options = new MarkerOptions().position( new LatLng(39.7500, -104.9500));
        options.title("Zoo");
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        //add a mark on the map where the zoo is
        getMap().addMarker( options );

        getMap().setOnMarkerClickListener( new GoogleMap.OnMarkerClickListener(){
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return true;
            }
        });

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(getString(R.string.pins_feed))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PinsApiInterface pinsApiInterface = restAdapter.create(PinsApiInterface.class);
        Call<List<Pin>> call = pinsApiInterface.getStreams();
        call.enqueue(new Callback<List<Pin>>() {
            @Override
            public void onResponse(Call<List<Pin>> call, Response<List<Pin>> response) {
                if (response.isSuccessful()){
                    List<Pin> pins = response.body();
                    if( !isAdded() || pins == null || pins.isEmpty()) {
                        return;
                    }
                    for (Pin pin : pins) {
                        Log.e(getString(R.string.app_name), pin.getName());
                        MarkerOptions options1 = new MarkerOptions().position( new LatLng( pin.getLatitude(), pin.getLongitude() ) );
                        options1.title( pin.getName() );
                        options1.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_GREEN ) );
                        getMap().addMarker( options1 );
                    }
                }
                else {
                    Log.e(getString(R.string.app_name), "Retrofit error: " + "No response. " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Pin>> call, Throwable t) {

            }
        });

    }
}
