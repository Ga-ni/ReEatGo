package org.androidtown.termproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FoodInfo extends AppCompatActivity {
    Intent intent;
    String foodInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);
        intent = getIntent();//부모 인텐트 객체
        final TextView textView = (TextView) findViewById(R.id.textView4);
        if (intent != null) {
            foodInfo = intent.getStringExtra("info");
        }
        textView.setText(foodInfo);
    }

}