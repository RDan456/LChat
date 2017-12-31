package com.arhat.chattest.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.arhat.chattest.xmpp.FriendsManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

/**
 * 下载图片的工具类 通过downloadImage方法下载图片，并将图片保存到缓存中（使用线程池）。
 * 对下载得到的图片交由一个回调接口OnImageDownloadListener处理 通过showCacheImage方法获取缓存中的图片
 */
public class ImageDownloader {
	/**
	 * 下载image的线程池
	 */
	private ExecutorService mImageThreadPool = null;

	/**
	 * 文件缓存的工具类
	 */
	private FileCacheUtils fileCacheUtils = null;

	/**
	 * 线程池中线程的数量
	 */
	private static final int THREAD_NUM = 2;

	/**
	 * 缩略图的宽
	 */
	private static final int REQ_WIDTH = 90;
	/**
	 * 缩略图的高
	 */
	private static final int REQ_HEIGHT = 90;

	protected static final int DOWNLOAD = 1;

	private Context context;

	/**
	 * 获取头像类
	 */
	private FriendsManager mFriendsManager;
	/**
	 * 构造器
	 * 
	 * @param context
	 */
	public ImageDownloader(Context context) {
		this.context = context;
		fileCacheUtils = new FileCacheUtils(context);
		mFriendsManager = new FriendsManager();
	}

	/**
	 * 下载一张图片，先从内存缓存中找，如果没有则去文件缓存中找，如果还没有就从网络中下载
	 * 
	 * @param listener
	 * @return
	 */
	public void downloadImage(final String username, final OnImageDownloadListener listener) {
		if (username == null || username.equals("")) {
			return;
		}
		final String subUrl = username.replaceAll("[^\\w]", "");
		Bitmap bitmap = showCacheBitmap(subUrl);
		if (bitmap != null)// 缓存中找到
		{
			listener.onImageDownload(username, bitmap);
		} else// 缓存中未找到，则开启线程下载
		{

			final Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					if (msg.what == DOWNLOAD) {
						listener.onImageDownload(username, (Bitmap) msg.obj);// 对下载后的图片的操作交由listener实现类处理
					}
				}
			};
			getThreadPool().execute(new Runnable()// 从线程池中获取一个线程执行下载操作并将下载后的图片加到文件缓存和内存缓存
			{
				@Override
				public void run() {
					L.d("carl","下载");
					Bitmap bitmap = mFriendsManager.getUserImage(username);// 从网络上下载图片
					L.d("carl","下载完成");
					Message msg = Message.obtain(handler, DOWNLOAD, bitmap);
					msg.sendToTarget();// 发送消息

					// 加到缓存中
					fileCacheUtils.addBitmapToFile(subUrl, bitmap);
					BitmapLruCacheHelper.getInstance().addBitmapToMemCache(subUrl, bitmap);
				}
			});

		}
	}


	/**
	 * 显示缓存中的图片
	 *
	 * @param url
	 * @return
	 */
	public Bitmap showCacheBitmap(String url) {
		BitmapLruCacheHelper bitmapLruCacheHelper = BitmapLruCacheHelper.getInstance();
		Bitmap bitmap = bitmapLruCacheHelper.getBitmapFromMemCache(url);
		if (bitmap != null)// 首先从内存缓存中找
		{
			return bitmap;
		} else {
			// Log.d("fiend", "" + url);
			bitmap = fileCacheUtils.getBitmapFromFile(url);
			if (bitmap != null)// 在文件缓存中找到
			{
				BitmapLruCacheHelper.getInstance().addBitmapToMemCache(url, bitmap);// 加入内存缓存
				return bitmap;
			}
		}
		return null;
	}

	/**
	 * 获取线程池实例
	 */
	public ExecutorService getThreadPool() {
		if (mImageThreadPool == null) {
			synchronized (ExecutorService.class) {
				if (mImageThreadPool == null) {
					mImageThreadPool = Executors.newFixedThreadPool(THREAD_NUM);
				}
			}
		}
		return mImageThreadPool;
	}


	/**
	 * 取消当前的任务
	 */
	public synchronized void cancellTask() {
		if (mImageThreadPool != null) {
			mImageThreadPool.shutdownNow();
			mImageThreadPool = null;
		}
	}

	/**
	 * 操作下载后的图片的回调接口
	 */
	public interface OnImageDownloadListener {
		void onImageDownload(String url, Bitmap bitmap);
	}
}