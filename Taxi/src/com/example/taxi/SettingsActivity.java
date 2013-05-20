package com.example.taxi;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity
{
	@Override 
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState); 
	    // загружаем предпочтения из ресурсов 
	    addPreferencesFromResource(R.xml.settings); 
	}
}
