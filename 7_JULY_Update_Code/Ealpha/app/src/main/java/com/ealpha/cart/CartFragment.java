package com.ealpha.cart;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ealpha.R;
import com.ealpha.homeclick.ProductDetailActivityPP;
import com.ealpha.main.MainActivity;
import com.ps.DTO.CartsDTO;
import com.ps.utility.JSONParser;
import com.ps.utility.SessionManager;

public class CartFragment extends Fragment {
	private String vProduct_ID = "";
	private int qty = 1;
	private ListView cart_list;
	private ArrayList<CartsDTO> cartsDTOs;
	private SessionManager sessionManager;
	public static CartFragment myCartFragment;
	public static StringBuilder vProduct_ids = new StringBuilder();
	public static float total_amount = 0.00f;
	public static String total_amount_intent;
	private CartAdapter cartListAdapter;
	private int position_final = -1;
	private TextView total_cart, items_count;
	Button place_order_cart;
	public static String total_cart_items = "", product_id = "",
			product_quantity = "", id_address_delivery = "",
			id_address_invoice = "", id_cart = "", total_discount = "",
			shipping_charge = "", free_shipping = "";

	public CartFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.cartlist, container, false);
		getActivity().setTitle("eAlpha Cart");
		myCartFragment = this;
		MainActivity.view_pagination_index = 1;
		place_order_cart = (Button) rootView
				.findViewById(R.id.place_order_cart);
		place_order_cart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (cartsDTOs.size() > 0) {
					total_amount_intent = "0";
					total_cart_items = "";
					try {
						totalAmountCalculate();
						total_amount_intent = cartsDTOs.get(0).getTotal_price()
								+ "";
					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						id_cart = cartsDTOs.get(0).getId_cart() + "";
						System.out.println("krishna ji..." + id_cart);
					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						total_cart_items = cartsDTOs.size() + "";
					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						product_id = cartsDTOs.get(0).getId_product() + "";
					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						product_quantity = cartsDTOs.get(0).getQuantity() + "";
					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						id_address_delivery = sessionManager.getCustomeDetail()
								.getId_address_delivery();
					} catch (Exception e) {
						// TODO: handle exception
					}
					try {
						id_address_invoice = sessionManager.getCustomeDetail()
								.getId_address_invoice();
					} catch (Exception e) {
						// TODO: handle exception
					}
					Intent place_order_cart_intent = new Intent(getActivity(),
							ShippingAddress.class);
					startActivity(place_order_cart_intent);
				} else {
					Toast.makeText(getActivity(), "No items in your cart.",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		initialize(rootView);
		return rootView;
	}

	private void initialize(View view) {
		// TODO Auto-generated method stub
		cart_list = (ListView) view.findViewById(R.id.cart_list);
		total_cart = (TextView) view.findViewById(R.id.total_cart);
		items_count = (TextView) view.findViewById(R.id.items_count);
		setData();
	}

	public void setData() {
		sessionManager = new SessionManager(getActivity());
		try {
			cartsDTOs = sessionManager.getCartsDTOs();
		} catch (Exception e) {
		}
		if (cartsDTOs == null) {
			cartsDTOs = new ArrayList<>();
		}
		if (sessionManager.isLogin()) {
			if (cartsDTOs.size() > 0) {
				total_amount = 0.00f;
				cartListAdapter = new CartAdapter(getActivity(), cartsDTOs);
				cart_list.setAdapter(cartListAdapter);
				items_count.setText("Items(" + cartsDTOs.size() + ")");
				totalAmountCalculate();
				new getCartAsyncTask()
						.execute("http://www.ealpha.com/mob/customers.php?customers=view_cart&id_customer="
								+ sessionManager.getUserDetail()
										.getCustomer_id());
			} else {
				Toast.makeText(getActivity(), "No items in your Cart.",
						Toast.LENGTH_SHORT).show();
				cartListAdapter = new CartAdapter(getActivity(), cartsDTOs);
				cart_list.setAdapter(cartListAdapter);
				items_count.setText("Items(" + cartsDTOs.size() + ")");
				totalAmountCalculate();
			}
		} else {
			Toast.makeText(getActivity(), "User not logged in.",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void totalAmountCalculate() {
		total_amount = 0.00f;
		vProduct_ids = new StringBuilder();
		for (int i = 0; i < cartsDTOs.size(); i++) {
			System.out.println("ttt..." + cartsDTOs.get(i).getTotal_price());
			try {
				total_amount = total_amount
						+ Integer.parseInt(cartsDTOs.get(i).getTotal_price());
			} catch (Exception e) {

			}
			vProduct_ids.append(cartsDTOs.get(i).getId_product() + ",");
		}
		total_cart.setText("Total Rs.: " + total_amount);
	}

	@Override
	public void onResume() {
		super.onResume();
		totalAmountCalculate();
	}

	public void removeAction(int position) {
		position_final = position;
		vProduct_ID = cartsDTOs.get(position).getId_product();
		qty = cartsDTOs.get(position).getQuantity();
		deletefromCartList().show();
	}

	public void cartQtyIncrenment(int position) {
		int qty = cartsDTOs.get(position).getQuantity();
		vProduct_ID = cartsDTOs.get(position).getId_product();
		cartsDTOs.get(position).getQuantity();
		qty++;
		cartsDTOs.get(position).setQuantity(qty);
		cartsDTOs.get(position).setTotal_price(
				Integer.parseInt(cartsDTOs.get(position).getUnit_price()) * qty
						+ "");
		sessionManager.setCartsDTOs(cartsDTOs);
		cartListAdapter.notifyDataSetChanged();
		totalAmountCalculate();
		new addToCartAsyncTask()
				.execute("http://www.ealpha.com/mob/customers.php?customers=add_to_cart");
	}

	public void cartQtyDescrenment(int position) {
		int qty = cartsDTOs.get(position).getQuantity();
		vProduct_ID = cartsDTOs.get(position).getId_product();
		if (qty == 1) {
			return;
		}
		qty--;
		cartsDTOs.get(position).setQuantity(qty);
		cartsDTOs.get(position).setTotal_price(
				Integer.parseInt(cartsDTOs.get(position).getUnit_price()) * qty
						+ "");
		sessionManager.setCartsDTOs(cartsDTOs);
		cartListAdapter.notifyDataSetChanged();
		totalAmountCalculate();
		new addToCartAsyncTask()
				.execute("http://www.ealpha.com/mob/customers.php?customers=remove_add_to_cart");
	}

	public void detailFromCart(int position) {
		Intent intent_popular_grid = new Intent(getActivity(),
				ProductDetailActivityPP.class);
		intent_popular_grid.putExtra("product_link_popular",
				cartsDTOs.get(position).getProduct_link());
		startActivity(intent_popular_grid);
	}

	public AlertDialog deletefromCartList() {
		AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getActivity())
				// set message, title, and icon
				.setTitle("ealpha")
				.setMessage("Are you sure, to remove from the cart ?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.dismiss();
								if (sessionManager.isLogin()) {
									cartsDTOs.remove(position_final);
									sessionManager.setCartsDTOs(cartsDTOs);
									cartListAdapter.notifyDataSetChanged();
									totalAmountCalculate();
									Toast.makeText(getActivity(),
											"Item Removed from cart.",
											Toast.LENGTH_SHORT).show();
									if (MainActivity.mainActivity != null) {
										MainActivity.mainActivity
												.setBadgeOnCartTest();
									}
									new RemoveCartAsyncTask()
											.execute("http://www.ealpha.com/mob/customers.php?customers=remove_add_to_cart");
								} else {
									Toast.makeText(getActivity(),
											"User not logged in.",
											Toast.LENGTH_SHORT).show();
								}
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).create();
		return myQuittingDialogBox;
	}

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

	class getCartAsyncTask extends AsyncTask<String, Void, JSONObject> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject json = new JSONParser().makeHttpRequest2(args[0], "GET",
					params);
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			super.onPostExecute(jsonObject);
			String vStatus = "", vCart_Id = "";

			try {
				System.out.println("get_all_cart..." + jsonObject.toString());
				JSONObject customer_view_cart = jsonObject
						.getJSONObject("customer_view_cart");
				vStatus = customer_view_cart.getString("status");
				if (vStatus.trim().equals("Success")) {
					JSONObject cart_dataObject = customer_view_cart
							.getJSONObject("message")
							.getJSONObject("cart_data");
					vCart_Id = cart_dataObject.getString("id_cart");
					if (cartsDTOs != null) {
						if (cartsDTOs.size() > 0) {
							if (cartsDTOs.get(0).getId_cart().length() == 0) {
								cartsDTOs = sessionManager.getCartsDTOs();
								cartsDTOs.get(0).setId_cart(vCart_Id);
								sessionManager.setCartsDTOs(cartsDTOs);
								try {
									id_cart = cartsDTOs.get(0).getId_cart()
											+ "";
									System.out.println("Radha ji..." + id_cart);
									System.out.println("customer_iddd"
											+ cartsDTOs);

								} catch (Exception e) {
									// TODO: handle exception
								}
							}
						}
					}
				}
			} catch (Exception e) {

			}
		}
	}

	class addToCartAsyncTask extends AsyncTask<String, Void, JSONObject> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id_customer", sessionManager
					.getUserDetail().getCustomer_id()));
			params.add(new BasicNameValuePair("id_product", vProduct_ID));
			params.add(new BasicNameValuePair("quantity", "1"));
			System.out.println("params..." + params.toString());
			JSONObject json = new JSONParser().makeHttpRequest2(args[0],
					"POST", params);
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			super.onPostExecute(jsonObject);
			System.out.println(jsonObject.toString());
		}
	}

	class RemoveCartAsyncTask extends AsyncTask<String, Void, JSONObject> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id_customer", sessionManager
					.getUserDetail().getCustomer_id()));
			params.add(new BasicNameValuePair("id_product", vProduct_ID));
			params.add(new BasicNameValuePair("quantity", qty + ""));
			System.out.println("params..." + params.toString());
			JSONObject json = new JSONParser().makeHttpRequest2(args[0],
					"POST", params);
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			super.onPostExecute(jsonObject);
			System.out.println(jsonObject.toString());
		}
	}

}
