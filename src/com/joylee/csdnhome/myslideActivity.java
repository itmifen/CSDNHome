package com.joylee.csdnhome;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.agimind.widget.SlideHolder;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.os.Bundle;
import com.joylee.common.AsyncImageLoader;
import com.joylee.entity.newsentity;
import com.origamilabs.library.views.StaggeredGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class myslideActivity  extends Activity {

    /**
     * Images are taken by Romain Guy ! He's a great photographer as well as a
     * great programmer. http://www.flickr.com/photos/romainguy
     */

    private List<Map<String, String>> list = new ArrayList<Map<String, String>>();

    AsyncImageLoader asyncImageLoader;

    StaggeredGridView gridView;

    private MyHandler myHandler;


    private String urls[] = {
            "http://t2.baidu.com/it/u=1107959548,2846438166&fm=23&gp=0.jpg",
            "http://t1.baidu.com/it/u=387538671,2473224000&fm=23&gp=0.jpg",
            "http://t3.baidu.com/it/u=1957344197,1727460690&fm=23&gp=0.jpg",
            "http://t1.baidu.com/it/u=813492054,1888949070&fm=23&gp=0.jpg",
            "http://t3.baidu.com/it/u=1262100614,561709515&fm=23&gp=0.jpg",
            "http://t2.baidu.com/it/u=1064891170,3563175148&fm=23&gp=0.jpg",
            "http://t2.baidu.com/it/u=3534177823,4284169540&fm=23&gp=0.jpg",
            "http://t3.baidu.com/it/u=3600426509,3698677111&fm=23&gp=0.jpg",
            "http://t3.baidu.com/it/u=3515104138,2079115592&fm=23&gp=0.jpg",
            "http://t1.baidu.com/it/u=387538671,2473224000&fm=23&gp=0.jpg",
            "http://t3.baidu.com/it/u=1957344197,1727460690&fm=23&gp=0.jpg",
            "http://t1.baidu.com/it/u=813492054,1888949070&fm=23&gp=0.jpg",
            "http://t3.baidu.com/it/u=1262100614,561709515&fm=23&gp=0.jpg",
            "http://t2.baidu.com/it/u=1064891170,3563175148&fm=23&gp=0.jpg",
            "http://t2.baidu.com/it/u=3534177823,4284169540&fm=23&gp=0.jpg",
            "http://t3.baidu.com/it/u=3600426509,3698677111&fm=23&gp=0.jpg",
            "http://t1.baidu.com/it/u=387538671,2473224000&fm=23&gp=0.jpg",
            "http://t3.baidu.com/it/u=1957344197,1727460690&fm=23&gp=0.jpg",
            "http://t1.baidu.com/it/u=813492054,1888949070&fm=23&gp=0.jpg",
            "http://t3.baidu.com/it/u=1262100614,561709515&fm=23&gp=0.jpg",
            "http://t2.baidu.com/it/u=1064891170,3563175148&fm=23&gp=0.jpg",
            "http://t2.baidu.com/it/u=3534177823,4284169540&fm=23&gp=0.jpg",
            "http://t3.baidu.com/it/u=3600426509,3698677111&fm=23&gp=0.jpg",
            "http://img.hb.aicdn.com/218d392a45b71e09ebcc4254def46d0ed46f64da6e5c-Z9DM2X_fw236",
            "http://img.hb.aicdn.com/801dd2a0d8d64961be5b9368d86229f74febcb261e12d-mAzY4P_fw236",
            "http://img.hb.aicdn.com/a8d9cf3554d80fd07575fc14cfc5d8c05a5d25aff417-9rVPAK_fw236",
            "http://t1.baidu.com/it/u=387538671,2473224000&fm=23&gp=0.jpg",
            "http://t3.baidu.com/it/u=1957344197,1727460690&fm=23&gp=0.jpg",
            "http://t1.baidu.com/it/u=813492054,1888949070&fm=23&gp=0.jpg",
            "http://t3.baidu.com/it/u=1262100614,561709515&fm=23&gp=0.jpg",
            "http://t2.baidu.com/it/u=1064891170,3563175148&fm=23&gp=0.jpg",
            "http://t2.baidu.com/it/u=3534177823,4284169540&fm=23&gp=0.jpg",
            "http://t3.baidu.com/it/u=3600426509,3698677111&fm=23&gp=0.jpg",
            "http://t3.baidu.com/it/u=1957344197,1727460690&fm=23&gp=0.jpg",
            "http://t1.baidu.com/it/u=813492054,1888949070&fm=23&gp=0.jpg",
            "http://t3.baidu.com/it/u=1262100614,561709515&fm=23&gp=0.jpg",
            "http://t2.baidu.com/it/u=1064891170,3563175148&fm=23&gp=0.jpg",
            "http://t2.baidu.com/it/u=3534177823,4284169540&fm=23&gp=0.jpg",
            "http://img.hb.aicdn.com/218d392a45b71e09ebcc4254def46d0ed46f64da6e5c-Z9DM2X_fw236",
            "http://img.hb.aicdn.com/801dd2a0d8d64961be5b9368d86229f74febcb261e12d-mAzY4P_fw236"
    };
    /**
     * This will not work so great since the heights of the imageViews
     * are calculated on the iamgeLoader callback ruining the offsets. To fix this try to get
     * the (intrinsic) image width and height and set the views height manually. I will
     * look into a fix once I find extra time.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myslide);

        asyncImageLoader = new AsyncImageLoader(new Handler());
        gridView = (StaggeredGridView) this.findViewById(R.id.staggeredGridView1);

        myHandler = new MyHandler();
        MyThread m = new MyThread();
        new Thread(m).start();

    }



    // 绘制并显示图片
    public class MyViewBinder implements SimpleAdapter.ViewBinder {
        public boolean setViewValue(View view, Object data, String textRepresentation) {
            if ((view instanceof ImageView) & (data instanceof String)) {
                ImageView iv = (ImageView) view;
                loadImage(data.toString(), iv);
                return true;
            }
            return false;
        }
    }


    public void loadImage(final String url, final ImageView imageView) {

        asyncImageLoader.loadDrawable(url,
                new AsyncImageLoader.ImageCallback() {
                    public void loadRemoteImage(Drawable imageDrawable) {
                        if (imageDrawable != null) {
                            imageView.setImageDrawable(imageDrawable);
                        }
                    }

                    @Override
                    public void loadDefaultIamge() {
                        imageView.setImageResource(R.drawable.loading);
                    }

                    @Override
                    public void loadLoadingImage() {
                        imageView.setImageResource(R.drawable.loading);
                    }

                    @Override
                    public void loadCaheImage(Drawable imageDrawable) {
                        if (imageDrawable != null) {
                            imageView.setImageDrawable(imageDrawable);
                        }
                    }

                });

    }


    class MyHandler extends Handler {
        public MyHandler() {
        }


        public MyHandler(Looper L) {
            super(L);
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Log.d("MyHandler", "handleMessage......");
            super.handleMessage(msg);

            for (int i = 0; i < 20; i++) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("url", urls[i]);
                list.add(map);
            }

            SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), list,
                    R.layout.row_staggered_demo, new String[]{"url"}, new int[]{
                    R.id.imageView1});

            adapter.setViewBinder(new MyViewBinder());
            gridView.setAdapter(adapter);

            adapter.notifyDataSetChanged();

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
            myslideActivity.this.myHandler.sendMessage(msg);

        }
    }

}
