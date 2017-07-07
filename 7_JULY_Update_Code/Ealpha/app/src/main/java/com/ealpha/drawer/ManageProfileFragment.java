package com.ealpha.drawer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.ealpha.R;
import com.ealpha.cart.OrderHistory;
import com.ealpha.main.MainActivity;
import com.ps.DTO.UserDTO;
import com.ps.utility.JSONParser;
import com.ps.utility.SessionManager;

public class ManageProfileFragment extends Fragment {
	private SessionManager sessionManager;
	private EditText edt_title, edt_fname, edt_lname, edt_email, edt_birth,
			edt_pass;

	String title, fname, lname, email, birthday, password, client_id;

	Button btn_submit, btn_customer_care, btn_orderList;

	private UserDTO userDTO;
	public static ManageProfileFragment myFragment;

	public ManageProfileFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.manage_profile, container,
				false);
		myFragment = this;
		MainActivity.view_pagination_index = 1;
		initialize(rootView);
		return rootView;
	}

	private void initialize(View view) {
		// TODO Auto-generated method stub
		edt_title = (EditText) view.findViewById(R.id.edt_title);
		// edt_title.requestFocus();
		edt_fname = (EditText) view.findViewById(R.id.edt_fname);
		edt_lname = (EditText) view.findViewById(R.id.edt_lname);
		edt_email = (EditText) view.findViewById(R.id.edt_email);
		edt_birth = (EditText) view.findViewById(R.id.edt_birthday);
		edt_pass = (EditText) view.findViewById(R.id.edt_password);

		btn_submit = (Button) view.findViewById(R.id.btn_mp_submit);
		sessionManager = new SessionManager(getActivity());
		if (sessionManager.isLogin()) {
			edt_title.setText(sessionManager.getTitleAndDOB().get(
					SessionManager.KEY_USER_TITLE));
			edt_fname.setText(sessionManager.getUserDetail().getFirst_name());
			edt_lname.setText(sessionManager.getUserDetail().getLast_name());
			edt_email.setText(sessionManager.getUserDetail().getEmail());
			edt_birth.setText(sessionManager.getTitleAndDOB().get(
					SessionManager.KEY_USER_DOB));
			edt_pass.setText(sessionManager.getUserDetail().getPassword());
		}

		btn_customer_care = (Button) view.findViewById(R.id.btn_customer_care);
		btn_customer_care.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent_to_customer = new Intent(getActivity(),
						CustomerCare.class);
				startActivity(intent_to_customer);
				finish();
			}
		});

		btn_orderList = (Button) view.findViewById(R.id.btn_order_detail);
		btn_orderList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent_to_order = new Intent(getActivity(),
						OrderHistory.class);

				startActivity(intent_to_order);

			}
		});
		// date pic
		edt_birth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePickerDialog(getActivity(), date, myCalendar
						.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
						myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		btn_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated methodstub // edit text set errors
				if (edt_title.getText().toString().toString().trim().length() == 0) {
					Toast.makeText(getActivity(), "Enter Title.",
							Toast.LENGTH_SHORT).show();
				} else if (edt_fname.getText().toString().toString().trim()
						.length() == 0) {
					Toast.makeText(getActivity(), "Enter First Name.",
							Toast.LENGTH_SHORT).show();
				} else if (edt_lname.getText().toString().toString().trim()
						.length() == 0) {
					Toast.makeText(getActivity(), "Enter Last Name.",
							Toast.LENGTH_SHORT).show();
				} else if (edt_email.getText().toString().toString().trim()
						.length() == 0) {
					Toast.makeText(getActivity(), "Enter Email.",
							Toast.LENGTH_SHORT).show();
				} else if (edt_birth.getText().toString().toString().trim()
						.length() == 0) {
					Toast.makeText(getActivity(), "Select Birthdate.",
							Toast.LENGTH_SHORT).show();
				} else if (edt_pass.getText().toString().toString().trim()
						.length() == 0) {
					Toast.makeText(getActivity(), "Enter Password.",
							Toast.LENGTH_SHORT).show();
				} else {
					new ManageProfileAsynkTask()
							.execute("http://ealpha.com/mob/customers.php?customers=update_profile");
				}
			}
		});

	}

	// date pic
	Calendar myCalendar = Calendar.getInstance();

	DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			myCalendar.set(Calendar.YEAR, year);
			myCalendar.set(Calendar.MONTH, monthOfYear);
			myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			updateLabel();
		}
	};

	private void updateLabel() {
		String myFormat = "MM/dd/yy"; // In which you need put here
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
		edt_birth.setText(sdf.format(myCalendar.getTime()));
	}

	class ManageProfileAsynkTask extends AsyncTask<String, Void, JSONObject> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generatedmethod stub
			super.onPreExecute();

			dialog = new ProgressDialog(getActivity());
			dialog.setMessage("please wait ..");
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();

		}

		@Override
		protected JSONObject doInBackground(String... args) {
			// TODO Auto-generated method stub
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("title", edt_title.getText()
					.toString().trim()));
			params.add(new BasicNameValuePair("firstname", edt_fname.getText()
					.toString().trim()));
			params.add(new BasicNameValuePair("lastname", edt_lname.getText()
					.toString().trim()));
			params.add(new BasicNameValuePair("email", edt_email.getText()
					.toString().trim()));
			params.add(new BasicNameValuePair("birthday", edt_birth.getText()
					.toString().trim()));
			params.add(new BasicNameValuePair("password", sessionManager
					.getUserDetail().getPassword()));
			params.add(new BasicNameValuePair("new_password", edt_pass
					.getText().toString().trim()));
			params.add(new BasicNameValuePair("id_customer", sessionManager
					.getUserDetail().getCustomer_id()));
			JSONObject json = new JSONParser().makeHttpRequest2(args[0],
					"POST", params);
			return json;

		}

		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			super.onPostExecute(jsonObject);
			String vStatus = "";
			String vMessage = "";
			dialog.dismiss();
			try {
				System.out.println("update_profile_response..."
						+ jsonObject.toString());
				JSONObject profile_update_object = jsonObject
						.getJSONObject("profile_update");
				vStatus = profile_update_object.getString("status");
				vMessage = profile_update_object.getString("message");
				if (vStatus.trim().equals("Success")) {
					userDTO = new UserDTO();
					userDTO.setCustomer_id(sessionManager.getUserDetail()
							.getCustomer_id());
					userDTO.setFirst_title(edt_title.getText().toString()
							.trim());
					userDTO.setFirst_name(edt_fname.getText().toString().trim());
					userDTO.setLast_name(edt_lname.getText().toString().trim());
					userDTO.setEmail(edt_email.getText().toString().trim());
					userDTO.setBirthday(edt_birth.getText().toString().trim());
					userDTO.setPassword(edt_pass.getText().toString().trim());
					sessionManager.setUserDetail(userDTO);
					sessionManager.login();
					sessionManager.setTitleAndDOB(edt_title.getText()
							.toString().trim(), edt_birth.getText().toString()
							.trim());
					Toast.makeText(getActivity(), "Profile Updated.",
							Toast.LENGTH_SHORT).show();
					if (MainActivity.mainActivity != null) {
						MainActivity.mainActivity.addDrawerItems();
					}
				} else if (vStatus.trim().equals("Error")) {
					Toast.makeText(getActivity(), "" + vMessage,
							Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {

			}
		}
	}

	public static String toTitleCase(String givenString) {
		String[] arr = givenString.split("");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			try {
				sb.append(Character.toUpperCase(arr[i].charAt(0)))
						.append(arr[i].substring(1)).append("");
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		return sb.toString().trim();
	}

	/*
	 * public void setProfileData() { if (sessionManager.isLogin()) {
	 * edt_title.setText(sessionManager.getUserDetail().getFirst_title());
	 * edt_fname.setText(sessionManager.getUserDetail().getFirst_name());
	 * edt_lname.setText(sessionManager.getUserDetail().getLast_name());
	 * 
	 * edt_email.setText(sessionManager.getUserDetail().getEmail());
	 * edt_pass.setText(sessionManager.getUserDetail().getPassword());
	 * edt_birth.setText(sessionManager.getUserDetail().getBirthday());
	 * 
	 * }
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Zeero/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {

		case android.R.id.home:
			// app icon in action bar clicked; go home
			this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void finish() {
		// TODO Auto-generated method stub

	}

}
