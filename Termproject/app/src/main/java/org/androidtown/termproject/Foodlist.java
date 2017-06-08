package org.androidtown.termproject;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Foodlist extends AppCompatActivity {
    Intent intent;
    String outFoodlist;
    String outInfo;
    String outAddress;
    String outFood;
    int len2;
    public String[] foodInfo = new String[200];
    public String[] address = new String[200];
    public String[] food = new String[200];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodlist); //인플레이터
        final ListView listView = (ListView) findViewById(R.id.listview);
        final View header = getLayoutInflater().inflate(R.layout.listview_header,null,false);
        final ImageButton exit = (ImageButton) findViewById(R.id.button2);
        ArrayList<String> arrayList = new ArrayList<String>();
        intent = getIntent();//부모 인텐트 객체
        if(intent!=null)
        {
            String sub="";
            outFoodlist = intent.getStringExtra("foodlist");
            outInfo = intent.getStringExtra("info");
            outAddress = intent.getStringExtra("address");
            outFood = intent.getStringExtra("food");
            int len = outFoodlist.length();
            int len1 = outInfo.length();
            len2 = outAddress.length();
            int len3= outFood.length();
            int j=0, k=0, m=0,n=0;
            int a=0,b=0;
            for(int i =0; i<len; i++)
            {
                if(outFoodlist.charAt(i) == '.')
                {
                    arrayList.add(outFoodlist.substring(k,i));
                    k=i+1;
                    j++;

                }
            }
            for(int l=0; l<len1; l++)
            {
                if(outInfo.charAt(l)=='.')
                {
                    foodInfo[m]=outInfo.substring(n,l);
                    n = l+1;
                    m++;

                }
            }
            for(int c =0; c<len2; c++)
            {
                if(outAddress.charAt(c) == '.')
                {
                    address[b]=outAddress.substring(a,c);
                    a = c+1;
                    b++;

                }
            }
            b=0;
            a=0;
            for(int d =0; d<len3; d++)
            {
                if(outFood.charAt(d) == '.')
                {
                    food[b]=outFood.substring(a,d);
                    a = d+1;
                    b++;

                }
            }


            listView.addHeaderView(header);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, R.layout.simpleitem, arrayList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView adapterView, View view, int position, long id){
                    //음식정보 액티비티 인텐트로 실행 & 정보넘겨주기
                    intent = new Intent (getApplicationContext(), FoodInfo.class);
                    intent.putExtra("info",foodInfo[position-1]);
                    intent.putExtra("address",address[position-1]);
                    intent.putExtra("food",food[position-1]);
                    startActivity(intent);
                }

            });

            exit.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    finish();//버튼 클릭하면 액티비티 끝내기
                }

            });
        }


    }

}
