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
    Intent intent;
    String foodlist ="";
    String info="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            goFood(foodlist, info);
            foodlist="";
            info="";
        }

    }
    public void goFood(String food, String information)
    {
        intent = new Intent (getApplicationContext(), Foodlist.class);
        intent.putExtra("foodlist",food);
        intent.putExtra("info",information);
        startActivity(intent);
    }
    private void createDatabase(){
        String name = "ex5.db";
        database = openOrCreateDatabase(name,MODE_PRIVATE, null); //DB가 존재하면 오픈. 존재하지않은면 생성
    }

    //테이블 생성
    private void createTable() {
        String sql = "create table " + tableName + "(per_id integer, group_id integer, fName text, price integer, rName text, address text, number text, etc text)";
        try {
            database.execSQL(sql);//slq문 실행
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //데이터넣기
    private void insertData(){
        database.beginTransaction(); //sql문을 실행하는 일정구간을 트랜잭션으로 묶어주겠다라는 의미
        //트랜잭션 시작을 나타내는 메소드
        try{
            String sql;
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(1,303,'즉석떡볶이(소)',3000,'경원분식','경기도 성남시 수정구 성남대로 1334 경원프라자 지하 4F','-','중-4,000,대-6,000')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(2,303,'순대볶음',3000,'경원분식','경기도 성남시 수정구 성남대로 1334 경원프라자 지하 4F','-',' -')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(3,302,'양푼비빔밥',5000,'경원분식','경기도 성남시 수정구 성남대로 1334 경원프라자 지하 4F','-',' -')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(4,303,'떡라면',3000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 2F','031-725-4632',' -')";            database.execSQL(sql);	            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(5,303,'만두라면',3000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 2F','031-725-4632',' -')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(6,303,'치즈라면',3000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 3F','031-725-4632',' -')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(7,303,'계란라면',3000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 4F','031-725-4632',' -')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(8,303,'콩나물해장라면',3000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 5F','031-725-4632',' -')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(9,303,'나가사키짬뽕',2500,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 6F','031-725-4632',' -')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(10,304,'꼬꼬면',2500,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 7F','031-725-4632',' -')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(11,301,'김치찌개(1인분)',5000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 8F','031-725-4632',' -')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(12,301,'부대지개(1인분)',5000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 9F','031-725-4632',' -')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(13,308,'불고기전골',5000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 10F','031-725-4632',' -')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(14,303,'즉석떡볶이',5000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 11F','031-725-4632',' -')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(15,304,'즉석짜라뽀끼',5000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 12F','031-725-4632','2인분이상')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(16,307,'불제육덮밥',5000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 13F','031-725-4632','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(17,301,'불오징어덮밥',5000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 14F','031-725-4632','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(18,301,'불낙지덮밥',5000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 15F','031-725-4632','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(19,301,'불쭈꾸미덮밥',5000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 16F','031-725-4632','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(20,302,'우렁된장찌개',5000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 17F','031-725-4632','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(21,302,'차돌된장찌개',5000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 18F','031-725-4632','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(22,301,'참치,김치볶음밥',4500,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(23,301,'오징어덮밥',5000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(24,301,'제육덮밥',5500,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(25,301,'김치찌개(1인분)',5000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(26,302,'해물된장찌개',5000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(27,308,'우거지갈비탕',5500,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(28,307,'육개장',5000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(29,308,'뚝배기불고기',5000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(30,308,'돈까스',5000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(31,302,'오므라이스',5000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(32,302,'카레덮밥',4500,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(33,302,'짜장덮밥',4500,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(34,301,'부대찌개',5000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(35,307,'내장탕',5000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(36,308,'소갈비탕',5500,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(37,301,'순두부찌개',5000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(38,307,'순대국밥',5500,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(39,302,'돌솥비빔밥',5000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(40,303,'비빔냉면',4500,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(41,303,'라볶이',5000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(42,304,'콩국수',5500,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(43,308,'삼계탕',9000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(44,304,'물냉면',4500,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(45,301,'참치찌개',5000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(46,304,'찐만두',3000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);
            sql = "insert into "+ tableName + "(per_id, group_id, fName, price, rName, address, number, etc) values(47,303,'쫄면',4500,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-')";            database.execSQL(sql);

            database.setTransactionSuccessful(); //트랜잭션으로 묶어준 일정영역의 SQL문들이 모두 성공적으로 끝났을 지정
        }catch(Exception e){
            //SQL문 실행중 오류가 발생하면 예외처리가 되고..
            //트랜잭션에 정의된 SQL쿼리가 성공되지 않았기때문에 finally블록에서
            //트랜잭션 종료메서드 실행시(endTransaction()) 롤백이 된다.
            e.printStackTrace();
        }finally{
            database.endTransaction(); //트랜잭션을 끝내는 메소드.
        }
    }
    private void queryData(int group_id){
        String sql = "select per_id, group_id, fName, price, rName, address, number, etc from "+ tableName + " where group_id = " + group_id;
        Cursor cursor = database.rawQuery(sql, null);


        if(cursor != null){
            int count = cursor.getCount(); //조회된 개수얻기
            for(int i = 0; i< count ; i++){
                cursor.moveToNext();
                foodlist+=cursor.getString(2) +" ";
                info += cursor.getString(3)+"\n"+ cursor.getString(4)+"\n"+ cursor.getString(5)+"\n"+ cursor.getString(6)+"\n"+ cursor.getString(7)+".";

            }
        }

    }


}