package com.example.taxi;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class sysDialogs extends Activity {
 
  final int DIALOG_EXIT = 1;
 
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(com.example.taxi.R.layout.main);
    }
   
    public void onclick(View v) {
      // вызываем диалог
      showDialog(DIALOG_EXIT);
    }
   
    protected Dialog onCreateDialog(int id) {
      if (id == DIALOG_EXIT) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        // заголовок
        adb.setTitle(com.example.taxi.R.string.exit);
        // сообщение
        adb.setMessage(com.example.taxi.R.string.save_data);
        // иконка
        adb.setIcon(android.R.drawable.ic_dialog_info);
        // кнопка положительного ответа
        adb.setPositiveButton(com.example.taxi.R.string.yes, myClickListener);
        // кнопка отрицательного ответа
        adb.setNegativeButton(com.example.taxi.R.string.no, myClickListener);
        // кнопка нейтрального ответа
        adb.setNeutralButton(com.example.taxi.R.string.cancel, myClickListener);
        // создаем диалог
        return adb.create();
      }
      return super.onCreateDialog(id);
    }
   
    OnClickListener myClickListener = new OnClickListener() {
    public void onClick(DialogInterface dialog, int which) {
      switch (which) {
      // положительная кнопка
      case Dialog.BUTTON_POSITIVE:
        saveData();
        finish();
        break;
      // негаитвная кнопка
      case Dialog.BUTTON_NEGATIVE:
        finish();
        break;
      // нейтральная кнопка 
      case Dialog.BUTTON_NEUTRAL:
        break;
      }
    }
  };
 
  void saveData() {
    Toast.makeText(this, com.example.taxi.R.string.saved, Toast.LENGTH_SHORT).show();
  }
}
/*
 * 
 * public void addDialog(){
	context = MainActivity.this;
	String title = "Выбор есть всегда";
	String message = "Выбери пищу";
	String button1String = "Вкусная пища";
	String button2String = "Здоровая пища";
	
	ad = new AlertDialog.Builder(context);
	ad.setTitle(title);  // заголовок
	ad.setMessage(message); // сообщение
	ad.setPositiveButton(button1String, new OnClickListener() {
		public void onClick(DialogInterface dialog, int arg1) {
			Toast.makeText(context, "Вы сделали правильный выбор",
					Toast.LENGTH_LONG).show();
		}
	});
	ad.setNegativeButton(button2String, new OnClickListener() {
		public void onClick(DialogInterface dialog, int arg1) {
			Toast.makeText(context, "Возможно вы правы", Toast.LENGTH_LONG)
					.show();
		}
	});
	ad.setCancelable(true);
	ad.setOnCancelListener(new OnCancelListener() {
		public void onCancel(DialogInterface dialog) {
			Toast.makeText(context, "Вы ничего не выбрали",
					Toast.LENGTH_LONG).show();
		}
	});	
}

 * */


