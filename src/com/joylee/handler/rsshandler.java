package com.joylee.handler;

/**
 * Created by Administrator on 13-6-23.
 */

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.joylee.entity.*;

import java.util.ArrayList;
import java.util.List;


public class rsshandler extends DefaultHandler {

    public List<newsentity> newslist;
    private static  final String itemSTRING="item";
    private static  final String titleSTRING="title";
    private static final String authorSTRING="author";
    private static final String timeSTRING="time";
    private static final String urlSTRING="link";

    public newsentity newsinfo;
    private String tempString;


    public List<newsentity> getNewslist()
    {
        return newslist;
    }

    @Override
    public void startDocument() throws SAXException {
        // TODO Auto-generated method stub
        newslist=new ArrayList<newsentity>();
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
           newslist.add(newsinfo);
            newsinfo=null;
        }
        tempString=null;

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
                newsinfo.setUrl(urlSTRING);
            }

        }


    }
}
