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
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ealpha.R;
import com.ealpha.main.MainActivity;
import com.ps.utility.SessionManager;

@SuppressWarnings("deprecation")
public class ForgotPassword extends Activity {

	EditText email_forgotpassword;
	Button done;
	String email_mail;
	JSONObject json;
	JsonParser jParser = new JsonParser();
	SessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgotpassword);
		android.app.ActionBar bar = getActionBar();
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(
				R.color.header)));

		done = (Button) findViewById(R.id.btn_done);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		email_forgotpassword = (EditText) findViewById(R.id.edt_forgot_email);

		done.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				email_mail = email_forgotpassword.getText().toString();

				if (TextUtils
						.isEmpty(email_forgotpassword.getText().toString())) {
					email_forgotpassword.setError(getResources().getString(
							R.string.email_is_required));
				} else if (!Function.isEmailValid(email_forgotpassword
						.getText().toString())) {
					email_forgotpassword
							.setError(getString(R.string.error_invalid_email));

				} else {
					/*
					 * ProgressDialog pDialog = new ProgressDialog(
					 * ForgotPassword.this); pDialog.setMessage("Loading..");
					 * pDialog.setIndeterminate(false);
					 * pDialog.setCancelable(true); pDialog.show();
					 */
					try {
						@SuppressWarnings("resource")
						HttpClient httpclient = new DefaultHttpClient();
						HttpPost httppost = new HttpPost(
								"http://ealpha.com/mob/customers.php?customers=forget_password");
						httppost.addHeader("Content-Type",
								"application/x-www-form-urlencoded");
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
								1);
						// Building Parameters

						nameValuePairs.add(new BasicNameValuePair("email",
								email_mail));

						InputStream is = null;
						StringBuilder sb = null;
						String result = null;
						// getting JSON string from URL
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

						Toast.makeText(getApplicationContext(), result,
								Toast.LENGTH_LONG).show();
						Intent intent = new Intent(getApplicationContext(),
								MainActivity.class);
						finish();
						startActivity(intent);
					} catch (Exception e) {
						e.printStackTrace();

					}
				}

			}

		});
	}

}
