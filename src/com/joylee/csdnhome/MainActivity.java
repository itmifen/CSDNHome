package com.joylee.csdnhome;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import com.joylee.business.NewsManager;
import com.joylee.common.NetHelper;
import com.joylee.entity.newsentity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.Toast;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import com.joylee.handler.*;

public class MainActivity extends TabActivity {


    private TabHost tabHost;

    private Intent intentcsdn;
    private Intent intentkr;

    public String whichTab;
    public RadioButton rb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        rb1=(RadioButton)findViewById(R.id.TabBlog);


        intentcsdn = new Intent(this, CsdnMainActivity.class);
        intentkr = new Intent(this, KrMainActivity.class);

        InitialTab();



    }

    private void InitialTab() {
        tabHost = getTabHost();
        tabHost.addTab(buildTabSpec("csdn", R.drawable.ic_launcher, R.drawable.ic_launcher, intentcsdn));
        tabHost.addTab(buildTabSpec("36kr", R.drawable.ic_launcher, R.drawable.ic_launcher, intentkr));

        // tabHost.addTab(buildTabSpec("search", R.string.main_search,
        // R.drawable.icon, intentSearch));//fix tabHost bug:set the first tab
        // as default tab


    }


    private void InitialSelectedTab() {
        SharedPreferences settings =getSharedPreferences("nowtab", MODE_PRIVATE);
        whichTab = settings.getString("nowtab", "blog");
        if (whichTab.equals("blog"))
           rb1.setChecked(true);

    }

    /**
     * 公用初始化Tab
     */
    private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
                                         final Intent content) {
        return tabHost
                .newTabSpec(tag)
                .setIndicator(getString(resLabel),
                        getResources().getDrawable(resIcon))
                .setContent(content);
    }


}
