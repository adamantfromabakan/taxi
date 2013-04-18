package com.example.taxi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class sysDialogs extends Activity{
	final int DIALOG_EXIT = 1;
	  
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {

    }
    
    public void onclick(View v) {
      // �������� ������
      showDialog(DIALOG_EXIT);
    }
    
    protected Dialog onCreateDialog(int id) {
      if (id == DIALOG_EXIT) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        // ���������
        adb.setTitle(R.string.exit);
        // ���������
        adb.setMessage(R.string.save_data);
        // ������
        adb.setIcon(android.R.drawable.ic_dialog_info);
        // ������ �������������� ������
        adb.setPositiveButton(R.string.yes, myClickListener);
        // ������ �������������� ������
        adb.setNegativeButton(R.string.no, myClickListener);
        // ������ ������������ ������
        adb.setNeutralButton(R.string.cancel, myClickListener);
        // ������� ������
        return adb.create();
      }
      return super.onCreateDialog(id);
    }
    
    OnClickListener myClickListener = new OnClickListener() {
    public void onClick(DialogInterface dialog, int which) {
      switch (which) {
      // ������������� ������
      case Dialog.BUTTON_POSITIVE:
        saveData();
        finish();
        break;
      // ���������� ������
      case Dialog.BUTTON_NEGATIVE:
        finish();
        break;
      // ����������� ������  
      case Dialog.BUTTON_NEUTRAL:
        break;
      }
    }
  };
  
  void saveData() {
    Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
  }
}
/*
 * 
 * public void addDialog(){
	context = MainActivity.this;
	String title = "����� ���� ������";
	String message = "������ ����";
	String button1String = "������� ����";
	String button2String = "�������� ����";
	
	ad = new AlertDialog.Builder(context);
	ad.setTitle(title);  // ���������
	ad.setMessage(message); // ���������
	ad.setPositiveButton(button1String, new OnClickListener() {
		public void onClick(DialogInterface dialog, int arg1) {
			Toast.makeText(context, "�� ������� ���������� �����",
					Toast.LENGTH_LONG).show();
		}
	});
	ad.setNegativeButton(button2String, new OnClickListener() {
		public void onClick(DialogInterface dialog, int arg1) {
			Toast.makeText(context, "�������� �� �����", Toast.LENGTH_LONG)
					.show();
		}
	});
	ad.setCancelable(true);
	ad.setOnCancelListener(new OnCancelListener() {
		public void onCancel(DialogInterface dialog) {
			Toast.makeText(context, "�� ������ �� �������",
					Toast.LENGTH_LONG).show();
		}
	});	
}

 * */


