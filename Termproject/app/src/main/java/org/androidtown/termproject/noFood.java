package org.androidtown.termproject;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


public class noFood extends AppCompatActivity {
    Intent intent;
    int group_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("count","노푸드찡");
        setContentView(R.layout.activity_no_food);
        ImageButton button6 = (ImageButton)findViewById(R.id.back); // 뒤로가기
        button6.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                intent = getIntent();
                group_id=intent.getIntExtra("group_id",0);
                Intent toselection=new Intent(getApplicationContext(),MainActivity.class);
                toselection.putExtra("group_id",group_id);
                setResult(111,toselection);
                finish();

            }

        });
    }
}