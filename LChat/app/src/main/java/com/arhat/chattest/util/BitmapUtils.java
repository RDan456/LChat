package com.arhat.chattest.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.ExifInterface;
import android.text.TextUtils;
import android.util.Log;

public class BitmapUtils {

	/**
	 * 计算insmapleSize的大小
	 * 
	 * @param option
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(Options option, int reqWidth, int reqHeight) {
		int inSampleSize = 1;
		int width = option.outWidth;
		int height = option.outHeight;
		if (width > reqWidth || height > reqHeight) {
			int halfWidth = width / 2;
			int halfHeight = height / 2;
			while ((halfWidth / inSampleSize) > reqWidth && (halfHeight / inSampleSize) > reqHeight) {
				inSampleSize *= 2;
			}
		}
		return inSampleSize;
	}

	public static Bitmap decodeSampleBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
		Bitmap bitmap = null;
		Options option = new Options();
		option.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, option);
		int inSampleSize = calculateInSampleSize(option, reqWidth, reqHeight);
		option.inJustDecodeBounds = false;
		option.inSampleSize = inSampleSize;
		try {
			bitmap = BitmapFactory.decodeResource(res, resId, option);
		} catch (OutOfMemoryError e) {
			BitmapLruCacheHelper.getInstance().clear();
			return null;
		}
		return bitmap;
	}

	public static Bitmap decodeSampleBitmapFromPath(String path, int reqWidth, int reqHeight) {
		Bitmap bitmap = null;
		Options option = new Options();
		option.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, option);
		int inSampleSize = calculateInSampleSize(option, reqWidth, reqHeight);
		option.inJustDecodeBounds = false;
		option.inSampleSize = inSampleSize;
		bitmap = BitmapFactory.decodeFile(path, option);
		return bitmap;
	}

	public static Bitmap decodeSampleBitmapFromPath(String path) {
		int width = ScreenUtils.getScreenW();
		int height = ScreenUtils.getScreenH();
		int size = width > height ? height : width;// 选取屏幕宽高最小的那个
		// size = size / 2;
		// 首先获取图片的大小
		Bitmap bitmap = null;
		Options option = new Options();
		option.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, option);
		int inSampleSize = calculateInSampleSize(option, size, size);
		Log.d("fiend", "inSampleSize:" + inSampleSize);
		option.inJustDecodeBounds = false;
		option.inSampleSize = inSampleSize;
		try {
			bitmap = BitmapFactory.decodeFile(path, option);
		} catch (OutOfMemoryError e) {
			BitmapLruCacheHelper.getInstance().clear();
			return null;
		}
		return bitmap;
	}

	public static int readPictureDegree(String path) {
		if (TextUtils.isEmpty(path)) {
			return -1;
		}
		int degree = 0;
		ExifInterface mExifInterface;
		try {
			mExifInterface = new ExifInterface(path);
			// Log.d("fiend", "path:" + path);
			int orientation = mExifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
			Log.d("fiend", "orientation" + orientation);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;

			default:
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 压缩图片
	 * 
	 */
	public static String compressFile(String path) {
		long k = 1024;
		long m = 1024 * k;
		long g = 1024 * m;
		if (path == null) {
			return path;
		}
		File file = new File(path);
		File cache = new File(FileCacheUtils.getCacheDirectory() + File.separator + file.getName());
		if (file.length() > 2 * m) {// 超过2M就压缩
			float compress = getCompressFromSize(file.length());
			Bitmap bm = BitmapFactory.decodeFile(path);
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(cache);
				bm.compress(Bitmap.CompressFormat.JPEG, (int) compress, fos);
				// 若压缩则改变图片路径
				path = cache.getAbsolutePath();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (fos != null) {
					try {
						fos.flush();
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return path;
	}

	/**
	 * 获取压缩比例
	 * 
	 * @param size
	 * @return
	 */
	public static float getCompressFromSize(long size) {
		float compress = 100f;
		long k = 1024;
		long m = 1024 * k;
		long g = 1024 * m;
		compress = (float) (100 / ((size / (2f * m))));
		return compress;
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean isExist(String path) {
		boolean exist = false;
		File file = new File(path);
		if (file.exists()) {
			exist = true;
		}
		return exist;
	}
}
