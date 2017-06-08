
package org.androidtown.termproject;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Selection extends AppCompatActivity {
    s1 S1_0;
    S2_0 s2_0;
    S2_1 s2_1;
    S3_0 s3_0;
    S3_1 s3_1;
    S3_2 s3_2;
    S3_3 s3_3;
    s4_0 s4_0;
    s4_1 s4_1;
    s4_2 s4_2;
    s4_3 s4_3;
    s4_4 s4_4;
    s4_5 s4_5;
    s4_6 s4_6;
    s4_7 s4_7;
    Intent intent;
    int groupIndex=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        S1_0 = new s1();//(s1) getSupportFragmentManager().findFragmentById(R.id.s1_0);
        s2_0 = new S2_0();
        s2_1 = new S2_1();
        s3_0 = new S3_0();
        s3_1 = new S3_1();
        s3_2 = new S3_2();
        s3_3 = new S3_3();
        s4_0 = new s4_0();
        s4_1 = new s4_1();
        s4_2 = new s4_2();
        s4_3 = new s4_3();
        s4_4 = new s4_4();
        s4_5 = new s4_5();
        s4_6 = new s4_6();
        s4_7 = new s4_7();
        //getSupportFragmentManager().beginTransaction().replace(R.id.container,S1_0).commit();
        Intent noMenuintent=getIntent();
        if(noMenuintent!=null){
            groupIndex=noMenuintent.getIntExtra("group_id",0);
            //int type_id=noMenuintent.getIntExtra("type_id",0);
            this.onFragmentChanged(groupIndex);
        }
    }

    public void onFragmentChanged(int index){
        if (index == 100){ //선택1번째 클래스 연결
            getSupportFragmentManager().beginTransaction().replace(R.id.container,S1_0).commit();
        }
        else if(index==-1){
            Intent intent= new Intent(getApplicationContext(),MainActivity.class);
            setResult(-1,intent);
            finish();
        }
        else if(index ==101){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,s2_0).commit();
        }
        else if(index ==102){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,s2_1).commit();
        }
        else if(index == 201){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,s3_0).commit();
        }
        else if(index == 202){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,s3_1).commit();
        }
        else if(index ==203){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,s3_2).commit();
        }
        else if(index ==204){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,s3_3).commit();
        }
        else if (index == 301) {
            groupIndex = 301;
            getSupportFragmentManager().beginTransaction().replace(R.id.container,s4_0).commit();
        }
        else if (index == 302) {
            groupIndex = 302;
            getSupportFragmentManager().beginTransaction().replace(R.id.container,s4_1).commit();
        }
        else if (index == 303) {
            groupIndex = 303;
            getSupportFragmentManager().beginTransaction().replace(R.id.container,s4_2).commit();
        }
        else if (index == 304) {
            groupIndex = 304;
            getSupportFragmentManager().beginTransaction().replace(R.id.container,s4_3).commit();
        }
        else if (index == 305) {
            groupIndex = 305;
            getSupportFragmentManager().beginTransaction().replace(R.id.container,s4_4).commit();
        }
        else if (index == 306) {
            groupIndex = 306;
            getSupportFragmentManager().beginTransaction().replace(R.id.container,s4_5).commit();
        }
        else if (index == 307) {
            groupIndex = 307;
            getSupportFragmentManager().beginTransaction().replace(R.id.container,s4_6).commit();
        }
        else if (index == 308) {
            groupIndex = 308;
            getSupportFragmentManager().beginTransaction().replace(R.id.container,s4_7).commit();
        }


    }

    public void getIndex(int index)
    {
        Intent intent = new Intent(this,MainActivity.class);
        Bundle bundle = new Bundle();
        if(index ==401){ //음식리스트 연결
            bundle.putInt("group_id",groupIndex);
            bundle.putInt("type_id",401);
            intent.putExtras(bundle);
            setResult(1,intent);
            finish();
        }
        else if(index ==402){
            bundle.putInt("group_id",groupIndex);
            bundle.putInt("type_id",402);
            intent.putExtras(bundle);
            setResult(1,intent);
            finish();
        }
        else if(index ==403){
            bundle.putInt("group_id",groupIndex);
            bundle.putInt("type_id",403);
            intent.putExtras(bundle);
            setResult(1,intent);
            finish();
        }

        else if(index ==404){
            bundle.putInt("group_id",groupIndex);
            bundle.putInt("type_id",404);
            intent.putExtras(bundle);
            setResult(1,intent);
            finish();
        }
        else if(index ==405){
            bundle.putInt("group_id",groupIndex);
            bundle.putInt("type_id",405);
            intent.putExtras(bundle);
            setResult(1,intent);
            finish();
        }
        else if(index ==406){
            bundle.putInt("group_id",groupIndex);
            bundle.putInt("type_id",406);
            intent.putExtras(bundle);
            setResult(1,intent);
            finish();
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent= new Intent(getApplicationContext(),MainActivity.class);
        setResult(-1,intent);
        finish();
    }



}