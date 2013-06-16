package com.joylee.common;

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


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(stream);
            Element root = document.getDocumentElement();
            NodeList items = root.getElementsByTagName("item");

            for (int i = 0; i < items.getLength(); i++) {
                newsentity newsinfo = new newsentity();
                Element item = (Element)items.item(i);
                Element titleitem= (Element)item.getElementsByTagName("title").item(0);
                if(titleitem!=null)
                {
                    //newsinfo.setUrl(item.getAttribute("title"));
                    //newsinfo.setUrl(item.getAttribute("link"));
                    newsinfo.setTitle(titleitem.getFirstChild().getNodeValue());
                }

                Element authoritem= (Element)item.getElementsByTagName("author").item(0);
                Element timeitem= (Element)item.getElementsByTagName("pubDate").item(0);
                if((timeitem!=null)&&(authoritem!=null))
                {
                    newsinfo.setNewsDatetime(authoritem.getFirstChild().getNodeValue()+"  "+timeitem.getFirstChild().getNodeValue());
                }

                Element urlitem= (Element)item.getElementsByTagName("link").item(0);
                if(timeitem!=null)
                {
                    newsinfo.setUrl(urlitem.getFirstChild().getNodeValue().replace("www.csdn.net","m.csdn.net"));
                }

                list.add(newsinfo);
            }

        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;
    }

}
