package com.example.hw2;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	final Context context = this;

	static {
		System.loadLibrary("read-write-file");
	}

	// this is for this JNI
	public native String rwFileContent(String fileName, String appendTxt);

	// show an alert box
	private void showMsg(String msg) {
		AlertDialog alertDialog;
		alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle("Something happened!");
		alertDialog.setMessage(msg);
		alertDialog.show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button button = (Button) findViewById(R.id.readAndWriteFile);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String fileName = ((EditText) findViewById(R.id.fileName))
						.getText().toString(), editFileContent = ((EditText) findViewById(R.id.editFileContent))
						.getText().toString();
				// when file name or file content is null
				if (fileName.equals("") || editFileContent.equals("")) {
					showMsg("File name and edit content must not be empty!");
					return;
				}
				TextView fileContent = (TextView) findViewById(R.id.fileContent);
				String content = rwFileContent(fileName, editFileContent);
				if (content.equals(""))
					fileContent
							.setText("FILE WRITING ERROR, OR PERMISSION DENIED!");
				else
					fileContent.setText(content);
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
