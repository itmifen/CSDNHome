package com.joylee.common;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.joylee.entity.newsentity;


public class DomParserHelper {

    public static List<newsentity> getChannelList(InputStream stream) {
        List<newsentity> list = new ArrayList<newsentity>();
       // stream=(InputStream)(stream.toString().replace("&","&amp;")); 特殊符号问题


        try {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(stream);
        Element root = document.getDocumentElement();
        NodeList items = root.getElementsByTagName("item");

        for (int i = 0; i < items.getLength(); i++) {
            newsentity newsinfo = new newsentity();
            Element item = (Element) items.item(i);
            Element titleitem = (Element) item.getElementsByTagName("title").item(0);
            if (titleitem != null) {
                //newsinfo.setUrl(item.getAttribute("title"));
                //newsinfo.setUrl(item.getAttribute("link"));
                newsinfo.setTitle(titleitem.getFirstChild().getNodeValue());
            }

            Element authoritem = (Element) item.getElementsByTagName("author").item(0);
            Element timeitem = (Element) item.getElementsByTagName("pubDate").item(0);
            if ((timeitem != null) && (authoritem != null)) {
                newsinfo.setNewsDatetime(authoritem.getFirstChild().getNodeValue() + "  " + timeitem.getFirstChild().getNodeValue());
            }

            Element urlitem = (Element) item.getElementsByTagName("link").item(0);
            if (timeitem != null) {
                newsinfo.setUrl(urlitem.getFirstChild().getNodeValue().replace("www.csdn.net", "m.csdn.net"));
            }

            list.add(newsinfo);
        }
        }
        catch (Exception ex)
        {
            Log.e("error", ex.toString());
        }

        return list;
    }

}
