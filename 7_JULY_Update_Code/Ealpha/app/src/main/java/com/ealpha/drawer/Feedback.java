package com.ealpha.drawer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ealpha.R;
import com.ealpha.main.MainActivity;
import com.ps.utility.SessionManager;

public class Feedback extends Activity {

	EditText email;
	EditText feedback;
	Button submit;

	String str_name, str_mail, str_feedback;

	SessionManager session;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		android.app.ActionBar bar = getActionBar();
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(
				R.color.header)));
		session = new SessionManager(getApplicationContext());

		// make message text field object
		email = (EditText) findViewById(R.id.et_mail_feed);
		feedback = (EditText) findViewById(R.id.et_feedback_feed);
		// make button object
		submit = (Button) findViewById(R.id.btn_sumit_feed);
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (session.isLogin()) {
					submitData();
				} else {
					Toast.makeText(getApplicationContext(), "Login first !",
							Toast.LENGTH_SHORT).show();
				}
			}

			@SuppressWarnings({ "resource" })
			protected void submitData() {

				str_mail = email.getText().toString();
				str_feedback = feedback.getText().toString();

				if (TextUtils.isEmpty(email.getText().toString())) {
					email.setError(getResources().getString(
							R.string.email_is_required));
				} else if (!Function.isEmailValid(email.getText().toString())) {
					email.setError(getString(R.string.error_invalid_email));

				} else if (TextUtils.isEmpty(str_feedback)) {
					feedback.setError(getResources().getString(
							R.string.error_field_required));
					Toast.makeText(getApplicationContext(), "vacant feild",
							Toast.LENGTH_SHORT).show();
				} else {

					/*
					 * ProgressDialog pDialog = new
					 * ProgressDialog(FeedBack.this);
					 * pDialog.setMessage("Loading..");
					 * pDialog.setIndeterminate(false);
					 * pDialog.setCancelable(true); pDialog.show();
					 */
					try {
						@SuppressWarnings({})
						HttpClient httpclient = new DefaultHttpClient();
						HttpPost httppost = new HttpPost(
								"http://ealpha.com/mob/customers.php?customers=feedback");
						httppost.addHeader("Content-Type",
								"application/x-www-form-urlencoded");
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
								2);
						// Building Parameters

						nameValuePairs.add(new BasicNameValuePair("email",
								str_mail));
						nameValuePairs.add(new BasicNameValuePair("message",
								str_feedback));

						InputStream is = null;
						StringBuilder sb = null;
						String result = null; // getting JSON string from URL
						httppost.setEntity(new UrlEncodedFormEntity(
								nameValuePairs, HTTP.UTF_8));
						HttpResponse response = null;
						try {
							response = httpclient.execute(httppost);

							// write response to log
							Log.d("Http Post Response:", response.toString());
						} catch (ClientProtocolException e) {
							// Log exception
							e.printStackTrace();
						} catch (IOException e) {
							// Log exception
							e.printStackTrace();
						}
						HttpEntity entity = response.getEntity();
						is = entity.getContent();

						try {
							BufferedReader reader = new BufferedReader(
									new InputStreamReader(is, "iso-8859-1"), 8);
							sb = new StringBuilder();
							sb.append(reader.readLine() + "\n");
							String line = "0";

							while ((line = reader.readLine()) != null) {
								sb.append(line + "\n");
							}

							is.close();
							result = sb.toString();

						} catch (Exception e) {
							Log.e("log_tag",
									"Error converting result " + e.toString());
						}

						Toast.makeText(getApplicationContext(),
								"Feedback Submitted", Toast.LENGTH_LONG).show();
						Intent intent_f = new Intent(Feedback.this,
								MainActivity.class);
						startActivity(intent_f);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.feed_back, menu);
		return true;
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
