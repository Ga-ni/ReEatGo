package org.androidtown.termproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    SQLiteDatabase database;
    String tableName = "FOOD";
    TextView textView1;
    TextView textView2;
    Intent intent;
    Intent intent1;
    String foodlist ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = (TextView) findViewById(R.id.textView5);

        Button button = (Button) findViewById(R.id.dutch);
        Button button1 = (Button) findViewById(R.id.selection);
        Button button2 = (Button) findViewById(R.id.ladder);
        createDatabase();
        //createTable();
        //insertData();


        button.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                intent = new Intent (getApplicationContext(), DutchPay.class);
                startActivity(intent);
            }
        });
        button1.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view) {
                intent = new Intent(getApplicationContext(), Selection.class);
                startActivityForResult(intent, 1);


            }
        });
        button2.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View view) {
                intent = new Intent(getApplicationContext(), Ladder.class);
                startActivity(intent);

            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1) {
            Bundle bundle = data.getExtras();
            int group_id = bundle.getInt("group_id");
            queryData(group_id);
        }

    }
    private void createDatabase(){
        String name = "ex1.db";
        database = openOrCreateDatabase(name, MODE_WORLD_WRITEABLE, null); //DB가 존재하면 오픈. 존재하지않은면 생성
    }

    //테이블 생성
    /*private void createTable() {

        String sql = "create table " + tableName + "(per_id integer, group_id integer, fName text, price integer, rName text, address text, number text, etc text)";

        try {
            database.execSQL(sql);//slq문 실행
            textView2.setText("테이블 생성"+"\n");
        } catch (Exception e) {
            textView2.setText(e.getMessage()+"\n");
            e.printStackTrace();
        }

    }*/

    //데이터넣기
    /*private void insertData(){
        database.beginTransaction(); //sql문을 실행하는 일정구간을 트랜잭션으로 묶어주겠다라는 의미
        //트랜잭션 시작을 나타내는 메소드
        try{
            String sql;
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(1,303,'즉석떡볶이(소)',3000,'경원분식','경기도 성남시 수정구 성남대로 1334 경원프라자 지하 4F','없음','중-4,000,대-6,000')";
            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(3,304,'즉석떡볶이(대)',5000,'경원분식','경기도 성남시 수정구 성남대로 1334 경원프라자 지하 4F','없음','중-4,000,대-6,000')";
            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(2,303,'ㄷㅏ예',5000,'다예씌','다예자취방','010-6276-0432','꿀꿀')";
            database.execSQL(sql);

            database.setTransactionSuccessful(); //트랜잭션으로 묶어준 일정영역의 SQL문들이 모두 성공적으로 끝났을 지정

        }catch(Exception e){
            //SQL문 실행중 오류가 발생하면 예외처리가 되고..
            //트랜잭션에 정의된 SQL쿼리가 성공되지 않았기때문에 finally블록에서
            //트랜잭션 종료메서드 실행시(endTransaction()) 롤백이 된다.
            e.printStackTrace();
        }finally{
            database.endTransaction(); //트랜잭션을 끝내는 메소드.
        }

    }*/
    private void queryData(int group_id){
        String sql = "select per_id, group_id, fName, price, rName, address, number, etc from "+ tableName + " where group_id = " + group_id;
        Cursor cursor = database.rawQuery(sql, null);


        if(cursor != null){
            int count = cursor.getCount(); //조회된 개수얻기
            for(int i = 0; i< count ; i++){
                cursor.moveToNext();
                foodlist=cursor.getString(0) + "\n" +cursor.getString(1) +"\n"+ cursor.getString(2)+"\n"+ cursor.getString(3)+"\n"+ cursor.getString(4)+"\n"+ cursor.getString(5)+"\n"+ cursor.getString(6)+"\n"+ cursor.getString(7);
                textView1.append("#"+i+1+":"+foodlist+"\n\n\n\n");

            }
        }

    }


}