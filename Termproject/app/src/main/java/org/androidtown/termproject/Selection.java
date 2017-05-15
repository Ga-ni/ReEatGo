package org.androidtown.termproject;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Selection extends AppCompatActivity {

    s1 S1_0;
    S2_0 s2_0;
    S2_1 s2_1;
    S3_0 s3_0;
    S3_1 s3_1;
    S3_2 s3_2;
    S3_3 s3_3;

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
    }

    public void onFragmentChanged(int index){
        if (index == 100){ //선택1번째 클래스 연결
            getSupportFragmentManager().beginTransaction().replace(R.id.container,S1_0).commit();
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

    }
    public void getIndex(int index)
    {
       Intent intent = new Intent(this,MainActivity.class);
        Bundle bundle = new Bundle();
        if(index ==301){ //음식리스트 연결
            bundle.putInt("group_id",301);
            intent.putExtras(bundle);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
        else if(index ==302){
            bundle.putInt("group_id",302);
            intent.putExtras(bundle);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
        else if(index ==303){
            bundle.putInt("group_id",303);
            intent.putExtras(bundle);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }

        else if(index ==304){
            bundle.putInt("group_id",304);
            intent.putExtras(bundle);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
        else if(index ==305){
            bundle.putInt("group_id",305);
            intent.putExtras(bundle);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
        else if(index ==306){
            bundle.putInt("group_id",306);
            intent.putExtras(bundle);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
        else if(index ==307){
            bundle.putInt("group_id",307);
            intent.putExtras(bundle);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
        else if(index ==308){
            bundle.putInt("group_id",308);
            intent.putExtras(bundle);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }

}
}