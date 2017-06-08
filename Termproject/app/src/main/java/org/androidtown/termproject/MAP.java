package org.androidtown.termproject;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle; import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory; import com.google.android.gms.maps.GoogleMap; import com.google.android.gms.maps.MapFragment; import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MAP extends AppCompatActivity implements OnMapReadyCallback {
    Intent intent;
    double lat;
    double lon;
    String food;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        intent = getIntent();//부모 인텐트 객체
        if (intent != null) {
            lat = intent.getDoubleExtra("lat",1);
            lon = intent.getDoubleExtra("lon",1);
            food = intent.getStringExtra("food");

        }
        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(final GoogleMap map) {

        LatLng add = new LatLng(lat, lon);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(add);
        markerOptions.title(food);
        Bitmap image = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier("marker2","drawable",getPackageName()));
        Bitmap resize = Bitmap.createScaledBitmap(image, 100,100,false);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resize));


        Marker food = map.addMarker(markerOptions);
        food.showInfoWindow();

        map.addMarker(markerOptions);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.moveCamera(CameraUpdateFactory.newLatLng(add));
        map.animateCamera(CameraUpdateFactory.zoomTo(17));
    }
}