package org.androidtown.termproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity {
    SQLiteDatabase database;
    String randFood = "";
    String tableName = "FOOD5";
    Intent intent;
    String randInfo="";
    String foodlist ="";
    String address="";
    String info="";
    String foodN="";
    int count=-1;
    int g, t;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton button = (ImageButton) findViewById(R.id.dutch);
        ImageButton button1 = (ImageButton) findViewById(R.id.select);
        ImageButton button2 = (ImageButton) findViewById(R.id.random);
        createDatabase();
        createTable();
        insertData();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), DutchPay.class);
                startActivity(intent);
            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address = "";
                randFood = "";
                randInfo = "";
                foodN = "";
                intent = new Intent(getApplicationContext(), Selection.class);
                startActivityForResult(intent, 1);
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address = "";
                randFood = "";
                randInfo = "";
                foodN = "";
                intent = new Intent(getApplicationContext(), Ladder.class);
                Random random = new Random();
                int[] ran = {-1, -1, -1, -1, -1};
                int temp;
                for (int i = 0; i < 5; i++) {
                    temp = random.nextInt(417);//data갯수만큼 써주기!!!!!!!!!
                    while (temp == ran[0] && temp == ran[1] && temp == ran[2] && temp == ran[3] && temp == ran[4]) {
                        temp = random.nextInt(417);//data갯수만큼 써주기!!!!!!!!!
                    }
                    ran[i] = temp;
                    queryData2(ran[i]);
                }
                intent.putExtra("randFood", randFood);
                intent.putExtra("randInfo", randInfo);
                intent.putExtra("randAddress", address);
                intent.putExtra("food", foodN);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        foodlist="";
        info="";
        address="";
        foodN="";
        if(resultCode==1) {
            Bundle bundle = data.getExtras();
            int group_id = bundle.getInt("group_id");
            g= group_id;
            int type_id = bundle.getInt("type_id");
            t = type_id;
            queryData(group_id,type_id);
            if(count==0) {
                noMenu(group_id);//,type_id
            }
            else {
                goFood(foodlist, info);
            }
        }
        else if(resultCode==111){
            int group_id=data.getIntExtra("group_id",0);
            intent = new Intent(getApplicationContext(), Selection.class);
            intent.putExtra("group_id",group_id);
            startActivityForResult(intent, 1);
        }
        else if(resultCode==-1){
            return;
        }
    }

    public void noMenu(int group_id){
        Intent noMenuIntent;
        noMenuIntent = new Intent (getApplicationContext(), noFood.class);
        noMenuIntent.putExtra("group_id",group_id);
        startActivityForResult(noMenuIntent,111);
    }
    public void goFood(String food, String information)
    {
        intent = new Intent (getApplicationContext(), Foodlist.class);
        intent.putExtra("foodlist",food);
        intent.putExtra("info",information);
        intent.putExtra("address",address);
        intent.putExtra("food",foodN);
        startActivity(intent);
    }
    private void createDatabase() {
        String name = "dahye124.db";
        database = openOrCreateDatabase(name, MODE_PRIVATE, null); //DB가 존재하면 오픈. 존재하지않은면 생성
    }

    //테이블 생성
    private void createTable() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(per_id integer unique, group_id integer, type_id integer, fName text, price integer, rName text, address text, number text, etc text, address2 text)";
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
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(1,303,405,'즉석떡볶이(소)',3000,'경원분식','경기도 성남시 수정구 성남대로 1334 경원프라자 지하 4F','-','중-4,000,대-6,000','경기도 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(2,303,405,'순대볶음',3000,'경원분식','경기도 성남시 수정구 성남대로 1334 경원프라자 지하 4F','-',' -','경기도 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(3,302,405,'양푼비빔밥',5000,'경원분식','경기도 성남시 수정구 성남대로 1334 경원프라자 지하 4F','-',' -','경기도 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(4,303,405,'떡라면',3000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 2F','031-725-4632',' -','경기도 성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(5,303,405,'만두라면 ',3000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 2F','031-725-4632',' -','경기도 성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(6,303,405,'치즈라면',3000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 3F','031-725-4632',' -','경기도 성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(7,303,405,'계란라면',3000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 4F','031-725-4632',' -','경기도 성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(8,303,405,'콩나물해장라면',3000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 5F','031-725-4632',' -','경기도 성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(9,304,405,'꼬꼬면',2500,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 7F','031-725-4632',' -','경기도 성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(10,301,401,'김치찌개(1인분)',5000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 8F','031-725-4632',' -','경기도 성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(11,301,401,'부대지개(1인분)',5000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 9F','031-725-4632',' -','경기도 성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(12,308,401,'불고기전골',5000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 10F','031-725-4632',' -','경기도 성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(13,303,405,'즉석떡볶이',5000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 11F','031-725-4632',' -','경기도 성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(14,304,405,'즉석짜라뽀끼',5000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 12F','031-725-4632','2인분이상','경기도 성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(15,302,401,'우렁된장찌개',5000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 17F','031-725-4632','-','경기도 성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(16,302,401,'차돌된장찌개',5000,'쩡이네분식','경기도 성남시 수정구 복정동 가천대학교 비전타워 지하 18F','031-725-4632','-','경기도 성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(17,301,401,'참치,김치볶음밥',4500,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-','경기도 성남시 수정구 복정동 709-13')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(18,308,401,'우거지갈비탕',5500,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-','경기도 성남시 수정구 복정동 709-13')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(19,307,401,'육개장',5000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-','경기도 성남시 수정구 복정동 709-13')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(20,308,401,'뚝배기불고기',5000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-','경기도 성남시 수정구 복정동 709-13')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(21,308,404,'돈까스',5000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-','경기도 성남시 수정구 복정동 709-13')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(22,302,404,'오므라이스',5000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-','경기도 성남시 수정구 복정동 709-13')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(23,302,404,'카레덮밥',4500,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-','경기도 성남시 수정구 복정동 709-13')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(24,307,401,'내장탕',5000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-','경기도 성남시 수정구 복정동 709-13')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(25,308,401,'소갈비탕',5500,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-','경기도 성남시 수정구 복정동 709-13')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(26,301,401,'순두부찌개',5000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-','경기도 성남시 수정구 복정동 709-13')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(27,307,401,'순대국밥',5500,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-','경기도 성남시 수정구 복정동 709-13')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(28,302,401,'돌솥비빔밥',5000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-','경기도 성남시 수정구 복정동 709-13')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(29,304,401,'콩국수',5500,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-','경기도 성남시 수정구 복정동 709-13')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(30,308,401,'삼계탕',9000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-','경기도 성남시 수정구 복정동 709-13')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(31,304,401,'물냉면',4500,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-','경기도 성남시 수정구 복정동 709-13')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(32,301,401,'참치찌개',5000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-','경기도 성남시 수정구 복정동 709-13')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(33,304,405,'찐만두',3000,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-','경기도 성남시 수정구 복정동 709-13')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(34,303,405,'쫄면',4500,'엉터리분식','경기도 성남시 수정구 복정동 709-13 엉터리분식','031-659-5388','-','경기도 성남시 수정구 복정동 709-13')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(35,302,405,'봉구스밥버거',1800,'봉구스밥버거','성남시 수정구 복정동 가천대학교 비전타워','031-721-4379','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(36,302,405,'치즈밥버거',2300,'봉구스밥버거','성남시 수정구 복정동 가천대학교 비전타워','031-721-4379','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(37,301,405,'제육밥버거',2300,'봉구스밥버거','성남시 수정구 복정동 가천대학교 비전타워','031-721-4379','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(38,301,405,'김치제육밥버거',2600,'봉구스밥버거','성남시 수정구 복정동 가천대학교 비전타워','031-721-4379','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(39,301,405,'치즈닭갈비밥버거',3000,'봉구스밥버거','성남시 수정구 복정동 가천대학교 비전타워','031-721-4379','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(40,302,405,'햄밥버거',2300,'봉구스밥버거','성남시 수정구 복정동 가천대학교 비전타워','031-721-4379','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(41,302,405,'햄치즈밥버거',2800,'봉구스밥버거','성남시 수정구 복정동 가천대학교 비전타워','031-721-4379','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(42,302,405,'치즈제육밥버거',2800,'봉구스밥버거','성남시 수정구 복정동 가천대학교 비전타워','031-721-4379','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(43,301,405,'닭갈비밥버거',2500,'봉구스밥버거','성남시 수정구 복정동 가천대학교 비전타워','031-721-4379','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(44,302,405,'소불고기밥버거',3000,'봉구스밥버거','성남시 수정구 복정동 가천대학교 비전타워','031-721-4379','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(45,304,403,'우동',3900,'오니기리와이규동','성남시 수정구 복정동 가천대학교 비전타워','031-721-4379','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(46,304,403,'야채튀김우동',4900,'오니기리와이규동','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(47,303,403,'나가사키짬뽕',5900,'오니기리와이규동','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(48,304,403,'꼬치어묵우동',4900,'오니기리와이규동','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(49,303,403,'김치어묵우동',5400,'오니기리와이규동','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(50,304,403,'해물야끼우동',5900,'오니기리와이규동','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(51,303,403,'돈까스김치나베우동',7500,'오니기리와이규동','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(52,304,403,'냉우동',4900,'오니기리와이규동','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','계절메뉴','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(53,304,403,'냉모밀',4900,'오니기리와이규동','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','계절메뉴','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(54,304,403,'냉냄비우동',5400,'오니기리와이규동','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','계절메뉴','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(55,303,403,'냉비빔모밀',5400,'오니기리와이규동','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','계절메뉴','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(56,308,403,'규동',5900,'오니기리와이규동','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(57,308,403,'보삼애규동',6500,'오니기리와이규동','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(58,308,403,'트리플치즈규동',7400,'오니기리와이규동','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(59,308,403,'규카레동',6900,'오니기리와이규동','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(60,308,403,'스팸마요동',5900,'오니기리와이규동','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(61,308,403,'장어덮밥',8800,'오니기리와이규동','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(62,307,403,'화끈규동',6900,'오니기리와이규동','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(63,306,403,'해물돈부리',6500,'오니기리와이규동','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(64,308,403,'치킨마요동',5900,'오니기리와이규동','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(65,308,403,'가츠동',6000,'오니기리와이규동','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(66,308,403,'치킨데리야끼동',6500,'오니기리와이규동','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(67,308,403,'차슈데리야끼동',6500,'오니기리와이규동','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(68,308,403,'카레돈까스',6500,'오니기리와이규동','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(69,308,401,'치킨마요',2700,'토마토도시락','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(70,308,401,'참치마요',2700,'토마토도시락','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(71,308,401,'통새우치킨마요',3600,'토마토도시락','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(72,308,401,'통새우참치마요',3600,'토마토도시락','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(73,308,401,'돈까스로제파스타',3900,'토마토도시락','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(74,308,401,'햄버그로제파스타',3900,'토마토도시락','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(75,307,401,'불고기커리',4500,'토마토도시락','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(76,308,401,'돈까스커리',4500,'토마토도시락','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(77,307,401,'치킨커리',4500,'토마토도시락','성남시 수정구 복정동 가천대학교 비전타워','031-758-5290','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(78,303,401,'버섯 매운탕 칼국수',8000,'등촌샤브칼국수','성남시 수정구 복정로 33','031-752-3414','-','성남시 수정구 복정로 33')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(79,304,401,'바지락 칼국수',8000,'등촌샤브칼국수','성남시 수정구 복정로 33','031-752-3414','-','성남시 수정구 복정로 33')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(80,304,401,'서리테 콩국수',8000,'등촌샤브칼국수','성남시 수정구 복정로 33','031-752-3414','-','성남시 수정구 복정로 33')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(81,308,401,'소고기샤브샤브',8000,'등촌샤브칼국수','성남시 수정구 복정로 33','031-752-3414','-','성남시 수정구 복정로 33')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(82,306,401,'해물샤브샤브',8000,'등촌샤브칼국수','성남시 수정구 복정로 33','031-752-3414','-','성남시 수정구 복정로 33')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(83,307,404,'매콤 돈가스',7000,'포크포크','성남시 수정구 복정로 30','031-751-3223','매운 돈가스-7000, 무진장매운 돈가스-7000','성남시 수정구 복정로 30')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(84,304,401,'돈냉면',7000,'포크포크','성남시 수정구 복정로 30','031-751-3223','불물냉면-5500, 곱배기-7000','성남시 수정구 복정로 30')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(85,304,401,'칡물냉면',5500,'포크포크','성남시 수정구 복정로 30','031-751-3223','불비빔냉면-5500, 곱빼기-7000','성남시 수정구 복정로 30')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(86,303,401,'비빔냉면',5500,'포크포크','성남시 수정구 복정로 30','031-751-3223','-','성남시 수정구 복정로 30')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(87,308,401,'생등심',84000,'동막골 숯불 바비큐','성남시 수정구 복정로 21','031-722-7728','-','성남시 수정구 복정로 21')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(88,308,401,'갈비살',15000,'동막골 숯불 바비큐','성남시 수정구 복정로 21','031-722-7728','양념돼지갈비-14000(국내산),12000(수입)','성남시 수정구 복정로 21')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(89,308,401,'생오겹',12000,'동막골 숯불 바비큐','성남시 수정구 복정로 21','031-722-7728','-','성남시 수정구 복정로 21')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(90,308,401,'막창',11000,'동막골 숯불 바비큐','성남시 수정구 복정로 21','031-722-7728','-','성남시 수정구 복정로 21')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(91,302,401,'백반정식',6000,'동막골 숯불 바비큐','성남시 수정구 복정로 21','031-722-7728','-','성남시 수정구 복정로 21')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(92,308,401,'버섯분고기전골',10000,'동막골 숯불 바비큐','성남시 수정구 복정로 21','031-722-7728','-','성남시 수정구 복정로 21')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(93,302,401,'오삼쌈밥',8000,'동막골 숯불 바비큐','성남시 수정구 복정로 21','031-722-7728','-','성남시 수정구 복정로 21')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(94,308,401,'왕갈비탕',8000,'동막골 숯불 바비큐','성남시 수정구 복정로 21','031-722-7728','-','성남시 수정구 복정로 21')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(95,301,401,'부대전골',7000,'동막골 숯불 바비큐','성남시 수정구 복정로 21','031-722-7728','-','성남시 수정구 복정로 21')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(96,302,401,'다슬기해장국',6000,'동막골 숯불 바비큐','성남시 수정구 복정로 21','031-722-7728','-','성남시 수정구 복정로 21')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(97,307,401,'닭도리탕',25000,'전주식당','성남시 수정구 복정로 9','031-753-5484','-','성남시 수정구 복정로 9')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(98,306,401,'동태탕',6000,'전주식당','성남시 수정구 복정로 9','031-753-5484','-','성남시 수정구 복정로 9')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(99,302,401,'된장찌개',5000,'전주식당','성남시 수정구 복정로 9','031-753-5484','-','성남시 수정구 복정로 9')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(100,301,401,'전주비빔밥',5000,'전주식당','성남시 수정구 복정로 9','031-753-5484','-','성남시 수정구 복정로 9')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(101,301,401,'콩나물해장국',5000,'전주식당','성남시 수정구 복정로 9','031-753-5484','-','성남시 수정구 복정로 9')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(102,308,401,'소내장탕',6000,'전주식당','성남시 수정구 복정로 9','031-753-5484','-','성남시 수정구 복정로 9')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(103,302,401,'청국장',6000,'전주식당','성남시 수정구 복정로 9','031-753-5484','-','성남시 수정구 복정로 9')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(104,307,401,'뼈감자탕',20000,'묵은지감자탕','성남시 수정구 복정로 27','031-722-1150','소-20000, 중- 25000, 대-30000','성남시 수정구 복정로 27')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(105,305,401,'뼈해물찜',25000,'묵은지감자탕','성남시 수정구 복정로 27','031-722-1150','중-25000, 대-35000','성남시 수정구 복정로 27')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(106,302,401,'황태해장국',6000,'묵은지감자탕','성남시 수정구 복정로 27','031-722-1150','특-7000','성남시 수정구 복정로 27')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(107,302,401,'도토리묵밥',5000,'묵은지감자탕','성남시 수정구 복정로 27','031-722-1150','-','성남시 수정구 복정로 27')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(108,304,401,'묵,칡냉면',5000,'묵은지감자탕','성남시 수정구 복정로 27','031-722-1150','-','성남시 수정구 복정로 27')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(109,302,403,'알밥',6000,'좋은물고기','성남시 수정구 복정로 50','031-752-9006','-','성남시 수정구 복정로 50')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(110,301,403,'실속알탕',6000,'좋은물고기','성남시 수정구 복정로 50','031-752-9006','-','성남시 수정구 복정로 50')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(111,306,403,'연어회덮밥',8000,'좋은물고기','성남시 수정구 복정로 50','031-752-9006','-','성남시 수정구 복정로 50')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(112,306,403,'고등어',8000,'좋은물고기','성남시 수정구 복정로 50','031-752-9006','-','성남시 수정구 복정로 50')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(113,306,403,'고갈비',9000,'좋은물고기','성남시 수정구 복정로 50','031-752-9006','-','성남시 수정구 복정로 50')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(114,306,403,'삼치',8000,'좋은물고기','성남시 수정구 복정로 50','031-752-9006','-','성남시 수정구 복정로 50')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(115,306,403,'갈치',1000,'좋은물고기','성남시 수정구 복정로 50','031-752-9006','-','성남시 수정구 복정로 50')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(116,306,403,'연어머리',15000,'좋은물고기','성남시 수정구 복정로 50','031-752-9006','-','성남시 수정구 복정로 50')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(117,306,403,'연어',13000,'좋은물고기','성남시 수정구 복정로 50','031-752-9006','-','성남시 수정구 복정로 50')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(118,306,403,'연어회',22000,'좋은물고기','성남시 수정구 복정로 50','031-752-9006','-','성남시 수정구 복정로 50')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(119,306,403,'명란구이',15000,'좋은물고기','성남시 수정구 복정로 50','031-752-9006','-','성남시 수정구 복정로 50')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(120,302,401,'순한알밥',3500,'알촌','성남시 수정구 복정로 20','070-7768-0598','-','성남시 수정구 복정로 20')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(121,301,401,'약매알밥',3900,'알촌','성남시 수정구 복정로 20','070-7768-0598','-','성남시 수정구 복정로 20')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(122,301,401,'매콤알밥',3900,'알촌','성남시 수정구 복정로 20','070-7768-0598','-','성남시 수정구 복정로 20')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(123,301,401,'진매알밥',4000,'알촌','성남시 수정구 복정로 20','070-7768-0598','-','성남시 수정구 복정로 20')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(124,301,401,'매콤치즈알밥',4500,'알촌','성남시 수정구 복정로 20','070-7768-0598','-','성남시 수정구 복정로 20')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(125,302,401,'치즈오밥',4800,'알촌','성남시 수정구 복정로 20','070-7768-0598','-','성남시 수정구 복정로 20')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(126,302,401,'카레알밥',4300,'알촌','성남시 수정구 복정로 20','070-7768-0598','-','성남시 수정구 복정로 20')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(127,302,401,'짜장알밥',4300,'알촌','성남시 수정구 복정로 20','070-7768-0598','-','성남시 수정구 복정로 20')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(128,301,401,'오밥',4300,'알촌','성남시 수정구 복정로 20','070-7768-0598','-','성남시 수정구 복정로 20')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(129,302,401,'갈릭알밥',4300,'알촌','성남시 수정구 복정로 20','070-7768-0598','-','성남시 수정구 복정로 20')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(130,302,401,'갈릭치즈알밥',4800,'알촌','성남시 수정구 복정로 20','070-7768-0598','-','성남시 수정구 복정로 20')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(131,301,401,'갈릭오밥',4800,'알촌','성남시 수정구 복정로 20','070-7768-0598','-','성남시 수정구 복정로 20')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(132,303,405,'떡볶이',2500,'신전떡볶이','성남시 수정구 복정로 42','050-7458-9390','-','성남시 수정구 복정로 42')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(133,303,405,'치즈떡볶이',3500,'신전떡볶이','성남시 수정구 복정로 42','050-7458-9390','-','성남시 수정구 복정로 42')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(134,301,405,'신전김밥',1500,'신전떡볶이','성남시 수정구 복정로 42','050-7458-9390','-','성남시 수정구 복정로 42')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(135,308,406,'후라이드',15000,'동키치킨','경기 성남시 수정구 복정로 28','031-753-2727','-','경기 성남시 수정구 복정로 28')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(136,307,406,'양념치킨',16000,'동키치킨','경기 성남시 수정구 복정로 28','031-753-2727','-','경기 성남시 수정구 복정로 28')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(137,305,401,'아구찜',33000,'복정아구찜해물탕','성남시 수정구 복정로 25-1','031-722-1929','2인-33000, 3인-46000, 4인-58000','성남시 수정구 복정로 25-1')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(138,305,401,'낙지찜',33000,'복정아구찜해물탕','성남시 수정구 복정로 25-1','031-722-1929','2인-33000, 3인-46000, 4인-58000','성남시 수정구 복정로 25-1')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(139,305,401,'아낙찜',46000,'복정아구찜해물탕','성남시 수정구 복정로 25-1','031-722-1929','2인, 3인-46000, 4인-59000','성남시 수정구 복정로 25-1')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(140,305,401,'해물찜',48000,'복정아구찜해물탕','성남시 수정구 복정로 25-1','031-722-1929','2인, 3인-48000, 4인-63000','성남시 수정구 복정로 25-1')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(141,306,401,'아구탕',33000,'복정아구찜해물탕','성남시 수정구 복정로 25-1','031-722-1929','2인-33000, 3인-46000, 4인-58000','성남시 수정구 복정로 25-1')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(142,306,401,'해물탕',48000,'복정아구찜해물탕','성남시 수정구 복정로 25-1','031-722-1929','2인, 3인-48000, 4인-63000','성남시 수정구 복정로 25-1')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(143,302,401,'아구해장국',9000,'복정아구찜해물탕','성남시 수정구 복정로 25-1','031-722-1929','-','성남시 수정구 복정로 25-1')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(144,305,401,'낙지한마리덮밥',9000,'복정아구찜해물탕','성남시 수정구 복정로 25-1','031-722-1929','-','성남시 수정구 복정로 25-1')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(145,305,401,'불고기낙지뚝배기',9000,'복정아구찜해물탕','성남시 수정구 복정로 25-1','031-722-1929','-','성남시 수정구 복정로 25-1')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(146,305,401,'쭈구미덮밥',9000,'복정아구찜해물탕','성남시 수정구 복정로 25-1','031-722-1929','-','성남시 수정구 복정로 25-1')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(147,305,401,'알탕',9000,'복정아구찜해물탕','성남시 수정구 복정로 25-1','031-722-1929','-','성남시 수정구 복정로 25-1')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(148,304,403,'모밀정식',9000,'한마루참치','성남시 수정구 복정동 711-3번지 1층','031-756-8837','-','성남시 수정구 복정동 711-3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(149,304,403,'우동정식',9000,'한마루참치','성남시 수정구 복정동 711-3번지 1층','031-756-8837','-','성남시 수정구 복정동 711-3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(150,305,403,'대구탕',6000,'한마루참치','성남시 수정구 복정동 711-3번지 1층','031-756-8837','-','성남시 수정구 복정동 711-3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(151,306,403,'특초밥',15000,'한마루참치','성남시 수정구 복정동 711-3번지 1층','031-756-8837','-','성남시 수정구 복정동 711-3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(152,306,403,'모듬초밥',15000,'한마루참치','성남시 수정구 복정동 711-3번지 1층','031-756-8837','-','성남시 수정구 복정동 711-3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(153,306,403,'참치초밥',10000,'한마루참치','성남시 수정구 복정동 711-3번지 1층','031-756-8837','-','성남시 수정구 복정동 711-3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(154,306,403,'한마루참치',50000,'한마루참치','성남시 수정구 복정동 711-3번지 1층','031-756-8837','진-50000, 선-35000, 미-25000','성남시 수정구 복정동 711-3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(155,308,401,'생삼겹',12000,'친친','경기 성남시 수정구 복정로 40','031-723-9276','-','경기 성남시 수정구 복정로 40')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(156,308,401,'돼지갈비',11000,'친친','경기 성남시 수정구 복정로 40','031-723-9276','-','경기 성남시 수정구 복정로 40')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(157,308,401,'통삼겹',7500,'친친','경기 성남시 수정구 복정로 40','031-723-9276','-','경기 성남시 수정구 복정로 40')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(158,307,401,'제육 직화',5000,'맘보 직화&죽이야기','성남시 수정구 복정동 가천대학교 비전타워 지하 2층','031-753-6242','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(159,305,401,'낙지 직화',5000,'맘보 직화&죽이야기','성남시 수정구 복정동 가천대학교 비전타워 지하 2층','031-753-6243','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(160,305,401,'낙삼 직화',5000,'맘보 직화&죽이야기','성남시 수정구 복정동 가천대학교 비전타워 지하 2층','031-753-6244','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(161,308,401,'철판 갈비살',6500,'맘보 직화&죽이야기','성남시 수정구 복정동 가천대학교 비전타워 지하 2층','031-753-6245','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(162,308,401,'치킨 도리아',5000,'맘보 직화&죽이야기','성남시 수정구 복정동 가천대학교 비전타워 지하 2층','031-753-6249','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(163,301,401,'김치 치즈 덮밥',5000,'맘보 직화&죽이야기','성남시 수정구 복정동 가천대학교 비전타워 지하 2층','031-753-6250','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(164,302,401,'콩나물 비빔밥',4500,'맘보 직화&죽이야기','성남시 수정구 복정동 가천대학교 비전타워 지하 2층','031-753-6251','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(165,307,401,'육개장',5000,'맘보 직화&죽이야기','성남시 수정구 복정동 가천대학교 비전타워 지하 2층','031-753-6255','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(166,302,401,'호박죽',4000,'맘보 직화&죽이야기','성남시 수정구 복정동 가천대학교 비전타워 지하 2층','031-753-6256','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(167,302,401,'야채죽',6000,'맘보 직화&죽이야기','성남시 수정구 복정동 가천대학교 비전타워 지하 2층','031-753-6257','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(168,306,401,'새우죽',7000,'맘보 직화&죽이야기','성남시 수정구 복정동 가천대학교 비전타워 지하 2층','031-753-6258','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(169,306,401,'참치죽',7000,'맘보 직화&죽이야기','성남시 수정구 복정동 가천대학교 비전타워 지하 2층','031-753-6259','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(170,308,401,'한우 야채죽',8000,'맘보 직화&죽이야기','성남시 수정구 복정동 가천대학교 비전타워 지하 2층','031-753-6260','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(171,306,401,'전복죽',10000,'맘보 직화&죽이야기','성남시 수정구 복정동 가천대학교 비전타워 지하 2층','031-753-6261','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(172,308,402,'갈비 찐만두',5000,'차이나스푼','성남시 수정구 복정동 가천대학교 비전타워 지하 3층','031-752-6789','','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(173,306,402,'새우볶음밥',5000,'차이나스푼','성남시 수정구 복정동 가천대학교 비전타워 지하 3층','031-752-6789','','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(174,305,402,'해물특밥',6000,'차이나스푼','성남시 수정구 복정동 가천대학교 비전타워 지하 3층','031-752-6789','','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(175,302,402,'잡채밥',6000,'차이나스푼','성남시 수정구 복정동 가천대학교 비전타워 지하 3층','031-752-6789','','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(176,301,402,'짬뽕밥',6000,'차이나스푼','성남시 수정구 복정동 가천대학교 비전타워 지하 3층','031-752-6789','','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(177,301,402,'마파 두부밥',7000,'차이나스푼','성남시 수정구 복정동 가천대학교 비전타워 지하 3층','031-752-6789','','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(178,306,402,'게살 볶음밥',8000,'차이나스푼','성남시 수정구 복정동 가천대학교 비전타워 지하 3층','031-752-6789','','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(179,306,402,'굴탕밥',8000,'차이나스푼','성남시 수정구 복정동 가천대학교 비전타워 지하 3층','031-752-6789','','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(180,301,402,'고추 잡채밥',8000,'차이나스푼','성남시 수정구 복정동 가천대학교 비전타워 지하 3층','031-752-6789','','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(181,306,402,'류산슬밥',10000,'차이나스푼','성남시 수정구 복정동 가천대학교 비전타워 지하 3층','031-752-6789','','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(182,306,402,'해물 잡탕밥',10000,'차이나스푼','성남시 수정구 복정동 가천대학교 비전타워 지하 3층','031-752-6789','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(183,304,406,'치킨 쌀국수',5500,'포밥 인 뉴욕','성남시 수정구 복정동 가천대학교 비전타워 지하 2층 209호','031-754-8838','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(184,301,406,'나시고랭볶음밥',5500,'포밥 인 뉴욕','성남시 수정구 복정동 가천대학교 비전타워 지하 2층 209호','031-754-8838','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(185,301,406,'칠리볶음밥',5500,'포밥 인 뉴욕','성남시 수정구 복정동 가천대학교 비전타워 지하 2층 209호','031-754-8838','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(186,304,406,'칠리볶음면',6500,'포밥 인 뉴욕','성남시 수정구 복정동 가천대학교 비전타워 지하 2층 209호','031-754-8838','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(187,308,401,'포밥국밥(고기국밥)',6000,'포밥 인 뉴욕','성남시 수정구 복정동 가천대학교 비전타워 지하 2층 209호','031-754-8838','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(188,308,401,'포밥곰탕',6000,'포밥 인 뉴욕','성남시 수정구 복정동 가천대학교 비전타워 지하 2층 209호','031-754-8838','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(189,308,404,'치킨브리또',3000,'ChimiChimi','성남시 수정구 복정동 가천대학교 비전타워 지하 1층','031-751-7271','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(190,308,404,'소고기브리또',3500,'ChimiChimi','성남시 수정구 복정동 가천대학교 비전타워 지하 1층','031-751-7271','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(191,308,404,'믹스브리또',3000,'ChimiChimi','성남시 수정구 복정동 가천대학교 비전타워 지하 1층','031-751-7271','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(192,301,401,'철판 라이스',3500,'신의 한컵','성남시 수정구 복정동 가천대학교 비전타워 지하 2층','031-757-2204','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(193,303,401,'철판 누들',3500,'신의 한컵','성남시 수정구 복정동 가천대학교 비전타워 지하 2층','031-757-2204','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(194,308,401,'소시지 마요',3500,'신의 한컵','성남시 수정구 복정동 가천대학교 비전타워 지하 2층','031-757-2204','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(195,308,401,'불고기 마요',3500,'신의 한컵','성남시 수정구 복정동 가천대학교 비전타워 지하 2층','031-757-2204','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(196,307,401,'김치고기 마요',3500,'신의 한컵','성남시 수정구 복정동 가천대학교 비전타워 지하 2층','031-757-2204','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(197,307,401,'불닭마요',3500,'신의 한컵','성남시 수정구 복정동 가천대학교 비전타워 지하 2층','031-757-2204','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(198,304,401,'세숫대야 물냉면',5000,'해마중 국수마을','성남시 수정구 복정동 가천대학교 비전타워 지하 3층','031-754-5987 ','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(199,303,401,'세숫대야 비빔냉면',5000,'해마중 국수마을','성남시 수정구 복정동 가천대학교 비전타워 지하 3층','031-754-5987 ','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(200,303,401,'세숫대야 뿔냉면',5000,'해마중 국수마을','성남시 수정구 복정동 가천대학교 비전타워 지하 3층','031-754-5987 ','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(201,304,401,'잔치국수',4500,'해마중 국수마을','성남시 수정구 복정동 가천대학교 비전타워 지하 3층','031-754-5987 ','-','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(202,308,406,'치킨버거/세트',4200,'파파이스','성남시 수정구 복정동 가천대학교 비전타워 지하 1층','031-721-8823','세트 6500','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(203,308,406,'닭달버거/세트',3800,'파파이스','성남시 수정구 복정동 가천대학교 비전타워 지하 1층','031-721-8823','세트 6000','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(204,308,406,'치즈베이컨킨버거/세트',4800,'파파이스','성남시 수정구 복정동 가천대학교 비전타워 지하 1층','031-721-8823','세트 7000','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(205,308,406,'케이준통버거/세트',5000,'파파이스','성남시 수정구 복정동 가천대학교 비전타워 지하 1층','031-721-8823','세트 5800','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(206,307,406,'통새우버거/세트',3500,'파파이스','성남시 수정구 복정동 가천대학교 비전타워 지하 1층','031-721-8823','세트 5400','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(207,308,406,'불고기버거/세트',3300,'파파이스','성남시 수정구 복정동 가천대학교 비전타워 지하 1층','031-721-8823','세트 6000','성남시 수정구 복정동 가천대학교 비전타워')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(208,307,404,'카레돈까스',7000,'코끼리왕돈까스','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(209,308,404,'치즈돈까스',7000,'코끼리왕돈까스','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(210,308,404,'김치돈까스',7000,'코끼리왕돈까스','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(211,308,404,'치킨까스마요',6000,'코끼리왕돈까스','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(212,306,404,'새우까스마요',6000,'코끼리왕돈까스','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(213,302,405,'미소김밥',2500,'미소분식','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(214,302,405,'참치김밥',3000,'미소분식','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(215,302,405,'치즈김밥',3000,'미소분식','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(216,302,405,'미소콤보(라면+김밥)',5000,'미소분식','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(217,302,405,'참치콤보',5500,'미소분식','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(218,302,405,'치즈콤보',5500,'미소분식','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(219,303,405,'미소라면',3000,'미소분식','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(220,303,405,'해장라면',3500,'미소분식','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(221,303,405,'김치라면',3500,'미소분식','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(222,304,405,'계란라면',3500,'미소분식','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(223,303,405,'어묵라면',3500,'미소분식','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(224,304,405,'유부우동',4000,'미소분식','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(225,303,405,'김치우동',4500,'미소분식','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(226,304,405,'꼬치어묵우동',4500,'미소분식','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(227,302,405,'유부정식',5000,'미소분식','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(228,301,405,'김치정식',5500,'미소분식','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(229,302,405,'어묵정식',5500,'미소분식','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(230,304,405,'떡만두국',5000,'미소분식','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(231,304,403,'냉메밀소바',5000,'미소분식','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(232,303,405,'김치만두',3500,'명인만두','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(233,304,402,'납작군만두',3000,'명인만두','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(234,302,401,'치즈계란말이 볶음밥',5500,'마미밥집','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(235,302,406,'나시고랭 볶음밥',5000,'마미밥집','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(236,305,401,'낙지 볶음밥',5500,'마미밥집','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(237,305,401,'새우 볶음밥',5500,'마미밥집','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(238,302,401,'야채 비빔밥',6000,'마미밥집','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(239,308,401,'불고기 비빔밥',6000,'마미밥집','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(240,302,401,'치즈밥',5000,'치즈밥있슈','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','031-722-2700','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(241,302,401,'스페셜 치즈밥',6000,'치즈밥있슈','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','031-722-2701','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(242,307,401,'대패삼겹살김치찌개',6000,'치즈밥있슈','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','031-722-2703','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(243,301,401,'양푼부대찌개',6000,'치즈밥있슈','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','031-722-2704','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(244,304,404,'치즈오븐스파게티',6000,'치즈밥있슈','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','031-722-2705','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(245,304,404,'베이컨 버섯 파스타',6000,'치즈밥있슈','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','031-722-2706','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(246,304,404,'까르보나라',6000,'치즈밥있슈','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','031-722-2707','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(247,308,401,'닭쌈',4000,'브리또','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(248,308,404,'소시지 브리또',4000,'브리또','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(249,308,404,'떡갈비 브리또',4000,'브리또','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(250,308,401,'소쌈',5500,'브리또','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(251,308,404,'살싸나초',2500,'브리또','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(252,308,404,'또텔라',3500,'브리또','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(253,308,401,'숯불고기밥',4000,'바비박스','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(254,308,401,'치킨밥',3700,'바비박스','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(255,308,401,'블랙치킨밥',3900,'바비박스','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(256,308,401,'쏘야밥',2900,'바비박스','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(257,308,401,'스팸밥',3400,'바비박스','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(258,307,401,'불닭밥',3900,'바비박스','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(259,308,404,'큐브스테이크밥',6900,'바비박스','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(260,308,404,'그릴떡갈비밥',5900,'바비박스','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(261,302,401,'곤드레밥',3900,'바비박스','성남시 수정구 복정동 가천대학교 가천관 지하 1층 푸드코트','-','-','경기 성남시 수정구 복정동 620-2')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(262,308,401,'삼겹살',4500,'돈마니','경기 성남시 수정구 성남대로 1334 2층','031-722-5564','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(263,308,401,'항정살',5500,'돈마니','경기 성남시 수정구 성남대로 1334 2층','031-722-5564','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(264,303,404,'베이컨 카르보나라 파스타',14900,'가천한마당','경기 성남시 수정구 성남대로 1334 2층','031-721-8291','4:30까지만 점심메뉴','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(265,306,404,'토마토 해물 파스타',14900,'가천한마당','경기 성남시 수정구 성남대로 1334 2층','031-721-8291','4:30까지만 점심메뉴','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(266,304,404,'몽골리안 파스타',14900,'가천한마당','경기 성남시 수정구 성남대로 1334 2층','031-721-8291','4:30까지만 점심메뉴','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(267,304,404,'샐러드 파스타',12900,'가천한마당','경기 성남시 수정구 성남대로 1334 2층','031-721-8291','4:30까지만 점심메뉴','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(268,308,404,'목살플레이트',17900,'가천한마당','경기 성남시 수정구 성남대로 1334 2층','031-721-8291','4:30까지만 점심메뉴','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(269,306,404,'훈제연어 샐러드',14900,'가천한마당','경기 성남시 수정구 성남대로 1334 2층','031-721-8291','4:30까지만 점심메뉴','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(270,307,404,'목살필라프',15900,'가천한마당','경기 성남시 수정구 성남대로 1334 2층','031-721-8291','4:30까지만 점심메뉴','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(271,306,404,'쉬림프해물필라프',15900,'가천한마당','경기 성남시 수정구 성남대로 1334 2층','031-721-8291','4:30까지만 점심메뉴','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(272,308,401,'족발',33000,'가천한마당','경기 성남시 수정구 성남대로 1334 2층','031-721-8291','4:30 이후부터 판매, 뒷발 가격: 27000','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(273,308,401,'냉채 족발',25000,'가천한마당','경기 성남시 수정구 성남대로 1334 2층','031-721-8291','4:30 이후부터 판매, 大: 30000','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(274,308,401,'간장 족발',33000,'가천한마당','경기 성남시 수정구 성남대로 1334 2층','031-721-8291','4:30 이후부터 판매','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(275,308,401,'마늘 족발',33000,'가천한마당','경기 성남시 수정구 성남대로 1334 2층','031-721-8291','4:30 이후부터 판매','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(276,308,401,'보쌈',17000,'가천한마당','경기 성남시 수정구 성남대로 1334 2층','031-721-8291','4:30 이후부터 판매','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(277,305,401,'쭈꾸미 콩나물',14000,'가천한마당','경기 성남시 수정구 성남대로 1334 2층','031-721-8291','4:30 이후부터 판매','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(278,305,401,'쭈꾸미 목살',16000,'가천한마당','경기 성남시 수정구 성남대로 1334 2층','031-721-8291','4:30 이후부터 판매','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(279,305,401,'쭈꾸미 훈제치킨',17000,'가천한마당','경기 성남시 수정구 성남대로 1334 2층','031-721-8291','4:30 이후부터 판매','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(280,308,404,'폭찹',20000,'가천한마당','경기 성남시 수정구 성남대로 1334 2층','031-721-8291','4:30 이후부터 판매','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(281,308,401,'통오징어 삼겹살',13000,'가천한마당','경기 성남시 수정구 성남대로 1334 2층','031-721-8291','4:30 이후부터 판매','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(282,305,405,'해물 떡볶이',10000,'가천한마당','경기 성남시 수정구 성남대로 1334 2층','031-721-8291','4:30 이후부터 판매','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(283,307,406,'치킨뱅이',22000,'가천한마당','경기 성남시 수정구 성남대로 1334 2층','031-721-8291','4:30 이후부터 판매','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(284,307,401,'쇠고기 버섯찌개',12000,'가천한마당','경기 성남시 수정구 성남대로 1334 2층','031-721-8291','4:30 이후부터 판매','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(285,301,401,'부대찌개',12000,'가천한마당','경기 성남시 수정구 성남대로 1334 2층','031-721-8291','4:30 이후부터 판매','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(286,307,401,'무뼈 닭발',10000,'가천한마당','경기 성남시 수정구 성남대로 1334 2층','031-721-8291','4:30 이후부터 판매','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(287,308,404,'탄두리치킨 샐러드',11000,'디왈리','경기 성남시 수정구 성남대로 1334 2층','031-722-0782','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(288,308,404,'탄두리치킨 티카',10000,'디왈리','경기 성남시 수정구 성남대로 1334 2층','031-722-0782','뼈없는 바비큐','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(289,307,404,'칠리치킨',10000,'디왈리','경기 성남시 수정구 성남대로 1334 2층','031-722-0782','인도식 닭고기 요리','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(290,308,404,'램 보티 케밥',14000,'디왈리','경기 성남시 수정구 성남대로 1334 2층','031-722-0782','뼈없는 양고기','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(291,308,404,'탄두리 바비큐 플레더',22000,'디왈리','경기 성남시 수정구 성남대로 1334 2층','031-722-0782','탄두리 바비큐 모듬','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(292,308,404,'비프 피아자',11000,'디왈리','경기 성남시 수정구 성남대로 1334 2층','031-722-0782','소고기 커리','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(293,306,406,'프로운 삭',10000,'디왈리','경기 성남시 수정구 성남대로 1334 2층','031-722-0782','왕새우 커리','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(294,302,406,'팔락 파니르',9000,'디왈리','경기 성남시 수정구 성남대로 1334 2층','031-722-0782','시금치 커리','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(295,308,406,'치킨 마크니',9000,'디왈리','경기 성남시 수정구 성남대로 1334 2층','031-722-0782','연한 치킨 커리','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(296,304,406,'난',2000,'디왈리','경기 성남시 수정구 성남대로 1334 2층','031-722-0782','인도식 전통 빵','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(297,308,404,'데리야끼 치킨',14000,'미스터리','경기 성남시 수정구 성남대로 1334 2층','031-755-9777','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(298,304,406,'오감자튀김',10000,'미스터리','경기 성남시 수정구 성남대로 1334 2층','031-755-9777','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(299,304,404,'감자 그라탕',7000,'미스터리','경기 성남시 수정구 성남대로 1334 2층','031-755-9777','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(300,308,404,'소시지 그라탕',7000,'미스터리','경기 성남시 수정구 성남대로 1334 2층','031-755-9777','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(301,307,401,'찜닭',14000,'미스터리','경기 성남시 수정구 성남대로 1334 2층','031-755-9777','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(302,303,405,'국물 떡볶이',11000,'미스터리','경기 성남시 수정구 성남대로 1334 2층','031-755-9777','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(303,301,401,'부대볶음',13000,'미스터리','경기 성남시 수정구 성남대로 1334 2층','031-755-9777','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(304,308,404,'함박스테이크',5500,'이가네돈까스','경기 성남시 수정구 성남대로 1334 1층','050-4570-5533','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(305,302,404,'칠리오므라이스',6500,'이가네돈까스','경기 성남시 수정구 성남대로 1334 1층','050-4570-5533','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(306,302,404,'크림오므라이스',6500,'이가네돈까스','경기 성남시 수정구 성남대로 1334 1층','050-4570-5533','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(307,302,404,'피자치즈오므라이스',6500,'이가네돈까스','경기 성남시 수정구 성남대로 1334 1층','050-4570-5533','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(308,301,401,'꽁치김치찌개',6000,'이가네돈까스','경기 성남시 수정구 성남대로 1334 1층','050-4570-5533','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(309,307,401,'닭개장',6000,'이가네돈까스','경기 성남시 수정구 성남대로 1334 1층','050-4570-5533','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(310,302,401,'설렁탕',6000,'이가네돈까스','경기 성남시 수정구 성남대로 1334 1층','050-4570-5533','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(311,301,401,'다슬기해장국',6000,'이가네돈까스','경기 성남시 수정구 성남대로 1334 1층','050-4570-5533','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(312,302,401,'나물비빔밥',5500,'이가네돈까스','경기 성남시 수정구 성남대로 1334 1층','050-4570-5533','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(313,301,401,'김치볶음밥',5500,'이가네돈까스','경기 성남시 수정구 성남대로 1334 1층','050-4570-5533','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(314,303,405,'신라면',3000,'이가네돈까스','경기 성남시 수정구 성남대로 1334 1층','050-4570-5533','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(315,303,405,'떡만두라면',4000,'이가네돈까스','경기 성남시 수정구 성남대로 1334 1층','050-4570-5533','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(316,303,405,'짬뽕라면',4500,'이가네돈까스','경기 성남시 수정구 성남대로 1334 1층','050-4570-5533','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(317,303,405,'해물라면',4500,'이가네돈까스','경기 성남시 수정구 성남대로 1334 1층','050-4570-5533','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(318,302,405,'원조김밥',2500,'이가네돈까스','경기 성남시 수정구 성남대로 1334 1층','050-4570-5533','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(319,302,405,'소고기김밥',3500,'이가네돈까스','경기 성남시 수정구 성남대로 1334 1층','050-4570-5533','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(320,303,405,'떡볶이',4000,'이가네돈까스','경기 성남시 수정구 성남대로 1334 1층','050-4570-5533','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(321,304,405,'떡국',5000,'이가네돈까스','경기 성남시 수정구 성남대로 1334 1층','050-4570-5533','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(322,304,405,'만두국',5000,'이가네돈까스','경기 성남시 수정구 성남대로 1334 1층','050-4570-5533','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(323,304,405,'떡만두국',5000,'이가네돈까스','경기 성남시 수정구 성남대로 1334 1층','050-4570-5533','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(324,304,405,'칼국수',5000,'이가네돈까스','경기 성남시 수정구 성남대로 1334 1층','050-4570-5533','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(325,304,402,'취룡자장',4500,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(326,303,402,'취룡짬뽕',5000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(327,304,402,'매생이굴탕면',7500,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(328,303,402,'취룡삼선굴짬뽕',7500,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(329,304,402,'삼선울면',6500,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(330,304,402,'삼선우동',6500,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(331,303,402,'사천탕면',7500,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(332,304,402,'삼선간자장',6500,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(333,302,402,'김치짬뽕밥',6000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(334,305,402,'해물냉짬뽕',7500,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','계절메뉴','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(335,305,402,'해물볶음밥',7000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(336,308,401,'쇠고기볶음밥',7000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(337,302,402,'삼선유산슬덮밥',9000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(338,302,402,'굴소스안심새송이덮밥',9000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(339,302,402,'닭고기 커리소스덮밥',7500,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(340,302,402,'타이식 철판오므라이스',8000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(341,302,402,'삼선잡채밥',8000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(342,302,402,'삼선잡탕밥',12000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(343,305,402,'취룡볶음해짬면',7000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(344,306,402,'취룡볶음해덮밥',8000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(345,306,402,'볶음해물쟁반자장',13000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','(2인), (3인)=16000','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(346,305,402,'칠리풍 중새우',30000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(347,306,402,'깐풍 중새우',30000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(348,308,402,'깐풍기',25000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(349,308,402,'마늘소스 닭요리',22000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','S, L=25000','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(350,308,402,'레몬소스 닭요리',25000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','S, L=28000','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(351,307,402,'고추매콤소스 닭요리',25000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(352,308,402,'호일 굴소스안심 새송이',30000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(353,308,402,'쇠고기 꿔바로우',30000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(354,308,402,'사천식 탕수육',20000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','S, L=23000','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(355,308,402,'북경식 간장 탕수육',20000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','S, L=23000','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(356,307,402,'칠리탕수육',25000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','S, L=28000','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(357,308,402,'레몬탕수육',25000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(358,305,402,'해분유산슬',30000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(359,306,402,'마라팔보채',30000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(360,305,402,'사천식 매운홍합요리',30000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(361,305,402,'새우고추잡채와 꽃빵',25000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(362,306,402,'게살샥스핀',60000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(363,306,402,'삼선샥스핀',60000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(364,306,402,'해물누룽지탕',18000,'취룡','경기 성남시 수정구 성남대로 1334 1층','031-721-5688','S, L=30000','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(365,302,405,'그집김밥',2500,'그집 김밥','경기 성남시 수정구 성남대로 1330','031-722-5553','','경기 성남시 수정구 성남대로 1330')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(366,302,405,'김치치즈김밥',3500,'그집 김밥','경기 성남시 수정구 성남대로 1330','031-722-5553','','경기 성남시 수정구 성남대로 1330')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(367,302,405,'스팸김밥',4000,'그집 김밥','경기 성남시 수정구 성남대로 1330','031-722-5553','','경기 성남시 수정구 성남대로 1330')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(368,303,405,'그집떡볶이',3500,'그집 김밥','경기 성남시 수정구 성남대로 1330','031-722-5553','','경기 성남시 수정구 성남대로 1330')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(369,302,403,'일본식카레덮밥',5000,'그집 김밥','경기 성남시 수정구 성남대로 1330','031-722-5553','','경기 성남시 수정구 성남대로 1330')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(370,301,401,'김치햄볶음밥',5500,'그집 김밥','경기 성남시 수정구 성남대로 1330','031-722-5553','','경기 성남시 수정구 성남대로 1330')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(371,301,401,'김치참치볶음밥',5500,'그집 김밥','경기 성남시 수정구 성남대로 1330','031-722-5553','','경기 성남시 수정구 성남대로 1330')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(372,301,401,'김치피자치즈볶음밥',6000,'그집 김밥','경기 성남시 수정구 성남대로 1330','031-722-5553','','경기 성남시 수정구 성남대로 1330')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(373,304,405,'왕만두',5000,'그집 김밥','경기 성남시 수정구 성남대로 1330','031-722-5553','','경기 성남시 수정구 성남대로 1330')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(374,308,403,'일본식생왕돈까스',6500,'그집 김밥','경기 성남시 수정구 성남대로 1330','031-722-5553','','경기 성남시 수정구 성남대로 1330')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(375,302,401,'가마솥곰탕',7000,'그집 김밥','경기 성남시 수정구 성남대로 1330','031-722-5553','','경기 성남시 수정구 성남대로 1330')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(376,302,401,'바지락된장찌개',5500,'그집 김밥','경기 성남시 수정구 성남대로 1330','031-722-5553','','경기 성남시 수정구 성남대로 1330')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(377,302,401,'해물순두부',5500,'그집 김밥','경기 성남시 수정구 성남대로 1330','031-722-5553','','경기 성남시 수정구 성남대로 1330')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(378,308,401,'소고기미역국',6000,'그집 김밥','경기 성남시 수정구 성남대로 1330','031-722-5553','','경기 성남시 수정구 성남대로 1330')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(379,305,401,'황태해장국',6000,'그집 김밥','경기 성남시 수정구 성남대로 1330','031-722-5553','','경기 성남시 수정구 성남대로 1330')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(380,306,401,'고등어구이',6500,'그집 김밥','경기 성남시 수정구 성남대로 1330','031-722-5553','','경기 성남시 수정구 성남대로 1330')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(381,306,401,'삼치구이',6500,'그집 김밥','경기 성남시 수정구 성남대로 1330','031-722-5553','','경기 성남시 수정구 성남대로 1330')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(382,301,401,'부대찌개',6000,'준호네부대찌개','경기 성남시 수정구 성남대로 1330','070-4103-0906','2인이상 주문가능','경기 성남시 수정구 성남대로 1330')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(383,302,401,'매화도시락',10000,'한솥도시락가천대점','경기 성남시 수정구 태평로 3','031-721-7748','','경기 성남시 수정구 태평로 3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(384,302,401,'반찬 돈까스도련님',2900,'한솥도시락가천대점','경기 성남시 수정구 태평로 3','031-721-7748','','경기 성남시 수정구 태평로 3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(385,302,401,'반찬 고기고기',2900,'한솥도시락가천대점','경기 성남시 수정구 태평로 3','031-721-7748','','경기 성남시 수정구 태평로 3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(386,302,401,'동백도시락',5000,'한솥도시락가천대점','경기 성남시 수정구 태평로 3','031-721-7748','','경기 성남시 수정구 태평로 3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(387,302,401,'개나리도시락',8000,'한솥도시락가천대점','경기 성남시 수정구 태평로 3','031-721-7748','','경기 성남시 수정구 태평로 3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(388,302,401,'진달래도시락',7000,'한솥도시락가천대점','경기 성남시 수정구 태평로 3','031-721-7748','','경기 성남시 수정구 태평로 3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(389,305,401,'반찬 고등어조림',4800,'한솥도시락가천대점','경기 성남시 수정구 태평로 3','031-721-7748','','경기 성남시 수정구 태평로 3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(390,308,401,'닭가슴살샐러드마요',3500,'한솥도시락가천대점','경기 성남시 수정구 태평로 3','031-721-7748','','경기 성남시 수정구 태평로 3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(391,306,401,'전복죽',10000,'본죽 가천대역점','경기 성남시 수정구 태평로 3','031-758-5288','','경기 성남시 수정구 태평로 3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(392,306,401,'전복내장죽',12000,'본죽 가천대역점','경기 성남시 수정구 태평로 3','031-758-5288','','경기 성남시 수정구 태평로 3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(393,308,401,'삼계전복죽',15000,'본죽 가천대역점','경기 성남시 수정구 태평로 3','031-758-5288','','경기 성남시 수정구 태평로 3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(394,308,401,'자연송이쇠고기죽',12000,'본죽 가천대역점','경기 성남시 수정구 태평로 3','031-758-5288','','경기 성남시 수정구 태평로 3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(395,306,401,'홍게살죽',9000,'본죽 가천대역점','경기 성남시 수정구 태평로 3','031-758-5288','','경기 성남시 수정구 태평로 3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(396,305,401,'불낙죽',9000,'본죽 가천대역점','경기 성남시 수정구 태평로 3','031-758-5288','','경기 성남시 수정구 태평로 3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(397,301,401,'신짬뽕죽',9500,'본죽 가천대역점','경기 성남시 수정구 태평로 3','031-758-5288','','경기 성남시 수정구 태평로 3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(398,305,401,'낙지김치죽',8500,'본죽 가천대역점','경기 성남시 수정구 태평로 3','031-758-5288','','경기 성남시 수정구 태평로 3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(399,302,401,'단호박죽',8000,'본죽 가천대역점','경기 성남시 수정구 태평로 3','031-758-5288','','경기 성남시 수정구 태평로 3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(400,302,401,'동지팥죽',8500,'본죽 가천대역점','경기 성남시 수정구 태평로 3','031-758-5288','','경기 성남시 수정구 태평로 3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(401,308,401,'쇠고기야채죽',8500,'본죽 가천대역점','경기 성남시 수정구 태평로 3','031-758-5288','','경기 성남시 수정구 태평로 3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(402,308,401,'쇠고기버섯죽',8000,'본죽 가천대역점','경기 성남시 수정구 태평로 3','031-758-5288','','경기 성남시 수정구 태평로 3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(403,302,401,'매생이굴죽',9500,'본죽 가천대역점','경기 성남시 수정구 태평로 3','031-758-5288','','경기 성남시 수정구 태평로 3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(404,306,401,'버섯굴죽',9000,'본죽 가천대역점','경기 성남시 수정구 태평로 3','031-758-5288','','경기 성남시 수정구 태평로 3')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(405,308,402,'탕수육',11000,'북경반점','경기 성남시 수정구 성남대로 1334 F4층','-','소, 중=14000, 대=17000','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(406,304,402,'짜장면',3500,'북경반점','경기 성남시 수정구 성남대로 1334 F4층','-','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(407,303,402,'짬뽕',4500,'북경반점','경기 성남시 수정구 성남대로 1334 F4층','-','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(408,304,402,'쟁반짜장',5000,'북경반점','경기 성남시 수정구 성남대로 1334 F4층','-','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(409,303,402,'매운빨강짜장',5000,'북경반점','경기 성남시 수정구 성남대로 1334 F4층','-','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(410,302,402,'짜장밥',4500,'북경반점','경기 성남시 수정구 성남대로 1334 F4층','-','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(411,302,402,'볶음밥',4500,'북경반점','경기 성남시 수정구 성남대로 1334 F4층','-','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(412,301,402,'짬뽕밥',4500,'북경반점','경기 성남시 수정구 성남대로 1334 F4층','-','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(413,302,402,'잡채밥',5500,'북경반점','경기 성남시 수정구 성남대로 1334 F4층','-','','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(414,308,401,'안동찜닭',17000,'내찜닭','경기 성남시 수정구 성남대로 1334 F4층','031-751-5552','소=17000, 중=25000, 대=34000(치즈 추가 3000원)','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(415,308,401,'순살안동찜닭',17000,'내찜닭','경기 성남시 수정구 성남대로 1334 F4층','031-751-5552','소=17000, 중=25000, 대=34000(치즈 추가 3000원)','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(416,307,401,'매콤순살고추장찜닭',17000,'내찜닭','경기 성남시 수정구 성남대로 1334 F4층','031-751-5552','소=17000, 중=25000, 대=34000(치즈 추가 3000원)','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);
            sql = "insert or replace into "+ tableName + "(per_id, group_id, type_id, fName, price, rName, address, number, etc, address2) values(417,307,401,'매콤고추장찜닭',17000,'내찜닭','경기 성남시 수정구 성남대로 1334 F4층','031-751-5552','소=17000, 중=25000, 대=34000(치즈 추가 3000원)','경기 성남시 수정구 성남대로 1334')";            database.execSQL(sql);



            database.setTransactionSuccessful(); //트랜잭션으로 묶어준 일정영역의 SQL문들이 모두 성공적으로 끝났을 지정
        }catch(Exception e){
            //SQL문 실행중 오류가 발생하면 예외처리가 되고..
            //트랜잭션에 정의된 SQL쿼리가 성공되지 않았기때문에 finally블록에서
            //트랜잭션 종료메서드 실행시(endTransaction()) 롤백이 된다.
            Log.d("ABCDEFG","catch");
            Log.d("ABCDEFG",e.getMessage()+"");
        }finally{
            Log.d("ABCDEFG","인설트finally");
            database.endTransaction(); //트랜잭션을 끝내는 메소드.
            Log.d("ABCDEFG","인설트finally// endTransaction후");
        }
    }
    private void queryData(int group_id,int type_id) {
        String sql = "select per_id, group_id, type_id, fName, price, rName, address, number, etc, address2 from " + tableName + " where group_id = " + group_id + " and type_id = " + type_id + " order by price";
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor != null) {
            //address="";
            count = cursor.getCount(); //조회된 개수얻기
            for (int i = 0; i < count; i++) {
                cursor.moveToNext();
                address += cursor.getString(9) + ".";
                foodN += cursor.getString(5) + ".";
                foodlist += cursor.getString(3) + ".";
                info += "가격: " + cursor.getString(4) + "원\n\n음식점 이름: " + cursor.getString(5) + "\n\n주소: " + cursor.getString(6) + "\n\n전화번호: " + cursor.getString(7) + "\n\n참고사항: " + cursor.getString(8) + ".";
            }
        }
        cursor.close();
    }
    private void queryData2(int per_id){
        String sql = "select fName, price, rName, address, number, etc, address2 from "+ tableName + " where per_id = " + per_id;
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor != null){
            cursor.moveToNext();
            address += cursor.getString(6)+".";
            foodN += cursor.getString(2)+".";
            randFood+=cursor.getString(0) +".";
            randInfo += "가격: " + cursor.getString(1) + "원\n\n음식점 이름: " + cursor.getString(2) + "\n\n주소: " + cursor.getString(3) + "\n\n전화번호: " + cursor.getString(4) + "\n\n참고사항: " + cursor.getString(5) + ".";
        }
        cursor.close();
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
            finish();
    }

}

