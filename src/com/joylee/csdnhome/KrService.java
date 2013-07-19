package com.joylee.csdnhome;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;
import com.joylee.business.NewsManager;
import com.joylee.common.DbHelper;
import com.joylee.entity.Config;
import com.joylee.entity.Emuns;
import com.joylee.entity.newsentity;
import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by lih on 13-7-18.
 */
public class KrService extends Service {

    public KrReceiver krReceiver;
    private String lastdate = "";
    private boolean quit = false;
    private static final String action = "com.joylee.csdnhome.KrService";
    private int isUpdate = 0;



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //创建时启动
    @Override
    public void onCreate() {
        super.onCreate();

        new Thread(new Runnable() {
            @Override
            public void run() {

                try{
                URL url2 = new URL(Config.krurl);
                URLConnection con = url2.openConnection();
                con.connect();
                InputStream stream = con.getInputStream();
                // 获得XmlPullParser解析器
                XmlPullParser xmlParser = Xml.newPullParser();
                xmlParser.setInput(stream, "UTF-8");

                // 获得解析到的事件类别，这里有开始文档，结束文档，开始标签，结束标签，文本等等事件。
                int evtType = xmlParser.getEventType();
                boolean isitem = false;

                // 一直循环，直到文档结束
                while(evtType != XmlPullParser.END_DOCUMENT) {
                    boolean bool=true;
                    String tag = xmlParser.getName();
                    switch (evtType) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            if (tag.equalsIgnoreCase("pubDate")) {
                                lastdate = xmlParser.nextText().trim();
                               bool=false;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            break;
                    }
                    // 如果xml没有结束，则导航到下一个节点
                    evtType = xmlParser.next();
                    if(bool==false)
                    {
                    break;
                    }
                }
                stream.close();

            } catch (Exception e) {
                Log.v("error", e.toString());
            }
                NewsManager manager = new NewsManager(getApplicationContext());
                String str = manager.GetLastdate(String.valueOf(Emuns.newssource.kr.value()));
                if (lastdate.equals(str)) {
                    isUpdate = 0;
                } else {
                    isUpdate = 1;
                }



            krReceiver = new KrReceiver();
                        IntentFilter filter = new IntentFilter();
                        filter.addAction(action);
                        registerReceiver(krReceiver, filter);
                        Intent sendintent = new Intent();
                        sendintent.setAction(action);
                        sendBroadcast(sendintent);
                        Log.e("service", "服务启动");
            }
        }).start();

    }

    //执行的时候启动，适用于循环
    @Override
    public int onStartCommand(Intent intent, int flagid, int startid) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(krReceiver);
        this.quit = true;
    }


    public class KrReceiver extends BroadcastReceiver {

        private String lastdate;
        private static final String action = "com.joylee.csdnhome.KrService";

        @Override
        public void onReceive(final Context context, Intent intent) {

            Intent sendintent = new Intent(action);
            sendintent.putExtra("isupdate", String.valueOf(isUpdate));
            sendBroadcast(sendintent);


        }
    }


}
