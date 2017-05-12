package org.androidtown.reeatgo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import java.util.Random;
import static java.lang.Math.abs;

public class ladder extends AppCompatActivity {
    float sadariarr[][] = new float[4][8];
    Random randomS= new Random();

    MyView mv;
    FrameLayout sadariContainer;
    ImageView Image;
    Animation alpha;

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
            float finishY = getRootView().getBottom()-355;//875.0
            int i=0,j=0;

            canvas.drawLine(baseX, startY, baseX, finishY, pnt);
            canvas.drawLine(baseX*3, startY, baseX*3, finishY, pnt);
            canvas.drawLine(baseX*5, startY, baseX*5, finishY, pnt);
            canvas.drawLine(baseX*7, startY, baseX*7, finishY, pnt);
            canvas.drawLine(baseX*9, startY, baseX*9, finishY, pnt);

            for (i = 0; i < 4; i++) {
                for (j = 0; j < 8; j++) {
                    if (j == 0)
                        sadariarr[i][j] = startY + randomS.nextInt(215);
                    else if (j % 2 == 0) {
                        do {
                            sadariarr[i][j] = startY + randomS.nextInt(215);
                        } while (abs(sadariarr[i][j] - sadariarr[i][j - 1]) < 15);
                    } else
                        sadariarr[i][j] = startY + randomS.nextInt(215);
                }
                startY += 215;
            }

            for(j=1;j<8;j+=2){
                for(i=0;i<4;i++){
                    canvas.drawLine(baseX*j,sadariarr[i][j-1],baseX*(j+2),sadariarr[i][j],pnt);
                }
            }
//            if(Vindex==1){
//                Log.d("tag","***imageLocation: "+simpson.getGlobalVisibleRect(r));
//                Log.d("tag","\n\nleft: "+r.left+"\nright: "+r.right+"\ntop: "+r.top+"\nbottom: "+r.bottom);
//                //TranslateAnimation ani = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,baseX,Animation.RELATIVE_TO_PARENT,sadariarr[0][0],Animation.RELATIVE_TO_PARENT,baseX*3,Animation.RELATIVE_TO_PARENT,sadariarr[0][1]);
//                TranslateAnimation ani= new TranslateAnimation(baseX,baseX*3,sadariarr[0][0],sadariarr[0][1]);
//                Log.d("tag","imageBase: "+baseX+"\nimageBase*3: "+ baseX*3+"\nStartY: "+startY+"\nFinishY: "+finishY);
//                Log.d("tag","imageBase: "+baseX+"\nimageBase*3: "+baseX*3+"\nStartY(arr): "+sadariarr[0][0]+"\nFinishY(arr): "+sadariarr[0][1]);
//
//                Log.d("tag","***imageLocation: "+simpson.getGlobalVisibleRect(r));
//                Log.d("tag","\n\nleft: "+r.left+"\nright: "+r.right+"\ntop: "+r.top+"\nbottom: "+r.bottom);
//                ani.setDuration(2000);
//                ani.setFillAfter(true);
//                simpson.startAnimation(ani);
//                simpson.bringToFront();
//            }
        }//onDraw
    }//MyView

    // button을 눌렀을 때 각 버튼에 맞게 이미지가 움직이도록 해주는 onclick method
    public void buttonClicked(View v){
        Button selected = (Button)v;
        TranslateAnimation ani;
        int btnNum=Integer.parseInt(selected.getText().toString()); //선택된 버튼의 숫자
        //애니메이션에 필요한 variables
        int xIdx=btnNum, curLv=0, left=0, right=0;
        float min=-1;
        float imageBase= sadariContainer.getRootView().getWidth()/10-5;
        float startY=sadariContainer.getRootView().getBaseline();
        float finishY=sadariContainer.getRootView().getBottom()-355;
        //그림의 위치를 맞추기 위해 y 좌표값 수정
        for(int i=0;i<4;i++){
            for(int j=0;j<8;j++){
                sadariarr[i][j]-=40;
            }
        }

        //애니메이션 시작
        //세로로 내려가기
        if(btnNum==1){
            ani = new TranslateAnimation(imageBase*(xIdx*2-1),imageBase*(xIdx*2-1),startY,sadariarr[curLv][0]);
            ani.setDuration(500);
            ani.setFillAfter(true);
            Image.startAnimation(ani);
        }else if(btnNum==5){
            ani = new TranslateAnimation(imageBase*(xIdx*2-1),imageBase*(xIdx*2-1),startY,sadariarr[curLv][7]);
            ani.setDuration(500);
            ani.setFillAfter(true);
            Image.startAnimation(ani);
        }else{
            if(btnNum==2) {left=1;right=2;}
            else if(btnNum==3) {left=3;right=4;}
            else {left=5;right=6;}

            if(sadariarr[curLv][left]<sadariarr[curLv][right]) {min=sadariarr[curLv][left];}
            else {min=sadariarr[curLv][right];}

            ani = new TranslateAnimation(imageBase*(xIdx*2-1),imageBase*(xIdx*2-1),startY,min);
            ani.setDuration(500);
            ani.setFillAfter(true);
            Image.startAnimation(ani);
        }




        //y좌표값 되돌려 놓기
        for(int i=0;i<4;i++){
            for(int j=0;j<8;j++){
                sadariarr[i][j]+=40;
            }
        }
    }
}


