package com.joylee.common;

import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.drawable.Drawable;
import android.os.Handler;

/**
 * 缓存图片数据的方法
 * 
 */
public class AsyncImageLoader {

	public static final int POOLSIZE = 5;

	public static Map<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();
	public ExecutorService executorService = Executors
			.newFixedThreadPool(POOLSIZE);
	private Handler handler;

	public AsyncImageLoader(Handler mHandler) {
		this.handler = mHandler;
	}

	public Drawable loadDrawable(final String imageUrl,
			final ImageCallback callback) {
		if (imageCache.containsKey(imageUrl)) {
			final SoftReference<Drawable> softReference = imageCache
					.get(imageUrl);
			if (softReference.get() != null) {// 显示缓存图片
				callback.loadCaheImage(softReference.get());
				return softReference.get();
			}
		}
		callback.loadLoadingImage();// 显示加载图片
		if (executorService == null) {
			executorService = Executors.newFixedThreadPool(POOLSIZE);
		}
		executorService.submit(new Runnable() {
			public void run() {
				try {
					final Drawable drawable = Drawable.createFromStream(
							new URL(imageUrl).openStream(), "image.png");
					imageCache.put(imageUrl, new SoftReference<Drawable>(
							drawable));
					handler.post(new Runnable() {
						public void run() {
							if (drawable != null) {
								callback.loadRemoteImage(drawable);
							} else {
								callback.loadDefaultIamge();
							}
						}
					});
				} catch (Exception e) {
					handler.post(new Runnable() {
						public void run() {
							callback.loadDefaultIamge();
						}
					});
					e.printStackTrace();
				}
			}
		});
		return null;
	}

	public interface ImageCallback {
		public void loadRemoteImage(Drawable imageDrawable);

		public void loadDefaultIamge();

		public void loadLoadingImage();

		public void loadCaheImage(Drawable imageDrawable);
	}

	public static void clearCache() {
		imageCache.clear();
	}
}