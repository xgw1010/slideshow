package com.xgw.slideshow;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SlidePreferenceActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstancesState)
	{
		super.onCreate(savedInstancesState);
		this.addPreferencesFromResource(R.xml.options);
	}
}
