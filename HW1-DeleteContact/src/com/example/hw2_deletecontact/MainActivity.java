package com.example.hw2_deletecontact;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	final Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button button = (Button) findViewById(R.id.delContactButton);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// get person name
				final String personName = ((EditText) findViewById(R.id.personName))
						.getText().toString();

				String where = ContactsContract.Data.DISPLAY_NAME + " = ? ";
				String[] params = new String[] { personName };

				ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
				ops.add(ContentProviderOperation
						.newDelete(ContactsContract.RawContacts.CONTENT_URI)
						.withSelection(where, params).build());
				try {
					getContentResolver().applyBatch(ContactsContract.AUTHORITY,
							ops);
					AlertDialog alertDialog;
					alertDialog = new AlertDialog.Builder(context).create();
					alertDialog.setTitle("Contact Deleted!");
					StringBuilder sb = new StringBuilder();
					sb.append("Name: ");
					sb.append(personName);
					alertDialog.setMessage(sb);
					alertDialog.show();
				} catch (Exception e) {
					System.out.println(e.getStackTrace());
				}
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
