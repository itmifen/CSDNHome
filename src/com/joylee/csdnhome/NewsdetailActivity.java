package com.joylee.csdnhome;


import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joylee.common.HtmlHelper;
import com.joylee.entity.newsentity;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 13-6-15.
 */
public class NewsdetailActivity extends Activity {
    private TextView newstitle;
    private WebView detail;
    private MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsdetail);

        myHandler = new MyHandler();
        MyThread m = new MyThread();
        new Thread(m).start();


    }


    class MyHandler extends Handler {
        public MyHandler() {
        }

        public MyHandler(Looper L) {
            super(L);
        }

        // 子类必须重写此方法,接受数据
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Log.d("MyHandler", "handleMessage......");
            super.handleMessage(msg);
            // 此处可以更新UI


            newstitle=(TextView)findViewById(R.id.detail_title);
            detail=(WebView)findViewById(R.id.detail_detail);

            Bundle bundle=NewsdetailActivity.this.getIntent().getExtras();
            newstitle.setText(bundle.getString("title"));
            String url=bundle.getString("url");
            String newsinfo= HtmlHelper.GetCsdnDetail(url);
            detail.loadDataWithBaseURL("", newsinfo, "text/html", "utf-8", "");

            findViewById(R.id.detail_progressBar1).setVisibility(View.INVISIBLE);
        }
    }


    class MyThread implements Runnable {
        public void run() {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Message msg = new Message();

            NewsdetailActivity.this.myHandler.sendMessage(msg); // 向Handler发送消息,更新UI
        }
    }

}










