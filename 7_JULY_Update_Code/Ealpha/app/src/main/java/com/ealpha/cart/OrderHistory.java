package com.ealpha.cart;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ealpha.R;
import com.ps.DTO.OrderHistoryDTO;
import com.ps.utility.JSONParser;
import com.ps.utility.SessionManager;

public class OrderHistory extends Activity {
	private SessionManager sessionManager;
	private ListView order_history_list;
	private ArrayList<OrderHistoryDTO> orderArrayList;
	private OrderHistoryAdapter orderHistoryAdapter;
	private OrderHistoryDTO orderHistoryDTO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orders_history);
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		sessionManager = new SessionManager(OrderHistory.this);
		order_history_list = (ListView) findViewById(R.id.order_list);
		orderArrayList = new ArrayList<OrderHistoryDTO>();
		orderHistoryAdapter = new OrderHistoryAdapter(this, orderArrayList);
		order_history_list.setAdapter(orderHistoryAdapter);
		order_history_list
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView,
							View view, int position, long l) {
						if (orderArrayList != null && orderArrayList.size() > 0)
							startActivity(new Intent(OrderHistory.this,
									OrderHistoryDetails.class).putExtra(
									"ORDER_ID", orderArrayList.get(position)
											.getId_()));
					}
				});
		new getAllOrderHistoryListAsyncTask()
				.execute("http://ealpha.com/webservice/order.php?action=get");
	}

	class getAllOrderHistoryListAsyncTask extends
			AsyncTask<String, Void, JSONObject> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new ProgressDialog(OrderHistory.this);
			dialog.setMessage("Please Wait...");
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			// TODO Auto-generated method stub
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id_customer", sessionManager
					.getUserDetail().getCustomer_id()));
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
			orderArrayList = new ArrayList<OrderHistoryDTO>();
			try {
				System.out.println("order_response..." + jsonObject.toString());
				JSONObject order_dataObjcet = jsonObject
						.getJSONObject("order_data");
				vStatus = order_dataObjcet.getString("status");
				if (vStatus.trim().equals("success")) {
					JSONObject orderJSONObject = null;
					try {
						orderJSONObject = order_dataObjcet
								.getJSONObject("detail")
								.getJSONObject("orders").getJSONObject("order");
						orderHistoryDTO = new OrderHistoryDTO();
						orderHistoryDTO.setId_(orderJSONObject.getJSONObject(
								"@attributes").getString("id"));
						orderArrayList.add(orderHistoryDTO);
					} catch (Exception e) {

					}
					JSONArray orderJSONArray = null;
					try {
						orderJSONArray = order_dataObjcet
								.getJSONObject("detail")
								.getJSONObject("orders").getJSONArray("order");
						for (int i = 0; i < orderJSONArray.length(); i++) {
							orderHistoryDTO = new OrderHistoryDTO();
							orderHistoryDTO.setId_(orderJSONArray
									.getJSONObject(i)
									.getJSONObject("@attributes")
									.getString("id"));
							orderArrayList.add(orderHistoryDTO);
						}

					} catch (Exception e) {

					}
				} else {
					Toast.makeText(OrderHistory.this, "Order not found.",
							Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				// TODO: handle exception

			}
			if (orderArrayList != null) {
				if (orderArrayList.size() == 0) {
					Toast.makeText(OrderHistory.this, "Order not found.",
							Toast.LENGTH_SHORT).show();
				}
			}
			orderHistoryAdapter = new OrderHistoryAdapter(OrderHistory.this,
					orderArrayList);
			order_history_list.setAdapter(orderHistoryAdapter);
		}
	}
}
