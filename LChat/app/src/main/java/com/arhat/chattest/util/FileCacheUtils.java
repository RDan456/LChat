package com.arhat.chattest.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

/**
 *
 * 文件缓存
 */
public class FileCacheUtils {
	/**
	 * 图片缓存的相对路径
	 */
	private static final String IMG_CACH_DIR = "/imgCache";

	/**
	 * 手机缓存目录
	 */
	private static String DATA_ROOT_PATH = null;
	/**
	 * sd卡根目录
	 */
	private static String SD_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

	/**
	 * 缓存的扩展名
	 */
	private static final String CACHE_TAIL = ".cach";

	/**
	 * 最大缓存空间,单位是mb
	 */
	private static final int CACHE_SIZE = 100;

	/**
	 * sd卡内存低于此值时将会清理缓存,单位是mb
	 */
	private static final int NEED_TO_CLEAN = 150;

	/**
	 * 上下文
	 */
	private Context context;

	private static final String TAG = "BitmapFileCacheUtils";

	public FileCacheUtils(Context context) {
		this.context = context;
		DATA_ROOT_PATH = context.getCacheDir().getAbsolutePath();
	}

	/**
	 * 从缓存中获取一张图片
	 */
	public Bitmap getBitmapFromFile(String key) {
		// Log.d("fiend", "getBitmapFromFile" + key);
		if (key == null) {
			return null;
		}
		String filename = getCacheDirectory() + File.separator + convertKeyToFilename(key);
		File file = new File(filename);
		// Log.d("fiend", "fileCache1:" + key);
		if (file.exists()) {
			Bitmap bitmap = BitmapUtils.decodeSampleBitmapFromPath(filename);
			if (bitmap == null) {
				file.delete();
			} else {
				updateFileModifiedTime(filename);
				Log.i(TAG, "get file from sdcard cache success...");
				return bitmap;
			}
		}
		return null;
	}

	/**
	 * 从缓存中获取一张图片,压缩
	 */
	public Bitmap getBitmapFromFileOnCompress(String key) {
		// Log.d("fiend", "fileCache2:" + key);
		if (key == null) {
			return null;
		}
		String filename = getCacheDirectory() + File.separator + convertKeyToFilename(key);
		File file = new File(filename);
		if (file.exists()) {
			Bitmap bitmap = BitmapUtils.decodeSampleBitmapFromPath(filename, ScreenUtils.dp2px(100),
					ScreenUtils.dp2px(100));
			if (bitmap == null) {
				file.delete();
			} else {
				updateFileModifiedTime(filename);
				Log.i(TAG, "get file from sdcard cache success...");
				return bitmap;
			}
		}
		return null;
	}

	/**
	 * 将图片存入文件缓存
	 */
	public void addBitmapToFile(String key, Bitmap bm) {
		if (bm == null || key == null) {
			return;
		}
		// 视情况清除部分缓存
		removeCache(getCacheDirectory());

		String filename = convertKeyToFilename(key);
		File dir = new File(getCacheDirectory());
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, filename);
		try {
			OutputStream out = new FileOutputStream(file);// 这里需要注意，如果指定目录不存在，应该先调用mkdirs生成目录，否则可能创建文件失败
			bm.compress(CompressFormat.JPEG, 100, out);
			out.close();
			Log.i(TAG, "add file to sdcard cache success...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将图片存入文件缓存
	 */
	public void addBitmapToFile(String key, byte[] bytes) {
		if (bytes == null || key == null) {
			return;
		}
		// 视情况清除部分缓存
		removeCache(getCacheDirectory());

		String filename = convertKeyToFilename(key);
		File dir = new File(getCacheDirectory());
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, filename);
		try {
			OutputStream out = new FileOutputStream(file);// 这里需要注意，如果指定目录不存在，应该先调用mkdirs生成目录，否则可能创建文件失败
			out.write(bytes);
			out.flush();
			out.close();
			Log.i(TAG, "add file to sdcard cache success...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取文件缓存路径
	 * 
	 * @return
	 */
	public static String getCacheDirectory() {
		String cachePath = null;
		if (isSdcardAvailable()) {
			cachePath = SD_ROOT_PATH + IMG_CACH_DIR;
		} else {
			cachePath = DATA_ROOT_PATH + IMG_CACH_DIR;
		}
		return cachePath;
	}

	/**
	 * 
	 * 清除40%的缓存，这些缓存被删除的优先级根据近期使用时间排列,越久没被使用，越容易被删除
	 */
	private void removeCache(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}
		double total_size = 0;
		for (File file : files) {
			total_size += file.length();
		}
		total_size = total_size / 1024 / 1024;
		if (total_size > CACHE_SIZE || getSdCardFreeSpace() <= NEED_TO_CLEAN) {
			Log.i(TAG, "remove cache from sdcard cache...");
			int removeFactor = (int) (files.length * 0.4);
			Arrays.sort(files, new FileLastModifiedComparator());
			for (int i = 0; i < removeFactor; i++) {
				files[i].delete();
			}
		}
	}

	/**
	 * 
	 * 清除100%的缓存
	 */
	public void removeAllCache(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}

		for (int i = 0; i < files.length; i++) {
			files[i].delete();
		}
	}

	/**
	 * 获取sd卡可用空间
	 */
	private int getSdCardFreeSpace() {
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
		double freespace = stat.getAvailableBlocks() * stat.getBlockSize();
		return (int) (freespace / 1024 / 1024);
	}

	/**
	 * 判断sd卡是否可用
	 * 
	 * @return
	 */
	private static boolean isSdcardAvailable() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	/**
	 * 将关键字转化为文件名
	 */
	private String convertKeyToFilename(String key) {
		if (key == null) {
			return "";
		}
		return key.hashCode() + CACHE_TAIL;
	}

	/**
	 * 更新文件最后修改时间
	 */
	private void updateFileModifiedTime(String path) {
		File file = new File(path);
		file.setLastModified(System.currentTimeMillis());
	}

	private class FileLastModifiedComparator implements Comparator<File> {
		@Override
		public int compare(File lhs, File rhs) {
			if (lhs.lastModified() > rhs.lastModified()) {
				return 1;
			} else if (lhs.lastModified() == rhs.lastModified()) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	public File getFileFromCache(String key) {
		if (key == null) {
			return null;
		}
		String filename = getCacheDirectory() + File.separator + convertKeyToFilename(key);
		File file = new File(filename);
		if (file.exists()) {
			return file;
		}
		return null;
	}
}
