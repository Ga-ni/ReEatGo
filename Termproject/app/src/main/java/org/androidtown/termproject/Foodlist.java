package org.androidtown.termproject;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Foodlist extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodlist); //인플레이터
        final TextView foodlist = (TextView) findViewById(R.id.textView3);
        final Button exit = (Button) findViewById(R.id.button2);
        intent = getIntent();//부모 인텐트 객체
        if(intent!=null)
        {
            String outFoodlist = intent.getStringExtra("foodlist");

            foodlist.setText(outFoodlist); //TextView 메시지 설정하기

        }
        exit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();//버튼 클릭하면 액티비티 끝내기
            }

        });
    }
}