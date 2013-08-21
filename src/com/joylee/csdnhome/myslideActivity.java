package com.joylee.csdnhome;

import android.view.View;
import com.agimind.widget.SlideHolder;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.os.Bundle;

public class myslideActivity  extends Activity {


    SlideHolder mSlideHolder;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        // set the content view
	        setContentView(R.layout.myslide);

         mSlideHolder = (SlideHolder) findViewById(R.id.slideHolder);



         View toggleView = findViewById(R.id.textView);
         toggleView.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 mSlideHolder.toggle();
             }
         });

	    }

}
