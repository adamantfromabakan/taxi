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
		// ��������� ����������� SD
	    /*if (!Environment.getExternalStorageState().equals(
	        Environment.MEDIA_MOUNTED)) {
	      Log.d(TAG, "SD-����� �� ��������: " + Environment.getExternalStorageState());
	      return;
	    }*/
	    // �������� ���� � SD
	    //File sdPath = Environment.getExternalStorageDirectory();
	    // ��������� ���� ������� � ����
	    //sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
	    File sdPath = new File(path);
	    // ������� �������
	    sdPath.mkdirs();
	    // ��������� ������ File, ������� �������� ���� � �����
	    File sdFile = new File(sdPath, file);
	    try {
	      // ��������� ����� ��� ������
	      BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
	      // ����� ������
	      bw.write(strput+"\n");
	      // ��������� �����
	      bw.close();
	      Log.d(TAG, "���� ������� �� SD: " + sdFile.getAbsolutePath());
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}

}
