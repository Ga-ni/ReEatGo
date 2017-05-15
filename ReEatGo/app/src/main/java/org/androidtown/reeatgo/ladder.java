package org.androidtown.reeatgo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;
import static java.lang.Math.abs;

public class ladder extends AppCompatActivity {
    float sadariarr[][] = new float[4][8];
    Random randomS= new Random();
    ArrayList<TranslateAnimation> tAnim = new ArrayList<TranslateAnimation>();

    MyView mv;
    FrameLayout sadariContainer;
    ImageView Image;
    Animation alpha;
    AnimationSet set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ladder);
        sadariContainer=(FrameLayout)findViewById(R.id.sadariPart);//Linear로 하면 심슨+사다리이고, Frame으로 하면 심슨위에 사다리


        float from=1,to=0;
        Image=(ImageView)findViewById(R.id.simpson);
        alpha=new AlphaAnimation(from, to);
        alpha.setFillAfter(true);
        Image.startAnimation(alpha);


        mv= new MyView(this);
        sadariContainer.addView(mv);
        Image.bringToFront();
        Log.d("tag","뷰 그린 후");
    }

    protected class MyView extends View{
        public MyView(Context context){
            super(context);
        }

        public void onDraw (Canvas canvas){
            Paint pnt = new Paint(Paint.ANTI_ALIAS_FLAG);
            pnt.setColor(Color.BLACK);
            pnt.setStrokeWidth(18);
            pnt.setStrokeCap(Paint.Cap.ROUND);

            float baseX= getRootView().getWidth()/10;//this
            float startY = getRootView().getBaseline()+16;//15.0
            float finishY = sadariContainer.getBottom()-355;//875.0
            int i=0,j=0;
            Log.d("tag","***Sadari*** baseX: "+baseX+", startY: "+startY+", finishY: "+finishY);

            canvas.drawLine(baseX, startY, baseX, finishY, pnt);
            canvas.drawLine(baseX*3, startY, baseX*3, finishY, pnt);
            canvas.drawLine(baseX*5, startY, baseX*5, finishY, pnt);
            canvas.drawLine(baseX*7, startY, baseX*7, finishY, pnt);
            canvas.drawLine(baseX*9, startY, baseX*9, finishY, pnt);

            for (i = 0; i < 4; i++) {
                for (j = 0; j < 8; j++) {
                    if (j == 0)
                        sadariarr[i][j] = startY +5+ randomS.nextInt(210);
                    else if (j % 2 == 0) {
                        do {
                            sadariarr[i][j] = startY+5 + randomS.nextInt(210);
                        } while (abs(sadariarr[i][j] - sadariarr[i][j - 1]) < 15);
                    } else
                        sadariarr[i][j] = startY +5+ randomS.nextInt(210);
                }
                startY += 215;
            }

            for(j=1;j<8;j+=2){
                for(i=0;i<4;i++){
                    canvas.drawLine(baseX*j,sadariarr[i][j-1],baseX*(j+2),sadariarr[i][j],pnt);
                }
            }
        }//onDraw
    }//MyView

    // button을 눌렀을 때 각 버튼에 맞게 이미지가 움직이도록 해주는 onclick method
    public void buttonClicked(View v){
        Log.d("tag","버튼클릭드");
        Button selected = (Button)v;
        float[][] imagearr;
        imagearr=sadariarr;
        //애니메이션에 필요한 variables
        float imageBase= sadariContainer.getRootView().getWidth()/10-5;
        float startY=sadariContainer.getRootView().getBaseline()-14;
        float finishY=sadariContainer.getBottom()-355;
        int xIdx=Integer.parseInt(selected.getText().toString());
        int curLv=0, left=0, right=0;
        float min=-1;
        boolean toLeft=false, toRight=false;
       // Log.d("tag","===animation=== imageBase: "+imageBase+", startY: "+startY+", finishY: "+finishY);

        Log.d("tag","변수 선언 후");
        //그림의 위치를 맞추기 위해 y 좌표값 수정
        for(int i=0;i<4;i++){
            for(int j=0;j<8;j++){
                if(imagearr[i][j]>40)
                imagearr[i][j]-=30;
                else if(imagearr[i][j]>10)
                    imagearr[i][j]-=5;
            }
        }
//        for(int i=0;i<8;i++){
//            Log.d("tag",imagearr[0][i]+"/ "+imagearr[1][i]+"/ "+imagearr[2][i]+"/ "+imagearr[3][i]+"/ \n");
//        }

        //애니메이션 시작
        //맨처음 세로로 이동하기
        Log.d("tag","애니메이션 시작");
        if(xIdx==1){
            makeAnimation(imageBase*(xIdx*2-1),imageBase*(xIdx*2-1),startY,imagearr[curLv][0]);
            toLeft=false;toRight=true;
        }else if(xIdx==5){
            makeAnimation(imageBase*(xIdx*2-1),imageBase*(xIdx*2-1),startY,imagearr[curLv][7]);
            toLeft=true;toRight=false;
        }else{
            if(xIdx==2) {left=1;right=2;}
            else if(xIdx==3) {left=3;right=4;}
            else {left=5;right=6;}

            if(imagearr[curLv][left]<imagearr[curLv][right]) {
                min=imagearr[curLv][left];
                toLeft=true;toRight=false;
            }
            else if(imagearr[curLv][left]>imagearr[curLv][right]){
                min=imagearr[curLv][right];
                toLeft=false;toRight=true;
            }
            makeAnimation(imageBase*(xIdx*2-1),imageBase*(xIdx*2-1),startY,min);
        }
        Log.d("tag","while룹 직전");


        //가로이동 + 세로이동 loop
        int count=0;
        while(count<10){
            Log.d("tag","while룹 안");
            //가로로 이동하기
            if(xIdx==1){
                Log.d("tag","xIdx=1");
                left=right=0;
                toLeft=false;toRight=true;
            }
            else if(xIdx==5){
                Log.d("tag","xIdx=5");
                left=right=7;
                toLeft=true;toRight=false;
            }
            else{
                Log.d("tag","xIdx=2~4");
                if(xIdx==2) {left=1;right=2;}
                else if(xIdx==3) {left=3;right=4;}
                else {left=5;right=6;}
            }
            if(toLeft){
                makeAnimation(imageBase*(xIdx*2-1),imageBase*((--xIdx)*2-1),imagearr[curLv][left],imagearr[curLv][--left]);
                Log.d("tag","가로이동xIdx="+xIdx);
            }
            else if(toRight)
                makeAnimation(imageBase*(xIdx*2-1),imageBase*((++xIdx)*2-1),imagearr[curLv][right],imagearr[curLv][++right]);
            Log.d("tag","가로로가기 끝");

            //세로로 내려가기 -> 가로이동에서 왼쪽으로 이동한 경우(이미지는 right에 있음)
            if(toLeft) {
                Log.d("tag","세로이동: toLeft");
                //left와 right의 index를 현재 위치에 맞게 조정해준다.
                if (left == 0) left = right = 0;
                else if(left==6) {left = 5;right = 6;}
                else {
                    int temp = right - 3;
                    right = left;
                    left = temp;
                }

                if (imagearr[curLv][left] > imagearr[curLv][right]) {//left와 right를 비교한 후, 현재level의 right값이 작으면 현재level의 left로 세로이동한다.
                    makeAnimation(imageBase * (xIdx * 2 - 1), imageBase * ((xIdx) * 2 - 1), imagearr[curLv][right], imagearr[curLv][left]);
                    Log.d("tag","세로이동: toLeft->curlv left로 이동");
                    toLeft = true; toRight = false;
                } else if (imagearr[curLv][left] < imagearr[curLv][right]) {//현재level의 left값이 작으면, 다음level의 left와 right를 비교하여 작은 값으로 이동한다.
                    if(curLv+1==4){//마지막 세로이동인 경우 loop을 빠져나옴.
                        makeAnimation(imageBase * (xIdx * 2 - 1),imageBase * (xIdx * 2 - 1),imagearr[curLv][right],finishY);
                        Log.d("tag","세로오른쪽이동: break");
                        break;
                    }
                    if (imagearr[++curLv][left] < imagearr[curLv][right]) {
                        min = imagearr[curLv][left];
                        toLeft = true; toRight = false;
                    } else {
                        min = imagearr[curLv][right];
                        toLeft = false;toRight = true;
                    }
                    makeAnimation(imageBase * (xIdx * 2 - 1), imageBase * ((xIdx) * 2 - 1), imagearr[curLv - 1][right],min);
                    Log.d("tag","세로이동: toLeft->curlv+1 min으로 이동");
                }else{
                    if(curLv==3){
                        makeAnimation(imageBase * (xIdx * 2 - 1),imageBase * (xIdx * 2 - 1),imagearr[curLv][left],finishY);
                        break;
                    }
                    makeAnimation(imageBase * (xIdx * 2 - 1), imageBase * ((xIdx) * 2 - 1), imagearr[curLv][0],imagearr[++curLv][0]);
                    Log.d("tag","세로이동: toLeft->curlv+1 0으로 이동");
                }
            }
            //세로로 내려가기 -> 가로이동에서 오른쪽으로 이동한 경우(이미지는 left에 있음)
            else{//toRight
                //left와 right의 index값을 현재 위치에 맞게 조정해준다.
                Log.d("tag","세로이동: toRight");
                if(right==7) left=right=7;
                else if(left==0){left=1;right=2;}
                else{
                    int temp=left+3;
                    left=right;
                    right=temp;
                }
                if(imagearr[curLv][left]<imagearr[curLv][right]) {//left와 right를 비교한 후, 현재level의 left값이 작으면 현재level의 right로 세로이동한다.
                    makeAnimation(imageBase*(xIdx*2-1),imageBase*(xIdx*2-1),imagearr[curLv][left],imagearr[curLv][right]);
                    Log.d("tag","세로이동: toRight->curlv right로 이동");
                    toLeft = false;toRight = true;
                }else if(imagearr[curLv][left]>imagearr[curLv][right]){//현재level의 right값이 작으면, 다음level의 left와 right를 비교하여 작은 값으로 이동한다.
                    if(curLv+1==4){//마지막 세로이동인 경우 loop을 빠져나옴.
                        makeAnimation(imageBase * (xIdx * 2 - 1),imageBase * (xIdx * 2 - 1),imagearr[curLv][left],finishY);
                        Log.d("tag","세로오른쪽이동: break");
                        break;
                    }
                    if(imagearr[++curLv][left]<imagearr[curLv][right]){
                        min = imagearr[curLv][left];
                        toLeft = true; toRight = false;
                    } else {
                        min = imagearr[curLv][right];
                        toLeft = false;toRight = true;
                    }
                    makeAnimation(imageBase * (xIdx * 2 - 1), imageBase * ((xIdx) * 2 - 1), imagearr[curLv - 1][left],min);
                    Log.d("tag","세로이동: toRight->curlv+1 min으로 이동");
                }else{
                    if(curLv==3){
                        makeAnimation(imageBase * (xIdx * 2 - 1),imageBase * (xIdx * 2 - 1),imagearr[curLv][right],finishY);
                        break;
                    }
                    makeAnimation(imageBase * (xIdx * 2 - 1), imageBase * ((xIdx) * 2 - 1), imagearr[curLv][7],imagearr[++curLv][7]);
                    Log.d("tag","세로이동: toRight-> curlv+1 7으로 이동");
                }
            }
            count++;
        }//while loop


        //int end=tAnim.size();
        //Log.d("tag","tAnim.size()"+end);
        //int k=0;


        TranslateAnimation temp;
//        temp=tAnim.get(0);
//        temp.setDuration(500);
//        temp.setFillAfter(true);
//        Image.startAnimation(temp);
//        Image.bringToFront();
        AnimationSet aniSet=new AnimationSet(true);
        for(int i=0;i<tAnim.size();i++){
//            temp=tAnim.get(i);
//            temp.setDuration(500);
//            temp.setFillAfter(true);
//            Image.startAnimation(temp);
//            Image.bringToFront();
            aniSet.addAnimation(tAnim.get(i));
            aniSet.setFillAfter(true);
//아아아.... 왜.... 이미지가 안보이냐구~!~!
            Image.bringToFront();
        }
        Image.startAnimation(aniSet);

//        while(k<7){
//            temp=tAnim.get(end);
//            temp.setFillAfter(true);
//            Image.startAnimation(temp);
//            Image.bringToFront();
//            end--;
//            k++;
//        }



        for(int i=0;i<4;i++){
            for(int j=0;j<8;j++){
                if(imagearr[i][j]>40)
                    imagearr[i][j]+=30;
                else if(imagearr[i][j]>10)
                    imagearr[i][j]+=5;
            }
        }

    }



    public void makeAnimation(float fX,float tX, float fY, float tY) {
        Log.d("tag","makeAnimation:"+fX+", "+tX+", "+fY+", "+tY);
        //TranslateAnimation ani=new TranslateAnimation(fX,tX,fY,tY);
        //ani.setDuration(500);
        //ani.setFillAfter(true);
        //Image.startAnimation(ani);
       // Image.bringToFront();

        tAnim.add(new TranslateAnimation(fX,tX,fY,tY));



//        ani.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//                // TODO Auto-generated method stub
//            }
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//                // TODO Auto-generated method stub
//            }
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//            }
//        });
    }
}


