package com.ealpha.cart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ealpha.R;
import com.ealpha.main.MainActivity;
import com.ps.utility.JSONParser;

public class OrderHistoryDetails extends Activity {
	private TextView order_id, invoice_number, reference, invoice_date,
			total_paid, payment, delivery_date;
	private Button btn_continue_shopping;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orders_history_row);
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		order_id = (TextView) findViewById(R.id.order_id);
		// invoice_number = (TextView) findViewById(R.id.invoice_number);
		reference = (TextView) findViewById(R.id.reference);
		invoice_date = (TextView) findViewById(R.id.invoice_date);
		delivery_date = (TextView) findViewById(R.id.delivery_date);
		total_paid = (TextView) findViewById(R.id.total_paid_real);
		payment = (TextView) findViewById(R.id.payment);
		// product_name = (TextView) findViewById(R.id.product_name);
		btn_continue_shopping = (Button) findViewById(R.id.btn_continue_shopping);
		btn_continue_shopping.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent intent_toHome = new Intent(getApplicationContext(),
						MainActivity.class);
				intent_toHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent_toHome);

				finish();
			}
		});
		try {
			if (getIntent().getStringExtra("ORDER_ID") != null) {
				if (getIntent().getStringExtra("ORDER_ID").length() > 0) {
					System.out.println("ORDER_ID..."
							+ getIntent().getStringExtra("ORDER_ID"));
					new getOrderDetalsByOrderIdAsyncTask()
							.execute("http://ealpha.com/webservice/order.php?action=get&order_id="
									+ getIntent().getStringExtra("ORDER_ID"));
				}
			}
		} catch (Exception e) {

		}
	}

	class getOrderDetalsByOrderIdAsyncTask extends
			AsyncTask<String, Void, JSONObject> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new ProgressDialog(OrderHistoryDetails.this);
			dialog.setMessage("Please Wait...");
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			// TODO Auto-generated method stub
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject json = new JSONParser().makeHttpRequest2(args[0],
					"POST", params);
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			// TODO Auto-generated method stub
			super.onPostExecute(jsonObject);
			String vStatus = "";
			dialog.dismiss();
			try {
				System.out.println("order_response..." + jsonObject.toString());
				JSONObject order_dataObjcet = jsonObject
						.getJSONObject("order_data");
				vStatus = order_dataObjcet.getString("status");
				if (vStatus.trim().equals("success")) {
					JSONObject orderObject = order_dataObjcet.getJSONObject(
							"detail").getJSONObject("order");
					order_id.setText(orderObject.getString("id"));
					reference.setText(orderObject.getString("reference"));
					try {
						invoice_date.setText(orderObject
								.getString("invoice_date"));
					} catch (Exception e) {

					}

					try {
						delivery_date.setText(orderObject
								.getString("delivery_date"));
					} catch (Exception e) {

					}
					try {
						total_paid.setText("Rs: "
								+ (int) Float.parseFloat(orderObject
										.getString("total_paid")));
					} catch (Exception e) {

					}
					try {
						payment.setText(orderObject.getString("payment"));
					} catch (Exception e) {

					}
					try {
						invoice_number.setText(""
								+ (int) Float.parseFloat(orderObject
										.getString("invoice_number")));
					} catch (Exception e) {

					}

				} else {
					Toast.makeText(OrderHistoryDetails.this,
							"Order not found.", Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				// TODO: handle exception

			}
		}
	}

	public String getFormatedDate(String vDateG) {
		String vDate = vDateG;
		try {
			SimpleDateFormat sourceFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat destinationFormat = new SimpleDateFormat(
					"dd MMM yyyy hh:mm:ss a");

			Date date = sourceFormat.parse(vDateG);
			vDate = destinationFormat.format(date.getTime());
		} catch (Exception e) {

		}
		return vDate;
	}
}
