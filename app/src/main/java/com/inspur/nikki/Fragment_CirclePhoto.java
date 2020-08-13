package com.inspur.nikki;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment_CirclePhoto extends Fragment {

    private Button vp_stand,vp_circle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.laytout_circlephoto, container, false);
        vp_circle = (Button) view.findViewById(R.id.vp_circle);
        vp_stand = (Button) view.findViewById(R.id.vp_stand);

        vp_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CircleViewPagerActivity.class);
                startActivity(intent);
            }
        });

        vp_stand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), StandardViewPagerActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


}
