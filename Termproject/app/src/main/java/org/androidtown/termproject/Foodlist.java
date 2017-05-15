package org.androidtown.termproject;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Foodlist extends AppCompatActivity {
    Intent intent;
    String outFoodlist;
    String outInfo;
    public String[] foodName = new String[50];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodlist); //인플레이터
        final ListView listView = (ListView) findViewById(R.id.listview);
        final Button exit = (Button) findViewById(R.id.button2);
        final TextView textView = (TextView) findViewById(R.id.textView3);

        ArrayList<String> arrayList = new ArrayList<String>();
        intent = getIntent();//부모 인텐트 객체
        if(intent!=null)
        {
            String sub="";
            outFoodlist = intent.getStringExtra("foodlist");
            outInfo = intent.getStringExtra("info");
            int len = outFoodlist.length();
            int j=0;
            int k=0;
            for(int i =0; i<len; i++)
            {
                if(outFoodlist.charAt(i) == ' ')
                {
                    arrayList.add(outFoodlist.substring(k,i));
                    //foodName[j]=outFoodlist.substring(k,i);
                    k=i+1;
                    j++;

                }
            }

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_item, arrayList);
        listView.setAdapter(adapter);




        exit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();//버튼 클릭하면 액티비티 끝내기
            }

        });
    }

}
