package com.joylee.handler;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Xml;
import com.joylee.business.NewsManager;
import com.joylee.entity.Emuns;
import com.joylee.entity.newsentity;
import org.xmlpull.v1.XmlPullParser;

/**
 * Created by lih on 13-7-16.
 */
public class krhandler {

    private Context applicationContext;

    public krhandler(Context context)
    {
        applicationContext=context;
    }

    public void Insert(String url) throws Exception {

        URL url2 = new URL(url);

        URLConnection con = url2.openConnection();
        con.connect();
        InputStream stream = con.getInputStream();
        newsentity entity = null;

        // 获得XmlPullParser解析器
        XmlPullParser xmlParser = Xml.newPullParser();

        xmlParser.setInput(stream, "UTF-8");

        // 获得解析到的事件类别，这里有开始文档，结束文档，开始标签，结束标签，文本等等事件。
        int evtType = xmlParser.getEventType();

        // 一直循环，直到文档结束
        while (evtType != XmlPullParser.END_DOCUMENT) {
            String tag = xmlParser.getName();
            switch (evtType) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    if (tag.equalsIgnoreCase("item")) {
                        entity = new newsentity();
                    } else if (tag.equalsIgnoreCase("title")) {
                        entity.setTitle(xmlParser.nextText().trim());
                    } else if (tag.equalsIgnoreCase("author")) {
                        entity.setAnthor(xmlParser.nextText().trim());
                    } else if (tag.equalsIgnoreCase("link")) {
                        entity.setUrl(xmlParser.nextText().trim());
                    } else if (tag.equalsIgnoreCase("pubdate"))
                        entity.setNewsDatetime(xmlParser.nextText().trim());
            break;
            case XmlPullParser.END_TAG:
                if (tag.equalsIgnoreCase("item")) {
                    String valuestring = entity.getUrl();
                    String[] str = valuestring.split("/");
                    String laststr = str[str.length - 1];
                    String[] ids = laststr.split(".");
                    if (ids.length <= 0) {
                        entity.setNewsid(laststr);
                    } else {
                        entity.setNewsid(ids[0]);
                    }
                    entity.setSource(String.valueOf(Emuns.newssource.kr.value()));
                    NewsManager manager=new NewsManager(applicationContext);
                    if(manager.GetInfoByID(entity.getNewsid(),String.valueOf(Emuns.newssource.csdn.value()))==null)
                    {
                        manager.InsertNews(entity);
                    }
                    entity = null;
                }
                break;
        }
        // 如果xml没有结束，则导航到下一个节点
        evtType = xmlParser.next();
    }

    stream.close();
}

}
