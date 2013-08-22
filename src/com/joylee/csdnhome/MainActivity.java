package com.joylee.csdnhome;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import android.app.*;
import android.content.*;
import android.graphics.Color;
import android.util.Xml;
import android.view.*;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.agimind.widget.SlideHolder;
import com.joylee.business.NewsManager;
import com.joylee.common.NetHelper;
import com.joylee.entity.Config;
import com.joylee.entity.Emuns;
import com.joylee.entity.newsentity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.joylee.handler.*;
import org.xmlpull.v1.XmlPullParser;

public class MainActivity extends TabActivity {


    private TabHost tabHost;

    private Intent intentcsdn;
    private Intent intentkr;
    private  Intent intentpic;

    public String whichTab;
    public RadioButton rb1;
    public RadioButton rb2;
    public RadioButton rb3;
    public int currentTab = 0;
    private RadioGroup radioGroup;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;
    private int maxTabIndex = 2;
    private int currentView = 0;
    private String lastdate = "";
    private ActivityReceiver activityReceiver;

    private SlideHolder slideHolder;

    private static final String action = "com.joylee.csdnhome.KrService";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        rb1 = (RadioButton) findViewById(R.id.TabBlog);
        rb2 = (RadioButton) findViewById(R.id.TabNews);
        rb3=(RadioButton)findViewById(R.id.TabPic);


        intentcsdn = new Intent(this, CsdnMainActivity.class);
        intentkr = new Intent(this, KrMainActivity.class);
        intentpic = new Intent(this, myslideActivity.class);


        radioGroup = (RadioGroup) findViewById(R.id.main_tab);
        radioGroup.setOnCheckedChangeListener(checkedChangeListener);

        slideHolder=(SlideHolder)findViewById(R.id.slideHolder);
        slideHolder.setDirection(SlideHolder.DIRECTION_RIGHT);

        InitialTab();

//        gestureDetector = new GestureDetector(new MyGestureDetector());
//        gestureListener = new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//                if (gestureDetector.onTouchEvent(event)) {
//                    return true;
//                }
//                return false;
//            }
//        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(action);
        activityReceiver = new ActivityReceiver();
        registerReceiver(activityReceiver, filter);
        Intent intent = new Intent(MainActivity.this, KrService.class);
        startService(intent);

    }

    @Override
    protected  void onStop()
    {
        showNotification();
        super.onStop();
    }

    private void InitialTab() {
        tabHost = getTabHost();
        tabHost.addTab(buildTabSpec("csdn", R.drawable.ic_launcher, R.drawable.ic_launcher, intentcsdn));
        tabHost.addTab(buildTabSpec("36kr", R.drawable.ic_launcher, R.drawable.ic_launcher, intentkr));
        tabHost.addTab(buildTabSpec("pic", R.drawable.ic_launcher, R.drawable.ic_launcher, intentpic));
    }


    private void InitialSelectedTab() {
        if (currentTab == 0) {
            rb1.setChecked(true);
            rb2.setChecked(false);
            rb3.setChecked(false);
        }
        if (currentTab == 1) {
            rb1.setChecked(false);
            rb2.setChecked(true);
            rb3.setChecked(false);
        }
        if (currentTab ==2) {
            rb1.setChecked(false);
            rb2.setChecked(false);
            rb3.setChecked(true);
        }
    }


    private RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {

        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.TabBlog:
                    setCurrentTabWithAnim(currentTab, 1, "csdn");
                    currentTab = 0;
                    break;
                case R.id.TabNews:
                    setCurrentTabWithAnim(currentTab, 0, "36kr");
                    currentTab = 1;
                    break;
                case R.id.TabPic:
                    setCurrentTabWithAnim(currentTab, 0, "pic");
                    currentTab = 2;
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
    private void clearradio(RadioGroup rp) {
        for (int i = 0; i < rp.getChildCount(); i++) {
            rp.getChildAt(i).setBackgroundColor(Color.parseColor("#000000"));
        }
    }

    /**
     * 在状态栏显示通知
     */

    private void showNotification() {

        // 创建一个NotificationManager的引用
        NotificationManager notificationManager = (NotificationManager)this.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
        // 定义Notification的各种属性

        Notification notification = new Notification(R.drawable.ic_launcher, "CSDN home", System.currentTimeMillis());
        //FLAG_AUTO_CANCEL   该通知能被状态栏的清除按钮给清除掉
        //FLAG_NO_CLEAR      该通知不能被状态栏的清除按钮给清除掉
        //FLAG_ONGOING_EVENT 通知放置在正在运
        //FLAG_INSISTENT     是否一直进行，比如音乐一直播放，知道用户响应

        notification.flags |= Notification.FLAG_ONGOING_EVENT; // 将此通知放到通知栏的"Ongoing"即"正在运行"组中
        notification.flags |= Notification.FLAG_NO_CLEAR; // 表明在点击了通知栏中的"清除通知"后，此通知不清除，经常与FLAG_ONGOING_EVENT一起使用
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        //DEFAULT_ALL     使用所有默认值，比如声音，震动，闪屏等等
        //DEFAULT_LIGHTS  使用默认闪光提示
        //DEFAULT_SOUNDS  使用默认提示声音
        //DEFAULT_VIBRATE 使用默认手机震动，需加上<uses-permission android:name="android.permission.VIBRATE" />权限
        notification.defaults = Notification.DEFAULT_LIGHTS;
        //叠加效果常量
        //notification.defaults=Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND;

        notification.ledARGB = Color.BLUE;
        notification.ledOnMS = 5000; //闪光时间，毫秒

        // 设置通知的事件消息
        CharSequence contentTitle = "督导系统标题"; // 通知栏标题
        CharSequence contentText = "督导系统内容"; // 通知栏内容
        Intent notificationIntent = new Intent(MainActivity.this, MainActivity.class); // 点击该通知后要跳转的Activity
        PendingIntent contentItent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(this, contentTitle, contentText, contentItent);
        // 把Notification传递给NotificationManager
        notificationManager.notify(0, notification);

    }

    // 在主菜单按返回键，则退出应用，在子菜单按返回键，返回主菜单，
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0) {

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }




    private void setCurrentTabWithAnim(int now, int next, String tag) {
        //这个方法是关键，用来判断动画滑动的方向
        if (now > next) {
            tabHost.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
            tabHost.setCurrentTabByTag(tag);
            tabHost.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
        } else {
            tabHost.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
            tabHost.setCurrentTabByTag(tag);
            tabHost.getCurrentView().startAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
        }
    }



    class ActivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(final Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String str = bundle.getString("isupdate");
                if (str.equals("1")) {
                    rb2.setText("kr/new");
                } else {
                    rb2.setText("kr");
                }

            }
            Log.v("service", "activity操作");
        }
    }


}
