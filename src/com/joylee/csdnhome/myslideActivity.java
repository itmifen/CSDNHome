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
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
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
    
    DisplayImageOptions options;
    
    protected ImageLoader imageLoader;


    private String urls[] = {
    		 "http://t2.baidu.com/it/u=1107959548,2846438166&fm=23&gp=0.jpg",
             "http://t1.baidu.com/it/u=387538671,2473224000&fm=23&gp=0.jpg",
             "http://t3.baidu.com/it/u=1957344197,1727460690&fm=23&gp=0.jpg",
             "http://t1.baidu.com/it/u=813492054,1888949070&fm=23&gp=0.jpg",
             "http://img.segbuy.com/images/2013/07/30/10224243409auto.jpg",
             "http://img.segbuy.com/images/2013/07/30/10224243707auto.jpg",
             "http://img.segbuy.com/images/2013/07/16/15354651555auto.jpg",
             "http://t1.baidu.com/it/u=387538671,2473224000&fm=23&gp=0.jpg",
             "http://t3.baidu.com/it/u=1957344197,1727460690&fm=23&gp=0.jpg",
             "http://img.segbuy.com/images/2013/07/23/15451156590auto.jpg",
             "http://img.segbuy.com/images/2013/07/18/14554613996auto.jpg",
             "http://img.segbuy.com/images/2013/07/18/14554666695auto.jpg",
             "http://img.segbuy.com/images/2013/07/18/14554676413auto.jpg",
             "http://img.segbuy.com/images/2013/07/18/14554621547auto.jpg",
             "http://img.segbuy.com/images/2013/07/18/14554698393auto.jpg",
             "http://t1.baidu.com/it/u=813492054,1888949070&fm=23&gp=0.jpg",
             "http://t3.baidu.com/it/u=1262100614,561709515&fm=23&gp=0.jpg",
             "http://t2.baidu.com/it/u=1064891170,3563175148&fm=23&gp=0.jpg",
             "http://t2.baidu.com/it/u=3534177823,4284169540&fm=23&gp=0.jpg",
             "http://img.segbuy.com/images/2013/07/23/15451156590auto.jpg",
             "http://img.segbuy.com/images/2013/07/18/14554613996auto.jpg",
             "http://img.segbuy.com/images/2013/07/18/14554666695auto.jpg",
             "http://img.segbuy.com/images/2013/07/18/14554676413auto.jpg",
             "http://img.segbuy.com/images/2013/07/18/14554621547auto.jpg",
             "http://img.segbuy.com/images/2013/07/18/14554698393auto.jpg",
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
             "http://img.hb.aicdn.com/15e250c08262561a2b72fcbfabd3782b609c0e7916b53-3eiaGe_fw580",
             "http://img.hb.aicdn.com/1bc87b69c537e536a0be5ab8d681fa1603cced7726f88-YrnK3W_fw580",
             "http://img.hb.aicdn.com/95006ce2a3ded7518b08959d295dae3939b7dca5bd7f9-M9tEks_fw580w",
             "http://img.hb.aicdn.com/f8c6b1ababac8d5e7633cb22fdc5f25e0a143d8bc554-RaTMGs_fw580w",
             "http://img.hb.aicdn.com/d52f1ca2a7562e124372e16625fc8ecc56a3286017514-XOLLYz_fw580w",
             "http://img.hb.aicdn.com/c104256cd7f2d2422ba4b84b3503c84889a8e8cb65082-OVYldq_fw580",
             "http://img.hb.aicdn.com/d221a8d55acff1621ddd84002a2e3f2241f89cb1473b6-yIwzjF_fw580",
             "http://t3.baidu.com/it/u=3600426509,3698677111&fm=23&gp=0.jpg",
             "http://t3.baidu.com/it/u=1957344197,1727460690&fm=23&gp=0.jpg",
             "http://t1.baidu.com/it/u=813492054,1888949070&fm=23&gp=0.jpg",
             "http://t3.baidu.com/it/u=1262100614,561709515&fm=23&gp=0.jpg",
             "http://t2.baidu.com/it/u=1064891170,3563175148&fm=23&gp=0.jpg",
             "http://t2.baidu.com/it/u=3534177823,4284169540&fm=23&gp=0.jpg",
             "http://img.segbuy.com/images/2013/08/08/09274372045auto.png",
             "http://img.segbuy.com/images/2013/08/08/09274310222auto.jpg",
             "http://img.hb.aicdn.com/218d392a45b71e09ebcc4254def46d0ed46f64da6e5c-Z9DM2X_fw236",
             "http://img.hb.aicdn.com/801dd2a0d8d64961be5b9368d86229f74febcb261e12d-mAzY4P_fw236",
             "http://img.segbuy.com/images/2013/08/08/09274480785auto.jpg",
             "http://img.segbuy.com/images/2013/08/07/14380285063auto.jpg",
             "http://img.segbuy.com/images/2013/07/30/10224243409auto.jpg",
             "http://img.segbuy.com/images/2013/07/30/10224243707auto.jpg",
             "http://img.hb.aicdn.com/85fe2406468e1b64e1b81602f6cb60b912828a581833d-UW6olU_fw580",
             "http://img.segbuy.com/images/2013/07/16/15354651555auto.jpg",
             "http://img.hb.aicdn.com/cbcb4f1015040f34457b51105498c716641b6d278daa6-67T0J7_fw580w",
             "http://img.hb.aicdn.com/15e250c08262561a2b72fcbfabd3782b609c0e7916b53-3eiaGe_fw580",
             "http://img.hb.aicdn.com/1bc87b69c537e536a0be5ab8d681fa1603cced7726f88-YrnK3W_fw580",
             "http://img.hb.aicdn.com/95006ce2a3ded7518b08959d295dae3939b7dca5bd7f9-M9tEks_fw580w",
             "http://img.hb.aicdn.com/f8c6b1ababac8d5e7633cb22fdc5f25e0a143d8bc554-RaTMGs_fw580w",
             "http://img.hb.aicdn.com/d52f1ca2a7562e124372e16625fc8ecc56a3286017514-XOLLYz_fw580w",
             "http://img.hb.aicdn.com/c104256cd7f2d2422ba4b84b3503c84889a8e8cb65082-OVYldq_fw580",
             "http://img.hb.aicdn.com/d221a8d55acff1621ddd84002a2e3f2241f89cb1473b6-yIwzjF_fw580",
             "http://img.hb.aicdn.com/797c40faeb1e0b201df87cc2ae9692b734551f2912e00a-EBBE4w_fw580w"
    };
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myslide);
        
        options = new DisplayImageOptions.Builder()
		//.showImageOnLoading(R.drawable.ic_stub)
		//.showImageForEmptyUri(R.drawable.ic_empty)
		//.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.build();
        
        imageLoader = ImageLoader.getInstance();
//        // 2. 使用默认配置  
//           ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);  
//           // 3. 初始化ImageLoader  
//           imageLoader.init(configuration); 
           
           
           
           ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
			.threadPriority(Thread.NORM_PRIORITY - 2)
			.denyCacheImageMultipleSizesInMemory()
			.discCacheFileNameGenerator(new Md5FileNameGenerator())
			.tasksProcessingOrder(QueueProcessingType.LIFO)
			.writeDebugLogs() // Remove for release app
			.build();
	// Initialize ImageLoader with configuration.
           imageLoader.init(config);

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
                
                imageLoader.displayImage(data.toString(), iv, options);
               // loadImage(data.toString(), iv);
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

            for (int i = 0; i < urls.length; i++) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("url", urls[i]);
                list.add(map);
            }

            SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), list,
                    R.layout.row_staggered_demo, new String[]{"url"}, new int[]{
                    R.id.imageView1});

            adapter.setViewBinder(new MyViewBinder());
            gridView.setAdapter(adapter);

          //  adapter.notifyDataSetChanged();

        }


    }

    class MyThread implements Runnable {
        public void run() {
            Log.d("thread.......", "mThread........");
            Message msg = new Message();
            myslideActivity.this.myHandler.sendMessage(msg);

        }
    }

}
