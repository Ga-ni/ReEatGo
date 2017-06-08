package org.androidtown.termproject;

import android.os.Bundle;

import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;

import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;


/**

 * Created by hong on 2017-05-04.

 */



public class S3_2 extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_s3_0, container, false);



        ImageButton button = (ImageButton) rootView.findViewById(R.id.s2_10); // 매운 button click

        button.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Selection activity = (Selection) getActivity();

                activity.onFragmentChanged(305); // 안익은 고기 매운거 출력

            }

        });



        ImageButton button1 = (ImageButton) rootView.findViewById(R.id.s2_11); //안매운button



        button1.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Selection activity = (Selection) getActivity();

                activity.onFragmentChanged(306); //안익은 고기 안매운거 출력

            }

        });



        ImageButton button2 = (ImageButton) rootView.findViewById(R.id.back); //뒤로가기



        button2.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Selection activity = (Selection) getActivity();

                activity.onFragmentChanged(102);

            }

        });



        return rootView;

    }

}