package org.androidtown.termproject;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DutchPay extends AppCompatActivity {
    ImageButton button;
    EditText cash;
    EditText person;
    TextView oneMoney, twoMoney, threeMoney;
    TextView one, two, three;
    LinearLayout linear;
    InputMethodManager imm;
    Intent intent;
    ImageView persons, money, pumkin, pumkin2, pumkin3, arrow1, arrow2, arrow3;
    int color= Color.parseColor("#FFF3E658");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dutch_pay);
        linear= (LinearLayout) findViewById(R.id.linearLayout);
        linear.setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.dutch_background)));
        button = (ImageButton) findViewById(R.id.button);
        button.setImageDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.okbutton)));
        cash = (EditText) findViewById(R.id.editText);
        cash.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        person = (EditText) findViewById(R.id.editText2);
        person.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        oneMoney = (TextView) findViewById(R.id.oneMoney);
        twoMoney = (TextView) findViewById(R.id.twoMoney);
        threeMoney = (TextView) findViewById(R.id.threeMoney);
        one = (TextView) findViewById(R.id.one);
        two = (TextView) findViewById(R.id.two);
        three = (TextView) findViewById(R.id.three);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        persons = (ImageView) findViewById(R.id.persons);
        money = (ImageView) findViewById(R.id.money);
        pumkin = (ImageView) findViewById(R.id.pumkin);
        pumkin2 = (ImageView) findViewById(R.id.pumkin2);
        pumkin3 = (ImageView) findViewById(R.id.pumkin3);
        arrow1 = (ImageView) findViewById(R.id.arrow1);
        arrow2 = (ImageView) findViewById(R.id.arrow2);
        arrow3 = (ImageView) findViewById(R.id.arrow3);
        persons.setImageDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable. persons)));
        money.setImageDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.money)));
        pumkin.setImageDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable. pumkin)));
        pumkin2.setImageDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable. pumkin2)));
        pumkin3.setImageDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable. pumkin3)));
        arrow1.setImageDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.arrow)));
        arrow2.setImageDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.arrow)));
        arrow3.setImageDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.arrow)));

        button.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                imm.hideSoftInputFromWindow(linear.getWindowToken(), 0);
                if(cash.getText().toString().equals("")||person.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"숫자를 입력해주세요 ! ",Toast.LENGTH_LONG).show();
                    return;
                }
                if(cash.getText().toString().length()>9){
                    Toast.makeText(getApplicationContext(),"10자리 이하로 입력해주세요 ! ",Toast.LENGTH_LONG).show();
                    return;
                }
                int total = Integer.parseInt(cash.getText().toString());
                int persons = Integer.parseInt(person.getText().toString());
                int sum = total/persons;
                sum = (sum/1000)*1000;
                //총 내야하는 돈을 인원수로 나누었을 때 값//나눈 돈을 1000원 단위로 내림을 함
                int change=0;
                change = total - (sum*persons);//나머지 돈은 총 내야할 돈에서 천원단위로 내야할 돈과 인원수의 곱을 뺍니다.
                if(total<1000){
                    String moeny = String.format("%4d",total);
                    oneMoney.setText(money+"원");
                    two.setText("x "+(persons-1));
                    three.setText("x 0");
                    twoMoney.setText("0000원");
                    threeMoney.setText("0000원");
                }
                if(persons==1)
                {one.setText("x "+persons);
                    String money = String.format("%4d",total);
                    oneMoney.setText(money+"원");
                    two.setText("x 0");
                    three.setText("x 0");
                    twoMoney.setText("0000원");
                    threeMoney.setText("0000원");

                }
                if(sum*persons == total&&persons!=1)
                {
                    one.setText("x "+persons);
                    String money = String.format("%4d",sum);
                    oneMoney.setText(money+"원");
                    two.setText("x 0");
                    three.setText("x 0");
                    twoMoney.setText("0000원");
                    threeMoney.setText("0000원");

                }
                else if(sum*persons!=total && persons!=1)
                {
                    int flag =1;
                    if(change>=1000)//나누어 떨어지지 않을 때
                    {
                        int i=0;
                        int j=0;
                        while(true)//나누어 떨어지지 않는 돈 나눠가질 사람의 수
                        {
                            if(change<1000) {//천원으로 나누어 떨어지지 않는 돈
                                if(change!=0) {
                                    flag = 0;
                                    j++;
                                }
                                break;
                            }
                            change = change-1000;//천원단위로 나눠가짐
                            i++;
                        }
                        if(flag==1)
                        {
                            if(persons-i-j==0)
                            {
                                one.setText("x "+(persons-i-j));
                                oneMoney.setText("0000원");
                            }
                            else {
                                one.setText("x " + (persons - i-j));
                                String money = String.format("%4d", sum);
                                oneMoney.setText(money + "원");
                            }

                            two.setText("x "+i);
                            String money2 = String.format("%4d",sum+1000);
                            twoMoney.setText(money2+"원");
                            three.setText("x 0");
                            threeMoney.setText("0000원");
                        }
                        else //백원짜리 나오면
                        {

                            if(persons-i-j==0)
                            {

                                one.setText("x "+i);
                                String money2 = String.format("%4d",sum+1000);
                                oneMoney.setText(money2+"원");

                                two.setText("x "+j);
                                String money3 = String.format("%4d",sum+change);
                                twoMoney.setText(money3+"원");
                                three.setText("x 0");
                                threeMoney.setText("0000원");
                            }

                            else {
                                one.setText("x " + (persons - i - j));
                                String money = String.format("%4d", sum);
                                oneMoney.setText(money + "원");
                                two.setText("x "+i);
                                String money2 = String.format("%4d",sum+1000);
                                twoMoney.setText(money2+"원");

                                three.setText("x "+j);
                                String money3 = String.format("%4d",sum+change);
                                threeMoney.setText(money3+"원");
                            }


                        }

                    }
                    else if(change<1000)//change<1000
                    {
                        one.setText("x "+ (persons-1));
                        String money= String.format("%4d", sum);
                        oneMoney.setText(money+"원");
                        two.setText("x 1");
                        String money2= String.format("%4d",sum+change);
                        twoMoney.setText(money2+"원");
                        three.setText("x 0");
                        threeMoney.setText("0000원");
                    }

                }
            }
        });


    }


    public void onBackPressed()
    {
        finish();
    }

    @Override
    public void onDestroy() {

        recycleView(findViewById(R.id.linearLayout));
        recycleImage(persons);
        recycleImage(pumkin);
        recycleImage(pumkin2);
        recycleImage(pumkin3);
        recycleImage(arrow1);
        recycleImage(arrow2);
        recycleImage(arrow3);
        super.onDestroy();

    }


    private  void recycleImage(ImageView imageView){
        if(imageView!=null){

            Drawable bg = imageView.getDrawable();

            if(bg!=null){
                bg.setCallback(null);
                ((BitmapDrawable)bg).getBitmap().recycle();
                imageView.setImageDrawable(null);

            }

        }
    }
    private void recycleView(View view) {

        if(view != null) {

            Drawable bg = view.getBackground();

            if(bg != null) {

                bg.setCallback(null);

                ((BitmapDrawable)bg).getBitmap().recycle();

                view.setBackgroundDrawable(null);

            }

        }

    }



}