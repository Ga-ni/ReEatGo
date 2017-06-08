package org.androidtown.termproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

public class Ladder extends AppCompatActivity {
    FrameLayout sadariContainer;
    ImageView Image;
    TextView randTextView;
    MyView mv;
    Intent intent;
    String getFoodName;
    String getFoodInfo;
    String getrestAddress;
    String food;
    String[] foodName=new String[5];
    String[] foodInfo=new String[5];
    String[] restAddress=new String[5];
    String[] food2 = new String[5];
    int animId = 0;
    private int lastXidx=-1;
    Random randomS= new Random();
    float sadariarr[][] = new float[4][8];
    ArrayList<TranslateAnimation> tAnim = new ArrayList<TranslateAnimation>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ladder);

        sadariContainer=(FrameLayout)findViewById(R.id.sadariPart);
        randTextView=(TextView)findViewById(R.id.result);
        Image=(ImageView)findViewById(R.id.pumpkin);

        randTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastXidx!=-1){
                    intent = new Intent (getApplicationContext(), FoodInfo.class);
                    intent.putExtra("info",foodInfo[lastXidx-1]);
                    intent.putExtra("address",restAddress[lastXidx-1]);
                    intent.putExtra("food",food2[lastXidx-1]);
                    startActivity(intent);
                }
            }
        });

        //mainActivity로부터 받은 intent에서 string(음식 5개)을 받아서 어레이에 하나씩 넣는다.
        Intent intent;
        intent = getIntent();
<<<<<<< HEAD
        getFoodName = intent.getStringExtra("randFood");
        getFoodInfo = intent.getStringExtra("randInfo");
        getrestAddress=intent.getStringExtra("randAddress");
        food=intent.getStringExtra("food");
        Log.d("Ladder","레더\ngetfoodName: "+getFoodName+"\ngetFoodInfo: "+getFoodInfo+"\ngetrestAdd: "+getrestAddress+"\nrestName: "+food);

        Log.d("Ladder","어레이확인!!\nfoodName");
=======
        outFoodlist = intent.getStringExtra("randFood");
        Log.d("tag",""+outFoodlist);
>>>>>>> origin/master
        int j=0,i=-1;
        while(j<5){
            i++;
            if(getFoodName.charAt(i) == '.') {
                foodName[j]=getFoodName.substring(0,i);
                getFoodName=getFoodName.substring(i+1);
                Log.d("Ladder","\n"+foodName[j]);
                j++;
                i=-1;
            }
        }
        Log.d("Ladder","어레이확인!!\nfoodInfo");
        j=0;i=-1;
        while(j<5){
            i++;
            if(getFoodInfo.charAt(i) == '.') {
                foodInfo[j]=getFoodInfo.substring(0,i);
                getFoodInfo=getFoodInfo.substring(i+1);
                Log.d("Ladder","\n"+foodInfo[j]);
                j++;
                i=-1;
            }
        }
        Log.d("Ladder","어레이확인!!\nrestAddress");
        j=0;i=-1;
        while(j<5){
            i++;
            if(getrestAddress.charAt(i) == '.') {
                restAddress[j]=getrestAddress.substring(0,i);
                getrestAddress=getrestAddress.substring(i+1);
                Log.d("Ladder","\n"+restAddress[j]);
                j++;
                i=-1;
            }
        }
        Log.d("Ladder","어레이확인!!\nrestName");
        j=0;i=-1;
        while(j<5){
            i++;
            if(food.charAt(i) == '.') {
                food2[j]=food.substring(0,i);
                food=food.substring(i+1);
                Log.d("Ladder","\n"+food2[j]);
                j++;
                i=-1;
            }
        }

        //사다리를 그리기 위해 FrameLayout인 sadariContainer에 customized view를 넣어준다.
        mv= new MyView(this);
        sadariContainer.addView(mv);
        Image.bringToFront();
    }




    //---------------사다리를 그리는 customized VIew------------//
    protected class MyView extends View{
        //constructor
        public MyView(Context context){
            super(context);
        }
        //사다리 그리고 사다리 어레이 저장하기
        public void onDraw (Canvas canvas){
            Paint pnt = new Paint(Paint.ANTI_ALIAS_FLAG);
            int color= Color.parseColor("#CC9933");
            pnt.setColor(color);
            pnt.setStrokeWidth(20);
            pnt.setStrokeCap(Paint.Cap.ROUND);

<<<<<<< HEAD
            float baseX= sadariContainer.getWidth()/10;//this    /w:768,h:600(sadariContainer)
            float startY = sadariContainer.getBaseline();//15.0//+16이었음
            float finishY = startY+sadariContainer.getHeight()-20;//875.0 //sadariContainer.getBottom()-355;
=======
            float baseX= getRootView().getWidth()/10;//this
            float startY = getRootView().getBaseline()+16;//15.0
            float finishY = sadariContainer.getBottom();//875.0 //sadariContainer.getBottom()-355;
>>>>>>> origin/master
            int i=0,j=0;
            //세로로 라인 5개를 그린다.
            canvas.drawLine(baseX, startY, baseX, finishY, pnt);
            canvas.drawLine(baseX*3, startY, baseX*3, finishY, pnt);
            canvas.drawLine(baseX*5, startY, baseX*5, finishY, pnt);
            canvas.drawLine(baseX*7, startY, baseX*7, finishY, pnt);
            canvas.drawLine(baseX*9, startY, baseX*9, finishY, pnt);
            //랜덤값으로 사다리의 위치값을 array에 저장한다.
            for (i = 0; i < 4; i++) {
                for (j = 0; j < 8; j++) {
                    if (j == 0) {
                        sadariarr[i][j] = startY + 5 + randomS.nextInt(sadariContainer.getHeight() / 4);
                        if(i==0)
                            sadariarr[i][j]+=10;
                    }
                    else if (j % 2 == 0) {
                        do {
                            sadariarr[i][j] = startY + 5 + randomS.nextInt(sadariContainer.getHeight()/4-5);
                        } while (abs(sadariarr[i][j] - sadariarr[i][j - 1]) < 15);
                    } else
                        sadariarr[i][j] = startY + 5 + randomS.nextInt(sadariContainer.getHeight()/4-5);
                }
                startY += sadariContainer.getHeight()/4;
            }
            //array에 저장한 값으로 사다리를 그린다.
            for(j=1;j<8;j+=2){
                for(i=0;i<4;i++){
                    canvas.drawLine(baseX*j,sadariarr[i][j-1],baseX*(j+2),sadariarr[i][j],pnt);
                }
            }
        }//onDraw
    }//MyView




    //---------------button을 눌렀을 때 각 버튼에 맞게 이미지가 움직이도록 해주는 onclick method----------------//
    public void buttonClicked(View v){
        animId=0;
        lastXidx=-1;
        tAnim.clear();
        randTextView.setClickable(false);
        float[][] imagearr;
        imagearr=sadariarr;
        //-----애니메이션에 필요한 variables----//
        Button selected = (Button)v;
        float imageBase= sadariContainer.getWidth()/10-(10);
        float startY=sadariContainer.getBaseline();//-14였음.
        float finishY=sadariContainer.getMeasuredHeight()-20;
        int xIdx=Integer.parseInt(selected.getText().toString());//1~5
        int curLv=0, left=0, right=0;
        float min=-1;
        boolean toLeft=false, toRight=false;

        //그림의 위치를 맞추기 위해 y 좌표값 수정
        for(int i=0;i<4;i++){
            for(int j=0;j<8;j++){
                if(imagearr[i][j]>40)
                    imagearr[i][j]-=30;
                else if(imagearr[i][j]>10)
                    imagearr[i][j]-=5;
            }
        }

        // ↓↓↓애니메이션 시작↓↓↓
        //맨처음 아래로 이동하기
        //현재의 xIdx에따라 left와 right의 index를 정하고 왼쪽으로갈지 오른쪽으로 갈지를 toLeft, toRight로 정한다.
        if(xIdx==1){
            makeAnimation(imageBase*(xIdx*2-1),imageBase*(xIdx*2-1),startY,imagearr[curLv][0]);
            toLeft=false;toRight=true;
        } else if(xIdx==5){
            makeAnimation(imageBase*(xIdx*2-1),imageBase*(xIdx*2-1),startY,imagearr[curLv][7]);
            toLeft=true;toRight=false;
        } else{
            if(xIdx==2) {left=1;right=2;}
            else if(xIdx==3) {left=3;right=4;}
            else {left=5;right=6;}

            if(imagearr[curLv][left]<imagearr[curLv][right]) {
                min=imagearr[curLv][left];
                toLeft=true;toRight=false;
            } else if(imagearr[curLv][left]>imagearr[curLv][right]){
                min=imagearr[curLv][right];
                toLeft=false;toRight=true;
            }
            //아래로 이동한다.
            makeAnimation(imageBase*(xIdx*2-1),imageBase*(xIdx*2-1),startY,min);
        }


        //가로이동 & 아래이동 while loop
        int count=0;
        while(count<17){
            //****가로로 이동하기****
            // xIdx에 따라서 left와 right의 index를 정한다.
            if(xIdx==1){
                left=right=0;
                toLeft=false;toRight=true;
            }else if(xIdx==5){
                left=right=7;
                toLeft=true;toRight=false;
            }else{
                if(xIdx==2) {left=1;right=2;}
                else if(xIdx==3) {left=3;right=4;}
                else {left=5;right=6;}
            }

            if(toLeft){
                makeAnimation(imageBase*(xIdx*2-1),imageBase*((--xIdx)*2-1),imagearr[curLv][left],imagearr[curLv][--left]);
            }else if(toRight)
                makeAnimation(imageBase*(xIdx*2-1),imageBase*((++xIdx)*2-1),imagearr[curLv][right],imagearr[curLv][++right]);

            //****아래로 내려가기****
            // -> 앞에서 왼쪽으로 이동한 경우(이미지는 right(index)에 있음)
            if(toLeft) {
                //left와 right의 index를 현재 위치에 맞게 조정해준다.
                if (left == 0) left = right = 0;
                else if(left==6) {left = 5;right = 6;}
                else {
                    int temp = right - 3;
                    right = left;
                    left = temp;
                }
                //left와 right를 비교한 후, 현재level의 right값이 작으면(_l-) 현재level의 left로 아래로 이동한다.
                if (imagearr[curLv][left] > imagearr[curLv][right]) {
                    makeAnimation(imageBase * (xIdx * 2 - 1), imageBase * ((xIdx) * 2 - 1), imagearr[curLv][right], imagearr[curLv][left]);
                    toLeft = true; toRight = false;
                }
                //현재level의 left값이 작으면(-ㅣ_) 다음level의 left와 right를 비교한다.
                else if (imagearr[curLv][left] < imagearr[curLv][right]) {
                    if(curLv+1==4){//마지막 아래이동인 경우 loop을 빠져나옴.
                        makeAnimation(imageBase * (xIdx * 2 - 1),imageBase * (xIdx * 2 - 1),imagearr[curLv][right],finishY);
                        break;
                    }
                    //다음 레벨의 left가 작은 경우 toLeft를 true로 설정한다.
                    if (imagearr[++curLv][left] < imagearr[curLv][right]) {
                        min = imagearr[curLv][left];
                        toLeft = true; toRight = false;
                    } else {//다음 레벨의 right가 작은 경우 toRight를 true로 설정한다.
                        min = imagearr[curLv][right];
                        toLeft = false;toRight = true;
                    }
                    makeAnimation(imageBase * (xIdx * 2 - 1), imageBase * ((xIdx) * 2 - 1), imagearr[curLv - 1][right],min);
                }else{
                    if(curLv==3){
                        makeAnimation(imageBase * (xIdx * 2 - 1),imageBase * (xIdx * 2 - 1),imagearr[curLv][left],finishY-50);
                        break;
                    }
                    makeAnimation(imageBase * (xIdx * 2 - 1), imageBase * ((xIdx) * 2 - 1), imagearr[curLv][0],imagearr[++curLv][0]);
                }
            }
            //****아래로 내려가기****
            // -> 앞에서 오른쪽으로 이동한 경우(이미지는 left(index)에 있음)
            else{//toRight
                //left와 right의 index값을 현재 위치에 맞게 조정해준다.
                if(right==7) left=right=7;
                else if(left==0){left=1;right=2;}
                else{
                    int temp=left+3;
                    left=right;
                    right=temp;
                }
                //left와 right를 비교한 후, 현재level의 left값이 작으면(-ㅣ_) 현재level의 right로 아래로 이동한다.
                if(imagearr[curLv][left]<imagearr[curLv][right]) {
                    makeAnimation(imageBase*(xIdx*2-1),imageBase*(xIdx*2-1),imagearr[curLv][left],imagearr[curLv][right]);
                    toLeft = false;toRight = true;
                }
                //현재level의 right값이 작으면(_ㅣ-) 다음level의 left와 right를 비교한다.
                else if(imagearr[curLv][left]>imagearr[curLv][right]){
                    if(curLv+1==4){//마지막 아래이동인 경우 loop을 빠져나옴.
                        makeAnimation(imageBase * (xIdx * 2 - 1),imageBase * (xIdx * 2 - 1),imagearr[curLv][left],finishY);
                        break;
                    }
                    //다음level의 left와 right를 비교하여 작은 쪽으로 간다.
                    if(imagearr[++curLv][left]<imagearr[curLv][right]){
                        min = imagearr[curLv][left];
                        toLeft = true; toRight = false;
                    } else {
                        min = imagearr[curLv][right];
                        toLeft = false;toRight = true;
                    }
                    makeAnimation(imageBase * (xIdx * 2 - 1), imageBase * ((xIdx) * 2 - 1), imagearr[curLv - 1][left],min);
                }else{
                    if(curLv==3){
                        makeAnimation(imageBase * (xIdx * 2 - 1),imageBase * (xIdx * 2 - 1),imagearr[curLv][right],finishY-50);
                        break;
                    }
                    makeAnimation(imageBase * (xIdx * 2 - 1), imageBase * ((xIdx) * 2 - 1), imagearr[curLv][7],imagearr[++curLv][7]);
                }
            }
            count++;
        }//while loop


        Drawable drawable=null;
        switch(Integer.parseInt(selected.getText().toString())){
            case 1:
                drawable=getResources().getDrawable(R.drawable.ladder1);
                break;
            case 2:
                drawable=getResources().getDrawable(R.drawable.ladder2);
                break;
            case 3:
                drawable=getResources().getDrawable(R.drawable.ladder3);
                break;
            case 4:
                drawable=getResources().getDrawable(R.drawable.ladder4);
                break;
            case 5:
                drawable=getResources().getDrawable(R.drawable.ladder5);
                break;
            default:
                break;
        }
        Image.setImageDrawable(drawable);
        lastXidx=xIdx;
        Image.startAnimation(tAnim.get(0));


        //어레이값을 돌려놓는다.
        for(int i=0;i<4;i++){
            for(int j=0;j<8;j++){
                if(imagearr[i][j]>40)
                    imagearr[i][j]+=30;
                else if(imagearr[i][j]>10)
                    imagearr[i][j]+=5;
            }
        }
    }//buttonClicked

    public void makeAnimation(float fX,float tX, float fY, float tY) {
        TranslateAnimation tmpTAnim = new TranslateAnimation(fX,tX,fY,tY);
        tmpTAnim.setDuration(300);
        tmpTAnim.setFillAfter(true);
        tmpTAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                animId++;
                if (!(animId >= tAnim.size())){
                    Image.startAnimation(tAnim.get(animId));
                }else{
                    //사다리 타기로 선택된 위치에 있는 음식을 xIdx와 비교하여 textView에 출력해 준다.
                    randTextView.setText(foodName[lastXidx-1]);
                    animId = 0;
                    tAnim.clear();
                    randTextView.setClickable(true);
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        tAnim.add(tmpTAnim);
    }//makeAnimation

    @Override
    public void onBackPressed()
    {
        finish();
    }
}//Ladder