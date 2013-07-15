package com.joylee.csdnhome;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import android.graphics.Color;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.joylee.business.NewsManager;
import com.joylee.common.NetHelper;
import com.joylee.entity.newsentity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.joylee.handler.*;

public class MainActivity extends TabActivity {


    private TabHost tabHost;

    private Intent intentcsdn;
    private Intent intentkr;

    public String whichTab;
    public RadioButton rb1;
    public RadioButton rb2;
    public int currentTab=0;
    private  RadioGroup radioGroup;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;
    private  int maxTabIndex=2;
    private  int currentView = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        rb1 = (RadioButton) findViewById(R.id.TabBlog);
        rb2 = (RadioButton) findViewById(R.id.TabNews);


        intentcsdn = new Intent(this, CsdnMainActivity.class);
        intentkr = new Intent(this, KrMainActivity.class);


        radioGroup = (RadioGroup) findViewById(R.id.main_tab);
        radioGroup.setOnCheckedChangeListener(checkedChangeListener);

        InitialTab();


        gestureDetector = new GestureDetector(new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    return true;
                }
                return false;
            }
        };

    }

    private void InitialTab() {
        tabHost = getTabHost();
        tabHost.addTab(buildTabSpec("csdn", R.drawable.ic_launcher, R.drawable.ic_launcher, intentcsdn));
        tabHost.addTab(buildTabSpec("36kr", R.drawable.ic_launcher, R.drawable.ic_launcher, intentkr));
    }


    private void InitialSelectedTab() {
        if (currentTab==0)
        {
            rb1.setChecked(true);
           rb2.setChecked(false);
        }
        if (currentTab==1)
        {
            rb1.setChecked(false);
            rb2.setChecked(true);
        }
    }


    private RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {

        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.TabBlog:
                    setCurrentTabWithAnim(currentTab, 1, "csdn");
                    currentTab=0;
                    break;
                case R.id.TabNews:
                    setCurrentTabWithAnim(currentTab, 0, "36kr");
                    currentTab=1;
                    break;
                default:
                    break;
            }

            clearradio(group);
            findViewById(checkedId).setBackgroundColor(Color.parseColor("#3f3f44"));
        }
    };

    /**
     * 公用初始化Tab
     */
    private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
                                         final Intent content) {
        return tabHost
                .newTabSpec(tag)
                .setIndicator(getString(resLabel),
                        getResources().getDrawable(resIcon))
                .setContent(content);
    }


    //清除单选
    private void clearradio(RadioGroup  rp)
    {
        for(int i=0;i<rp.getChildCount();i++)
        {
            rp.getChildAt(i).setBackgroundColor(Color.parseColor("#000000"));
        }
    }


    private void setCurrentTabWithAnim(int now,int next,String tag)
    {
    //这个方法是关键，用来判断动画滑动的方向
        if(now > next)
        {
            tabHost.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
            tabHost.setCurrentTabByTag(tag);
            tabHost.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
        }
        else
        {
            tabHost.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
            tabHost.setCurrentTabByTag(tag);
            tabHost.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
        }
    }




    // 左右滑动刚好页面也有滑动效果
    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            TabHost tabHost = getTabHost();
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.i("test", "right");
                    if (currentView == maxTabIndex) {
                        currentView = 0;
                    } else {
                        currentView++;
                    }
                    setCurrentTabWithAnim(currentTab, currentView, "csdn");
                    currentTab=currentView;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.i("test", "left");
                    if (currentView == 0) {
                        currentView = maxTabIndex;
                    } else {
                        currentView--;
                    }
                    setCurrentTabWithAnim(currentTab,currentView, "36kr");
                    currentTab=currentView;
                }
            } catch (Exception e) {
            }
            return false;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            event.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      //  getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}
