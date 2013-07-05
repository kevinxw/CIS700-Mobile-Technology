package com.example.hw5;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	final Context context = this;
	// load JNI
	static {
		System.loadLibrary("led-toggle");
	}
	// is current LED light on or off?
	boolean ledStatus = false;

	// this is for the JNI
	public static native String LEDToggle(String ledStatus);

	// show an alert box
	private void showMsg(String msg) {
		AlertDialog alertDialog;
		alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle("Changing LED light status!");
		alertDialog.setMessage(msg);
		alertDialog.show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button button = (Button) findViewById(R.id.LEDToggleBtn);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// change light status
				String status = LEDToggle(ledStatus ? "1" : "0");
				ledStatus = !ledStatus;
				showMsg(status);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
