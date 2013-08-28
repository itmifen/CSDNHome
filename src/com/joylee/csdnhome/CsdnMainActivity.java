package com.joylee.csdnhome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.joylee.business.NewsManager;
import com.joylee.common.NetHelper;
import com.joylee.entity.Emuns;
import com.joylee.entity.newsentity;
import com.joylee.handler.rsshandler;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Administrator on 13-7-6.
 */
public class CsdnMainActivity extends Activity {

    private JoyListView newslist;
    private MyHandler myHandler;
    private  List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    private List<newsentity> channlist = new ArrayList<newsentity>();
    private boolean isupdate;
    private int pageindex=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.csdnmain);

        newslist = (JoyListView) findViewById(R.id.listView1);

        newslist.setonRefreshListener(new JoyListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myHandler = new MyHandler(true);
                MyThread m = new MyThread();
                new Thread(m).start();
            }
        });


        NetHelper netHelper = new NetHelper();
        // Toast.makeText(MainActivity.this, String.valueOf(netHelper.isNetworkConnected(this)),200);
        if (netHelper.isNetworkConnected(this)) {
            myHandler = new MyHandler();
            MyThread m = new MyThread();
            new Thread(m).start();
        } else {
            Toast.makeText(getApplicationContext(), "网络故障，请检查您的网络环境", 1000).show();

        }
    }


    private List<Map<String, String>> getData() {
        list = new ArrayList<Map<String, String>>();
        channlist = new ArrayList<newsentity>();
            NewsManager manager = new NewsManager(getApplicationContext());
            channlist = manager.GetList(String.valueOf(Emuns.newssource.csdn.value()),0,20);
            if (channlist.size() <= 0) {
                insertdata();
            }
        else {
                //是否往数据库插数据，下拉更新的时候有用
                if(isupdate==true)
                {
                    insertdata();
                    isupdate=false;
                }
            }


        for (int i = 0; i < channlist.size(); i++) {
            Map<String, String> map = new HashMap<String, String>();
            newsentity newsinfo = (newsentity) channlist.get(i);
            map.put("title", newsinfo.getTitle());
            map.put("anthor", newsinfo.getAnthor());
            map.put("newsdatetime", newsinfo.getNewsDatetime());
            map.put("url", newsinfo.getUrl());
            list.add(map);
        }
        return list;
    }


    private  void insertdata()
    {
        NewsManager manager = new NewsManager(getApplicationContext());
        try {
        URL myURL = new URL("http://www.csdn.net/article/rss_lastnews");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLReader reader = parser.getXMLReader();
        rsshandler handler = new rsshandler(getApplicationContext());
        reader.setContentHandler(handler);
        reader.parse(new InputSource(myURL.openStream()));
        channlist = manager.GetList(String.valueOf(Emuns.newssource.csdn.value()));
        }
        catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.v("error", e.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    class MyHandler extends Handler {
        public MyHandler() {
        }

        public MyHandler(boolean update) {
            if(update==true)
            {
                isupdate=true;
            }
            else {
                isupdate=false;
            }
        }

        public MyHandler(Looper L) {
            super(L);
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Log.d("MyHandler", "handleMessage......");
            super.handleMessage(msg);

            findViewById(R.id.main_progressBar1).setVisibility(View.INVISIBLE);
            SimpleAdapter adapter = null;
            if (getData().size() != 0) {
                adapter = new SimpleAdapter(getApplicationContext(), getData(),
                        R.layout.newslistcontent, new String[]{"title","anthor", "newsdatetime", "url"}, new int[]{
                        R.id.titletext, R.id.datetimetext,R.id.anthortext});
                newslist.setAdapter(adapter);
            } else {
                Toast.makeText(getApplicationContext(), "暂无数据", Toast.LENGTH_LONG).show();
            }

            newslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    String url = getData().get(arg2).get("url").toString();
                    String title = getData().get(arg2).get("title").toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("url", url);
                    bundle.putString("title", title);
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), NewsdetailActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            });
            newslist.onRefreshComplete();
        }


    }

    class MyThread implements Runnable {
        public void run() {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Log.d("thread.......", "mThread........");
            Message msg = new Message();
            CsdnMainActivity.this.myHandler.sendMessage(msg);

        }
    }


}
