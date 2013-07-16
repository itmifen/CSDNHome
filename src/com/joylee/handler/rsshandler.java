package com.joylee.handler;

/**
 * Created by Administrator on 13-6-23.
 */

import android.content.Context;
import com.joylee.common.StringUtil;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.joylee.entity.*;
import com.joylee.business.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class rsshandler extends DefaultHandler {

    private static  final String itemSTRING="item";
    private static  final String titleSTRING="title";
    private static final String authorSTRING="author";
    private static final String timeSTRING="time";
    private static final String urlSTRING="link";

    public newsentity newsinfo;
    private String tempString;

    private Context applicationContext;

    public rsshandler(Context context) {
        applicationContext = context;
    }





    @Override
    public void startDocument() throws SAXException {
        // TODO Auto-generated method stub

    }

    @Override
    public void endDocument() throws SAXException {
        // TODO Auto-generated method stub

    }


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        // TODO Auto-generated method stub
        if(itemSTRING.equals(localName)){
            newsinfo=new newsentity();
           // newsinfo.setTitle(new String(attributes.getValue(itemSTRING)));
        }
        tempString=localName;

    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        // TODO Auto-generated method stub
        if(itemSTRING.equals(localName)&&newsinfo!=null)
        {
            NewsManager manager=new NewsManager(applicationContext);
            if(manager.GetInfoByID(newsinfo.getNewsid(),String.valueOf(Emuns.newssource.csdn.value()))==null)
            {
                manager.InsertNews(newsinfo);
            }
            newsinfo=null;
        }
        tempString="";

    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        if(newsinfo!=null)
        {
            String valuestring=new String(ch,start,length);
            if(tempString.equals(titleSTRING))
            {
                newsinfo.setTitle(valuestring);
            }
            if(tempString.equals(urlSTRING))
            {
                newsinfo.setUrl(valuestring);
                String[] str=valuestring.split("/");
                String laststr=str[str.length-1];
                String[] ids=laststr.split("-");
                if(ids.length<=0)
                {
                    newsinfo.setNewsid(laststr);
                }
                else
                {
                    newsinfo.setNewsid(ids[0]);
                }
            }
            if(tempString.equals(authorSTRING))
            {
                newsinfo.setAnthor(valuestring);
            }
            if(tempString.equals(timeSTRING))
            {
                valuestring=StringUtil.GetNowTime();
                newsinfo.setNewsDatetime(valuestring);
            }
            newsinfo.setSource(String.valueOf(Emuns.newssource.csdn.value()));
        }


    }
}
