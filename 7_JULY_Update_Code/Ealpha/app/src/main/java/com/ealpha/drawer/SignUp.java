package com.ealpha.drawer;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ealpha.R;
import com.ealpha.main.MainActivity;
import com.ps.DTO.UserDTO;
import com.ps.utility.JSONParser;
import com.ps.utility.SessionManager;

public class SignUp extends Activity {
	EditText fname1, lname1, email1, password1;
	Button btnsubmit;
	String email_mail, name, title, fname, lname, email, phone, city, state,
			status, country, password, client_id;
	JSONObject json;
	JsonParser jParser = new JsonParser();
	TextView Already_Registred_Login_Me;
	CheckBox check_showpass_signup;
	private SessionManager sessionManager;
	private UserDTO userDTO;

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);
		android.app.ActionBar bar = getActionBar();
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(
				R.color.header)));
		sessionManager = new SessionManager(this);
		btnsubmit = (Button) findViewById(R.id.btn_submit_signup);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		fname1 = (EditText) findViewById(R.id.edit_text_fname);
		lname1 = (EditText) findViewById(R.id.edit_text_lastname);
		email1 = (EditText) findViewById(R.id.edit_text_email);
		password1 = (EditText) findViewById(R.id.edit_text_password);

		check_showpass_signup = (CheckBox) findViewById(R.id.check_showpass_signup);
		if (!check_showpass_signup.isChecked()) {
			password1.setTransformationMethod(PasswordTransformationMethod
					.getInstance());
		} else {
			password1.setTransformationMethod(HideReturnsTransformationMethod
					.getInstance());

		}
		check_showpass_signup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (!isChecked) {
							// show password
							password1
									.setTransformationMethod(PasswordTransformationMethod
											.getInstance());
						} else {
							// hide password
							password1
									.setTransformationMethod(HideReturnsTransformationMethod
											.getInstance());
						}

					}
				});

		Already_Registred_Login_Me = (TextView) findViewById(R.id.existing_user_sign_in);
		Already_Registred_Login_Me.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i4 = new Intent(getBaseContext(), SignUp.class);
				startActivity(i4);
				finish();
			}
		});

		btnsubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// edit text set errors
				fname1.setError(null);
				lname1.setError(null);
				email1.setError(null);
				password1.setError(null);

				fname = fname1.getText().toString();
				lname = lname1.getText().toString();
				email_mail = email1.getText().toString();
				password = password1.getText().toString();

				// fname
				if (TextUtils.isEmpty(fname)) {
					fname1.setError(getResources().getString(
							R.string.error_field_required));

				} else if (TextUtils.isEmpty(lname)) {
					lname1.setError(getResources().getString(
							R.string.error_field_required));
				} else if (TextUtils.isEmpty(email1.getText().toString())) {
					email1.setError(getResources().getString(
							R.string.email_is_required));
				} else if (!Function.isEmailValid(email1.getText().toString())) {
					email1.setError(getString(R.string.error_invalid_email));
				} else if (TextUtils.isEmpty(password)) {
					password1.setError(getResources().getString(
							R.string.error_field_required));

				} else if (password.length() < 3) {
					password1.setError(getString(R.string.error_invalid));
				} else if (password.length() > 12) {
					password1
							.setError(getString(R.string.error_invalid_password));
				} else {
					new SignUpAsyncTask()
							.execute("http://ealpha.com/mob/customers.php?customers=customers");
				}
			}
		});
	}

	class SignUpAsyncTask extends AsyncTask<String, Void, JSONObject> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(SignUp.this);
			dialog.setMessage("Please Wait...");
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("customer_firstname", fname));
			params.add(new BasicNameValuePair("customer_lastname", lname));
			params.add(new BasicNameValuePair("email", email_mail));
			params.add(new BasicNameValuePair("passwd", password));
			JSONObject json = new JSONParser().makeHttpRequest2(args[0],
					"POST", params);
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			super.onPostExecute(jsonObject);
			String vStatus = "", vMessage = "not created.";
			dialog.dismiss();
			try {
				JSONObject customer_login_object = null;
				try {
					// {"customer_registration":{"message":"New Client Created","status":"Success","customer_id":43}}
					System.out.println("response..." + jsonObject.toString());
					customer_login_object = jsonObject
							.getJSONObject("customer_registration");
					vStatus = customer_login_object.getString("status");
					vMessage = customer_login_object.getString("message");
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (vStatus.trim().equals("Success")) {
					Toast.makeText(SignUp.this, "New Client Created.",
							Toast.LENGTH_SHORT).show();
					userDTO = new UserDTO();
					userDTO.setFirst_name(fname);
					userDTO.setLast_name(lname);
					userDTO.setEmail(email_mail);
					userDTO.setPassword(password);
					userDTO.setCustomer_id(customer_login_object
							.getString("customer_id"));
					sessionManager.setUserDetail(userDTO);
					sessionManager.login();
					startActivity(new Intent(SignUp.this, MainActivity.class));
					finish();
					if (MainActivity.mainActivity != null) {
						MainActivity.mainActivity.finish();
					}
				} else {
					Toast.makeText(SignUp.this, vMessage, Toast.LENGTH_SHORT)
							.show();
				}
			} catch (Exception e) {
				System.out.println("gggg..." + e.toString());
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			try {
				InputMethodManager inputManager = (InputMethodManager) SignUp.this
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				inputManager.hideSoftInputFromWindow(SignUp.this
						.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
			} catch (Exception e) {

			}
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
