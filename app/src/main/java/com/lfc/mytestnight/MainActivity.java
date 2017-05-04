package com.lfc.mytestnight;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lfc.mytestnight.widget.ConstantUtil;
import com.lfc.mytestnight.widget.PreferenceUtil;

/*
    ··1  style 文件  values 和 values-v21中的
    ··2   colors.xml文件 和  values-v21文件夹
    ··3   Application 类的 重写
   * */

public class MainActivity extends AppCompatActivity {

    private LinearLayout activityMain;
    private Button btnDayNightSwitch;
    private ImageView imgNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        activityMain = (LinearLayout) findViewById(R.id.activity_main);
        btnDayNightSwitch = (Button) findViewById(R.id.btn_day_night_switch);
        btnDayNightSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switchNightMode();
            }
        });
        boolean isNight = PreferenceUtil.getBoolean(ConstantUtil.SWITCH_MODE_KEY, false);
        String str_note = "";
        if (isNight) {
            // 日间模式
            str_note = "巴拉拉能量，呜呼啦呼，巴扎黑！暗！";
            
        } else {
            // 夜间模式

            str_note = "巴拉拉能量，呜呼啦呼，巴扎黑！光！";

        }
        btnDayNightSwitch.setText(str_note);
        imgNext = (ImageView) findViewById(R.id.img_next);
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MyTest2.class));

            }
        });
    }

    /**
     * 日夜间模式切换
     */
    private void switchNightMode() {
        boolean isNight = PreferenceUtil.getBoolean(ConstantUtil.SWITCH_MODE_KEY, false);
        Intent intent = new Intent(MainActivity.this, DayNightChang.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


        if (isNight) {
            // 日间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            PreferenceUtil.putBoolean(ConstantUtil.SWITCH_MODE_KEY, false);

        } else {
            // 夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            PreferenceUtil.putBoolean(ConstantUtil.SWITCH_MODE_KEY, true);

        }

        recreate();
//        btnDayNightSwitch.setText(str_note);
    }

    /**
     * */
    private final String KEY_MARIO_CACHE_THEME_TAG = "MarioCache_themeTag";


    protected int getThemeTag() {

        SharedPreferences preferences = getSharedPreferences("MarioCache", Context.MODE_PRIVATE);
        return preferences.getInt(KEY_MARIO_CACHE_THEME_TAG, 1);
    }

}
