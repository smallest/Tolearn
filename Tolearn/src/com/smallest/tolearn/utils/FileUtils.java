package com.smallest.tolearn.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.os.Environment;
import android.util.Log;

public class FileUtils {
	/**
	 * 得到sdcard的路径
	 * 
	 * @return 返回一个字符串数组 下标0:内置sdcard 下标1:外置sdcard
	 */
	public static String[] getSDCardPath() {
		String[] sdCardPath = new String[2];
		File sdFile = Environment.getExternalStorageDirectory();
		Log.d("path", "sdPath:" + sdFile.getPath());
		File[] files = sdFile.getParentFile().listFiles();
		for (File file : files) {
			if (file.getAbsolutePath().equals(sdFile.getAbsolutePath())) {// 外置
				sdCardPath[0] = sdFile.getAbsolutePath();
				Log.d("path", "sdCardPath0:" + sdCardPath[0]);
			} else {
				sdCardPath[1] = file.getAbsolutePath();
				Log.d("path", "sdCardPath1:" + sdCardPath[1]);
			}
		}
		return sdCardPath;
	}

	public static boolean syncDbToSdcard() {
		final String inFileName = "/data/data/com.smallest.tolearn/databases/tolearn.db";
		File dbFile = new File(inFileName);
		FileInputStream fis;

		String[] sdPath = getSDCardPath();
		for (String path : sdPath) {
			if (!path.isEmpty()) {
				try {
					fis = new FileInputStream(dbFile);
				} catch (FileNotFoundException e) {
					Log.d("smallest", "tolearn.db not find");
					e.printStackTrace();
					return false;
				}
				String outFileName = path + "/Tolearn/database_copy.db";
				String outDir = path + "/Tolearn";
				File filedir = new File(outDir);
				if (!filedir.exists()) {
					filedir.mkdirs();
				}
				// Open the empty db as the output stream
				OutputStream output;
				try {

					output = new FileOutputStream(outFileName);
				} catch (FileNotFoundException e) {
					Log.d("smallest", "new fileoutputstream failed");
					e.printStackTrace();
					return false;
				}
				// Transfer bytes from the inputfile to the outputfile
				byte[] buffer = new byte[1024];
				int length;
				try {
					while ((length = fis.read(buffer)) > 0) {
						output.write(buffer, 0, length);
						Log.d("smallest", "read bytes:" + length);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Close the streams
				try {
					output.flush();
					output.close();
					fis.close();
				} catch (IOException e) {
					Log.d("smallest", "FileUtils,close failed");
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
}
