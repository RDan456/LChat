package com.arhat.chattest.util;

import java.io.File;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

/**
 * 
 * 使用lrucache缓存图片到内存，做成了单例模式
 */
public class BitmapLruCacheHelper {
	private static final String TAG = null;
	private LruCache cache = null;
	private static BitmapLruCacheHelper instance = null;

	public void LoadData() {
		int maxSize = (int) (Runtime.getRuntime().maxMemory() / 8);
		cache = new LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			}
		};
		cache.evictAll();
	}

	// 这个类必须自动向整个系统提供这个实例对象
	public static BitmapLruCacheHelper getInstance() {
		if (instance == null) {
			// 创建单例。在创建时进行数据读取
			instance = new BitmapLruCacheHelper();
			instance.LoadData();
		}
		// 返回单例的实例
		return instance;
	}

	/**
	 * 加入缓存
	 * 
	 * @param key
	 * @param value
	 */
	public void addBitmapToMemCache(String key, Bitmap value) {
		if (key == null || value == null) {
			return;
		}
		if (cache != null && getBitmapFromMemCache(key) == null) {
			cache.put(key, value);
			Log.i(TAG, "put to lrucache success");
		}
	}

	/**
	 * 从缓存中获取图片
	 * 
	 * @param key
	 * @return
	 */
	public Bitmap getBitmapFromMemCache(String key) {
		if (key == null) {
			return null;
		}
		Bitmap bitmap = (Bitmap) cache.get(key);
		Log.i(TAG, "from lrucache,bitmap=" + bitmap);
		return bitmap;
	}
	
	public void clear(){
		cache.evictAll();
	}
	
}
