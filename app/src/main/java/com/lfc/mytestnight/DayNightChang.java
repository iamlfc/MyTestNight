package com.lfc.mytestnight;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lfc.mytestnight.widget.KeyStore;


public class DayNightChang extends AppCompatActivity {

    private RelativeLayout activityDayNightChang;
    private ImageView modeChangeAnimatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_night_chang);
        initView();
        initAllDatum();
    }

    private void initView() {
        activityDayNightChang = (RelativeLayout) findViewById(R.id.activity_day_night_chang);
        modeChangeAnimatorView = (ImageView) findViewById(R.id.mode_change_animator_view);
    }

    public void initAllDatum() {
        modeChangeAnimatorView.setImageResource(getThemeTag() == -1 ? R.drawable.custom_drawable_mode_translation_turn_night_v16 : R.drawable.custom_drawable_mode_translation_turn_day_v16);
        sendEmptyMessageDelayed(KeyStore.KEY_TAG_ANIMATOR_START, 300);
    }

    private InternalHandler mHandler;

    private class InternalHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg == null)
                return;
            switch (msg.what) {
                case KeyStore.KEY_SKIP_ANIMATOR_FINISH:
                    finish();
                    break;
                case KeyStore.KEY_TAG_ANIMATOR_START:
                    startAnimator();
                    break;
                case KeyStore.KEY_TAG_ANIMATOR_STOP:
                    stopAnimator();
                    break;
                default:
                    break;
            }
        }

        /**
         *
         * */
        private void startAnimator() {
            Drawable drawable = modeChangeAnimatorView.getDrawable();
            if (drawable == null || !(drawable instanceof AnimationDrawable))
                return;
            ((AnimationDrawable) drawable).start();
            sendEmptyMessageDelayed(KeyStore.KEY_TAG_ANIMATOR_STOP, 1760);
        }

        /**
         *
         * */
        private void stopAnimator() {
            sendEmptyMessageDelayed(KeyStore.KEY_SKIP_ANIMATOR_FINISH, 360);
            switchCurrentThemeTag(); //
//            ((MyTestApp) getApplication()).notifyByThemeChanged(); //
        }
    }

    /**
     * */
    public void switchCurrentThemeTag() {
        setThemeTag(0 - getThemeTag());

    }


    /**
     * */
    private void sendEmptyMessageDelayed(int what, long delay) {
        if (mHandler == null)
            mHandler = new InternalHandler();
        mHandler.sendEmptyMessageDelayed(what, delay);
    }

    /**
     * */
    protected void setThemeTag(int tag) {
        SharedPreferences preferences = getSharedPreferences("MarioCache", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putInt(KEY_MARIO_CACHE_THEME_TAG, tag);
        edit.commit();
    }

    /**
     * */
    private final String KEY_MARIO_CACHE_THEME_TAG = "MarioCache_themeTag";

    protected int getThemeTag() {
        SharedPreferences preferences = getSharedPreferences("MarioCache", Context.MODE_PRIVATE);
        return preferences.getInt(KEY_MARIO_CACHE_THEME_TAG, 1);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            finish();     //调用双击退出函数
        }
        return false;
    }
}
