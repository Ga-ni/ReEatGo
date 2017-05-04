package org.androidtown.reeatgo;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class ladder extends AppCompatActivity {
    ImageView face;
    TranslateAnimation ani1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ladder);
        face=(ImageView)findViewById(R.id.face);
        //ani1= AnimationUtils.loadAnimation(this,R.anim.translate);

        face.setAnimation(ani1);
        face.startAnimation(ani1);

    }
}
