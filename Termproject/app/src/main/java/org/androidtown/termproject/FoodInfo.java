package org.androidtown.termproject;

import android.app.FragmentManager;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class FoodInfo extends AppCompatActivity {
    Intent intent;
    Intent intent2;
    String foodInfo;
    String address;
    String food;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);
        final Geocoder geocoder = new Geocoder(this);
        intent = getIntent();//부모 인텐트 객체
        final ImageButton b4 = (ImageButton) findViewById(R.id.button2);
        final TextView textView = (TextView) findViewById(R.id.textView4);
        if (intent != null) {
            foodInfo = intent.getStringExtra("info");
            address = intent.getStringExtra("address");
            food = intent.getStringExtra("food");

        }
        textView.setText(foodInfo);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2 = new Intent(getApplicationContext(), MAP.class);
                List<Address> list = null;
                try {
                    list = geocoder.getFromLocationName
                            (address, // 지역 이름
                                    2500); // 읽을 개수
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (list != null) {
                    if (list.size() == 0) {
                    } else {
                        // 해당되는 주소로 인텐트 날리기
                        Address addr = list.get(0);
                        double lat = addr.getLatitude();
                        double lon = addr.getLongitude();
                        intent2.putExtra("lat", lat);
                        intent2.putExtra("lon", lon);
                        intent2.putExtra("food",food);
                        startActivity(intent2);
                        list = null;

                    }
                }

            }
        });

    }
}