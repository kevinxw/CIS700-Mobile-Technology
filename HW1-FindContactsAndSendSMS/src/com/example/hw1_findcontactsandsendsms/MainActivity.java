package com.example.hw1_findcontactsandsendsms;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button button = (Button) findViewById(R.id.sendContactButton);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// get person name
				final String personName = ((EditText) findViewById(R.id.personName))
						.getText().toString();

				String where = ContactsContract.Data.DISPLAY_NAME + " = ? ";
				String[] params = new String[] { personName };

				Cursor people = getContentResolver().query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null, where, params, null);
				if (people.moveToFirst()) { // find at least one record
					String phoneNo = people.getString(people
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					// SmsManager sm = SmsManager.getDefault();
					// sm.sendTextMessage("12345", null, phoneNo, null, null);
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri
							.parse("sms:12345"));
					intent.putExtra("sms_body", phoneNo);
					startActivity(intent);
				}
				people.close();
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
