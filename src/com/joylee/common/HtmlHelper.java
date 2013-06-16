package com.joylee.common;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Administrator on 13-6-15.
 */
public class HtmlHelper {

    public static  String GetCsdnDetail(String url)
    { Document doc=null;
        try {
                doc = Jsoup.connect(url).get();
        }
        catch (IOException e) {

        }
     Element details= doc.select("div.text").first();
        details.select("div.share").html("");
        details.select("ul.belong").html("");
        details.select("div.next_page").html("");
     details.select("img").attr("width", "100%");

      //  details.select("img").attr("width", "100%");
        return   details.html();
    }


}

