package org.androidtown.termproject;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class s1 extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.s1, container, false);
        Button button = (Button) rootView.findViewById(R.id.s1_0);  //고기x 선택시
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Selection activity = (Selection) getActivity();
                activity.onFragmentChanged(101); //면or밥 액티비티 이동
            }
        });

        Button button1 = (Button) rootView.findViewById(R.id.s1_1); //고기O 선택시
        button1.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                Selection activity = (Selection) getActivity();
                activity.onFragmentChanged(102); //안익은 or 익은 액티비티 이동
            }

        });
        return rootView;
    }

}