package com.brioal.whellviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.brioal.whellviewtest.view.Wheel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Wheel wheel1;
    private Wheel wheel2;
    private Wheel wheel3;


    private List<String> mText1;
    private List<String> mText2;
    private List<String> mText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();


    }

    public void initData() {
        mText1 = new ArrayList<>();

        mText1.add("1");
        mText1.add("上午");
        mText1.add("下午");


        mText2 = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            mText2.add(i + "");
        }

        mText3 = new ArrayList<>();
        for (int i = 1; i <= 59; i++) {
            mText3.add(i + "");
        }
    }

    public void initView() {
        wheel1 = (Wheel) findViewById(R.id.main_whell1);
        wheel2 = (Wheel) findViewById(R.id.main_whell2);
        wheel3 = (Wheel) findViewById(R.id.main_whell3);

        wheel1.setmShowIndicator(true);
        wheel1.setmTexts(mText1);
        wheel1.setmShowCount(2);

        wheel2.setmTip("时");
        wheel2.setmTexts(mText2);

        wheel3.setmTip("分");
        wheel3.setmTexts(mText3);
    }
}
