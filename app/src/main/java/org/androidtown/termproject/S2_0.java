package org.androidtown.termproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class S2_0 extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_s2_0, container, false);
        Button button = (Button) rootView.findViewById(R.id.s2_00); //밥 button click
        button.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                Selection activity = (Selection) getActivity();
                activity.onFragmentChanged(201); //매운 or 안매운 액티비티 이동
            }

        });
        Button button1 = (Button) rootView.findViewById(R.id.s2_01); //면 button
        button1.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                Selection activity = (Selection) getActivity();
                activity.onFragmentChanged(202);  //매운 안매운 액티비티 이동
            }

        });
        Button button2 = (Button)rootView.findViewById(R.id.back);  //뒤로가기
        button2.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                Selection activity = (Selection) getActivity();
                activity.onFragmentChanged(100);
            }

        });
        return rootView;
    }
}