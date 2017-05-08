package org.androidtown.termproject;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DutchPay extends AppCompatActivity {
    Button button;
    EditText cash;
    EditText person;
    TextView oneMoney, twoMoney, threeMoney;
    TextView one, two, three;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dutch_pay);

        button = (Button) findViewById(R.id.button);
        cash = (EditText) findViewById(R.id.editText);
        person = (EditText) findViewById(R.id.editText2);
        oneMoney = (TextView) findViewById(R.id.oneMoney);
        twoMoney = (TextView) findViewById(R.id.twoMoney);
        threeMoney = (TextView) findViewById(R.id.threeMoney);
        one = (TextView) findViewById(R.id.one);
        two = (TextView) findViewById(R.id.two);
        three = (TextView) findViewById(R.id.three);

        button.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                int total = Integer.parseInt(cash.getText().toString());
                int persons = Integer.parseInt(person.getText().toString());
                int sum = total/persons;
                int change;
                sum = (sum/1000)*1000;

                if(sum*persons == total)
                {
                    one.setText("x "+persons);
                    String money = String.format("%4d",sum);
                    oneMoney.setText(money+"원");
                }
                else
                {

                    int flag =1;
                    change = total - (sum*persons);

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
                        }
                        else
                        {
                            if(persons-i-j==0)
                            {

                                one.setText("x "+i);
                                String money2 = String.format("%4d",sum+1000);
                                oneMoney.setText(money2+"원");

                                two.setText("x "+j);
                                String money3 = String.format("%4d",sum+change);
                                twoMoney.setText(money3+"원");
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

                }


            }
        });
    }
}