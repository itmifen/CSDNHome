package com.joylee.csdnhome;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.joylee.common.NetHelper;
import com.joylee.entity.newsentity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import com.joylee.handler.*;

public class MainActivity extends Activity {

    private ListView newslist;
    private MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


                        SimpleDateFormat sdf=new SimpleDateFormat("ddd MMM dd HH:mm:ss PDT yyyy", Locale.US);
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddkkmmss");
                try {
                String newdate=sdf.format(sdf2.parse("Fri, 05 Jul 2013 14:07:40"));
               Toast.makeText(getApplicationContext(),newdate,200).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.toString(),2000).show();;
                }


        NetHelper netHelper=new NetHelper();
       // Toast.makeText(MainActivity.this, String.valueOf(netHelper.isNetworkConnected(this)),200);
        if(netHelper.isNetworkConnected(this))
        {
        myHandler = new MyHandler();
        MyThread m = new MyThread();
        new Thread(m).start();
        }
        else {
            Toast.makeText(MainActivity.this,"缃戠粶鏃犳硶杩炴帴锛岃妫�煡缃戠粶",1000).show();

        }

    }


    private List<Map<String, String>> getData() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        //InputStream stream = getResources().openRawResource(R.raw.rss_lastnews);
//        InputStream stream = null;
//        try {
//            URL myURL = new URL("http://www.csdn.net/article/rss_lastnews");
//            // 鎵撳紑URL閾炬�?//            URLConnection ucon = myURL.openConnection();
//            // 浣跨敤InputStream锛屼粠URLConnection璇诲彇鏁版嵁
//            stream = ucon.getInputStream();
//        } catch (Exception e) {
//
//            return null;
//
//        }
//        List<newsentity> channlist = com.joylee.common.DomParserHelper.getChannelList(stream);

        List<newsentity> channlist=new ArrayList<newsentity>();
        try {
//杩欓噷鎴戜滑瀹炵幇浜嗘湰鍦拌В鏋愶紝鎵�互娉ㄦ�?��嗚繖涓彇缃戠粶鏁版嵁鐨勩�
            URL myURL = new URL("http://www.csdn.net/article/rss_lastnews");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            rsshandler  handler = new rsshandler(getApplicationContext());
            reader.setContentHandler(handler);
           // InputSource is = new InputSource(this.getClassLoader().getResourceAsStream("1.xml"));//鍙栧緱鏈�?��xml鏂囦�?           // InputStreamReader isr = new InputStreamReader(myURL.openStream(), "UTF-8");
          //  InputSource is = new InputSource(isr);
            //parser.parse(is,handler);
            reader.parse(new InputSource(myURL.openStream()));
            channlist=handler.getNewslist();
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.v("error",e.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for (int i = 0; i < channlist.size(); i++) {
            Map<String, String> map = new HashMap<String, String>();
            newsentity newsinfo = (newsentity) channlist.get(i);
            //map.put("id", chann.getId());
            //map.put("url", chann.getUrl());
            map.put("title", newsinfo.getTitle());
            map.put("newsdatetime", newsinfo.getNewsDatetime());
            map.put("url", newsinfo.getUrl());
            list.add(map);
        }

        return list;
    }


    class MyHandler extends Handler {
        public MyHandler() {
        }

        public MyHandler(Looper L) {
            super(L);
        }

        // 瀛愮被蹇呴�?閲嶅啓姝ゆ柟娉�鎺ュ彈鏁版嵁
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Log.d("MyHandler", "handleMessage......");
            super.handleMessage(msg);
            // 姝ゅ鍙互鏇存柊UI
            findViewById(R.id.main_progressBar1).setVisibility(View.INVISIBLE);

            newslist = (ListView) findViewById(R.id.listView1);
            SimpleAdapter adapter=null;
            if(getData().size()!=0)
            {
             adapter = new SimpleAdapter(MainActivity.this, getData(),
                    R.layout.newslistcontent, new String[]{"title", "newsdatetime", "url"}, new int[]{
                    R.id.titletext, R.id.datetimetext});
            newslist.setAdapter(adapter);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"鏆傛棤鏁版嵁",Toast.LENGTH_LONG).show();
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
                    intent.setClass(MainActivity.this, NewsdetailActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            });


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

            Log.d("thread.......", "mThread........");
            Message msg = new Message();
            MainActivity.this.myHandler.sendMessage(msg); // 鍚慔andler鍙戦�娑堟伅,鏇存柊UI

        }
    }


}
