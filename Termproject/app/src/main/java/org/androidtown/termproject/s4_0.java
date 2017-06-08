package org.androidtown.termproject;
import android.os.Bundle;

import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;

import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;


public class s4_0 extends  Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_s4_0, container, false);



        ImageButton button = (ImageButton) rootView.findViewById(R.id.s3_00); //한식

        button.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Selection activity = (Selection) getActivity();

                activity.getIndex(401); //익은 고기 매운거 출력

            }

        });



        ImageButton button1 = (ImageButton) rootView.findViewById(R.id.s3_01); //중식



        button1.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Selection activity = (Selection) getActivity();

                activity.getIndex(402); // 익은 고기 안매운거 출력

            }

        });



        ImageButton button2 = (ImageButton) rootView.findViewById(R.id.s3_02); //일식



        button2.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Selection activity = (Selection) getActivity();

                activity.getIndex(403);

            }

        });
        ImageButton button3 = (ImageButton) rootView.findViewById(R.id.s3_03); //양식
        button3.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Selection activity = (Selection) getActivity();

                activity.getIndex(404);

            }

        });
        ImageButton button4 = (ImageButton) rootView.findViewById(R.id.s3_04); //분식
        button4.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Selection activity = (Selection) getActivity();

                activity.getIndex(405);

            }

        });
        ImageButton button5 = (ImageButton) rootView.findViewById(R.id.s3_05); //기타
        button5.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Selection activity = (Selection) getActivity();

                activity.getIndex(406);

            }

        });

        ImageButton button6 = (ImageButton) rootView.findViewById(R.id.back); // 뒤로가기



        button6.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Selection activity = (Selection) getActivity();

                activity.onFragmentChanged(201);

            }

        });


        return rootView;

    }

}
