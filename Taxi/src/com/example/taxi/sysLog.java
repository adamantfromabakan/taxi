package com.example.taxi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.os.Environment;
import android.util.Log;


public class sysLog {
	private static final String TAG = "sysLog";
	private static final int MODE_PRIVATE = 32768;
	
	public void logwriter(String file, String path, String strput)
	{
		// проверяем доступность SD
	    /*if (!Environment.getExternalStorageState().equals(
	        Environment.MEDIA_MOUNTED)) {
	      Log.d(TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
	      return;
	    }*/
	    // получаем путь к SD
	    //File sdPath = Environment.getExternalStorageDirectory();
	    // добавляем свой каталог к пути
	    //sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
	    File sdPath = new File(path);
	    // создаем каталог
	    sdPath.mkdirs();
	    // формируем объект File, который содержит путь к файлу
	    File sdFile = new File(sdPath, file);
	    try {
	      // открываем поток для записи
	      BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
	      // пишем данные
	      bw.write(strput+"\n");
	      // закрываем поток
	      bw.close();
	      Log.d(TAG, "Файл записан на SD: " + sdFile.getAbsolutePath());
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}

}
