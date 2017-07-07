package com.ealpha.cart;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ealpha.R;
import com.ealpha.main.MainActivity;
import com.ps.DTO.CartsDTO;
import com.ps.utility.JSONParser;
import com.ps.utility.SessionManager;

public class PaymentOptions extends Activity {
	private SessionManager sessionManager;
	private Button c_o_d, pay_u_money, visa_master_hdfc;
	public static String vSelected_Payment_Gatway = "";
	private String id_address_delivery = "", id_address_invoice = "",
			id_cart = "", id_currency = "", id_lang = "", id_carrier = "",
			current_state = "", valid = "", payment = "", module = "",
			payment_payu = "", module_payu = "", payment_hdfc = "",
			module_hdfc = "", total_paid = "", total_paid_tax_incl = "",
			total_paid_tax_excl = "", total_paid_real = "",
			total_products = "", total_products_wt = "", conversion_rate = "",
			product_id = "", product_quantity = "";
	private boolean is_first_time_invoce = false;
	private String total_discounts = "", shipping_charge = "",
			total_discounts_tax_incl = "", total_discounts_tax_excl = "",
			free_shipping = "", total_shipping = "",
			total_shipping_tax_incl = "", total_shipping_tax_excl = "";

	public static PaymentOptions paymentOptions;
	private TextView total_charge, cod_charge;
	private String total_paid_payu, total_paid_tax_incl_payu,
			total_paid_tax_excl_payu, total_paid_real_payu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payment_options);
		is_first_time_invoce = false;

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		paymentOptions = this;
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		sessionManager = new SessionManager(this);
		c_o_d = (Button) findViewById(R.id.c_o_d);
		total_charge = (TextView) findViewById(R.id.total_charge);
		cod_charge = (TextView) findViewById(R.id.cod_charge);

		c_o_d.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vSelected_Payment_Gatway = "";
				if (!is_first_time_invoce) {
					new getAsynkTaskForCod()
							.execute("http://ealpha.com/webservice/order.php?action=add");
				}
			}
		});

		pay_u_money = (Button) findViewById(R.id.pay_u_money);
		pay_u_money.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) { // TODO Auto-generated method
				vSelected_Payment_Gatway = "PAYU";
				if (is_first_time_invoce) {
					new getInvoiceDataByOrderID()
							.execute("http://ealpha.com/webservice/order.php?action=get");
				} else {
					new getAsynkTaskForPayU()
							.execute("http://ealpha.com/webservice/order.php?action=add");
				}
			}
		});

		visa_master_hdfc = (Button) findViewById(R.id.visa_master_card);

		visa_master_hdfc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vSelected_Payment_Gatway = "HDFC";
				if (is_first_time_invoce) {
					new getInvoiceDataByOrderID()
							.execute("http://ealpha.com/webservice/order.php?action=get");
				} else {
					new getAsynkTaskForHdfc()
							.execute("http://ealpha.com/webservice/order.php?action=add");
				}
			}
		});
		setData();
		/*
		 * setData2payu(); setData3hdfc();
		 */
	}

	public void setData() {
		// these r for cod
		id_address_delivery = CartFragment.id_address_delivery;
		id_address_invoice = CartFragment.id_address_invoice;
		id_cart = CartFragment.id_cart;
		id_currency = "1";
		id_lang = "1";
		id_carrier = "89";
		current_state = "3";
		valid = "1";
		payment = "Cashondeliveryplus(COD+)";
		module = "cashondeliveryplus";
		// payu
		payment_payu = "PayUMoney Checkout";
		module_payu = "payu";

		// hdfc
		payment_hdfc = "Hdfc ePayment Gateway";
		module_hdfc = "hdfcepg";
		// 6 paras

		total_products = CartFragment.total_amount_intent;
		total_products_wt = CartFragment.total_amount_intent;

		// /

		conversion_rate = "1";
		product_id = CartFragment.product_id;
		product_quantity = CartFragment.product_quantity;

		// add 6 new paras

		if (CartFragment.free_shipping != null) {
			if (CartFragment.free_shipping.trim().length() == 0) {
				CartFragment.free_shipping = "0";
			}
		} else {
			CartFragment.free_shipping = "0";
		}
		if (CartFragment.shipping_charge != null) {
			if (CartFragment.shipping_charge.trim().length() == 0) {
				CartFragment.shipping_charge = "0";
			}
		} else {
			CartFragment.shipping_charge = "0";
		}

		total_paid = (CartFragment.total_amount + Integer
				.parseInt(CartFragment.shipping_charge)) + "";
		total_paid_tax_incl = (CartFragment.total_amount + Integer
				.parseInt(CartFragment.shipping_charge)) + "";
		total_paid_tax_excl = (CartFragment.total_amount + Integer
				.parseInt(CartFragment.shipping_charge)) + "";
		total_paid_real = (CartFragment.total_amount + Integer
				.parseInt(CartFragment.shipping_charge)) + "";
		//
		// Only for payu and hdfc
		total_paid_payu = (CartFragment.total_amount) + "";
		total_paid_tax_incl_payu = (CartFragment.total_amount) + "";
		total_paid_tax_excl_payu = (CartFragment.total_amount) + "";
		total_paid_real_payu = (CartFragment.total_amount) + "";
		// /
		// /
		total_charge.setText("The total amount of your order is Rs. "
				+ (CartFragment.total_amount + Integer
						.parseInt(CartFragment.shipping_charge)));
		cod_charge.setText("This includes COD fee Rs. "
				+ CartFragment.shipping_charge);

		total_shipping = (Integer.parseInt(CartFragment.free_shipping) + Integer
				.parseInt(CartFragment.shipping_charge)) + "";
		total_shipping_tax_incl = (Integer.parseInt(CartFragment.free_shipping) + Integer
				.parseInt(CartFragment.shipping_charge)) + "";
		total_shipping_tax_excl = (Integer.parseInt(CartFragment.free_shipping) + Integer
				.parseInt(CartFragment.shipping_charge)) + "";

		total_discounts = CartFragment.total_discount;
		total_discounts_tax_incl = CartFragment.total_discount;
		total_discounts_tax_excl = CartFragment.total_discount;

	}

	class getAsynkTaskForCod extends AsyncTask<String, Void, JSONObject> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new ProgressDialog(PaymentOptions.this);
			dialog.setMessage("processing");
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			// TODO Auto-generated method stub

			// http://demo.ealpha.com/webservice/order.php?action=add&id_customer=2&id_address_delivery=5&id_address_invoice=5&id_cart=14&id_currency=1&id_lang=1&id_carrier=10�t_state=1&valid=1&payment=Cashondeliveryplus(COD+)&module=cashondeliveryplus&total_paid=299&total_paid_tax_incl=299&total_paid_tax_excl=299&total_paid_real=299&total_products=299&total_products_wt=299&conversion_rate=1&product_id=9&product_quantity=3

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("id_customer", sessionManager
					.getUserDetail().getCustomer_id()));

			System.out.println("Cod is happing ..");
			params.add(new BasicNameValuePair("id_address_delivery",
					id_address_delivery));
			params.add(new BasicNameValuePair("id_address_invoice",
					id_address_invoice));
			params.add(new BasicNameValuePair("id_cart", id_cart));
			params.add(new BasicNameValuePair("id_currency", id_currency));
			params.add(new BasicNameValuePair("id_lang", id_lang));
			params.add(new BasicNameValuePair("id_carrier", id_carrier));
			params.add(new BasicNameValuePair("current_state", current_state));
			params.add(new BasicNameValuePair("valid", valid));
			params.add(new BasicNameValuePair("payment", payment));
			params.add(new BasicNameValuePair("module", module));
			params.add(new BasicNameValuePair("total_paid", total_paid));
			params.add(new BasicNameValuePair("total_paid_tax_incl",
					total_paid_tax_incl));
			params.add(new BasicNameValuePair("total_paid_tax_excl",
					total_paid_tax_excl));
			params.add(new BasicNameValuePair("total_paid_real",
					total_paid_real));
			params.add(new BasicNameValuePair("total_products", total_products));
			params.add(new BasicNameValuePair("total_products_wt",
					total_products_wt));
			params.add(new BasicNameValuePair("conversion_rate",
					conversion_rate));
			params.add(new BasicNameValuePair("product_id", product_id));
			params.add(new BasicNameValuePair("product_quantity",
					product_quantity));

			// 6 new params
			params.add(new BasicNameValuePair("total_shipping ", total_shipping));
			params.add(new BasicNameValuePair("total_shipping_tax_incl",
					total_shipping_tax_incl));
			params.add(new BasicNameValuePair("total_shipping_tax_excl",
					total_shipping_tax_excl));

			params.add(new BasicNameValuePair("total_discounts",
					total_discounts));
			params.add(new BasicNameValuePair("total_discounts_tax_incl",
					total_discounts_tax_incl));
			params.add(new BasicNameValuePair("total_discounts_tax_excl",
					total_discounts_tax_excl));
			// /

			System.out.println("params.." + params.toString());
			JSONObject json = new JSONParser().makeHttpRequest2(args[0],
					"POST", params);
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			// TODO Auto-generated method stub
			super.onPostExecute(jsonObject);
			dialog.dismiss();
			String vStatus = "";
			String vOrder_id = "";
			try {
				System.out.println("order responce..." + jsonObject.toString());
				if (jsonObject.has("order_data")) {
					JSONObject orderDataObject = jsonObject
							.getJSONObject("order_data");
					vStatus = orderDataObject.getString("status");
					vOrder_id = orderDataObject.getJSONObject("Order Id")
							.getString("0");
					if (vStatus.equals("success")) {
						Toast.makeText(
								PaymentOptions.this,
								"Order_Id:= " + vOrder_id
										+ "\nOrder Added Successfully",
								Toast.LENGTH_SHORT).show();
						is_first_time_invoce = true;
						sessionManager.setOrderID(vOrder_id);
						sessionManager.setCartsDTOs(new ArrayList<CartsDTO>());
						MainActivity.cart_refresh_status = 2;
						if (ShippingAddress.shippingAddress != null) {
							MainActivity.home_from_order_complete = 2;
							finish();
							ShippingAddress.shippingAddress.finish();
						}
						if (vSelected_Payment_Gatway.trim().equals("PAYU")) {
							if (sessionManager.getOrderID().trim().length() > 0) {
								new getInvoiceDataByOrderID()
										.execute("http://ealpha.com/webservice/order.php?action=get");
							}
						} else if (vSelected_Payment_Gatway.trim().equals(
								"HDFC")) {
							if (sessionManager.getOrderID().trim().length() > 0) {
								new getInvoiceDataByOrderID()
										.execute("http://ealpha.com/webservice/order.php?action=get");
							}

						}
					}

				}
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(PaymentOptions.this,
						"getting null responce from server...",
						Toast.LENGTH_SHORT).show();
			}

		}
	}

	class getAsynkTaskForPayU extends AsyncTask<String, Void, JSONObject> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new ProgressDialog(PaymentOptions.this);
			dialog.setMessage("processing");
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			// TODO Auto-generated method stub

			// http://demo.ealpha.com/webservice/order.php?action=add&id_customer=2&id_address_delivery=5&id_address_invoice=5&id_cart=14&id_currency=1&id_lang=1&id_carrier=10�t_state=1&valid=1&payment=Cashondeliveryplus(COD+)&module=cashondeliveryplus&total_paid=299&total_paid_tax_incl=299&total_paid_tax_excl=299&total_paid_real=299&total_products=299&total_products_wt=299&conversion_rate=1&product_id=9&product_quantity=3

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("id_customer", sessionManager
					.getUserDetail().getCustomer_id()));

			System.out.println("Cod is happing ..");
			params.add(new BasicNameValuePair("id_address_delivery",
					id_address_delivery));
			params.add(new BasicNameValuePair("id_address_invoice",
					id_address_invoice));
			params.add(new BasicNameValuePair("id_cart", id_cart));
			params.add(new BasicNameValuePair("id_currency", id_currency));
			params.add(new BasicNameValuePair("id_lang", id_lang));
			params.add(new BasicNameValuePair("id_carrier", id_carrier));
			params.add(new BasicNameValuePair("current_state", current_state));
			params.add(new BasicNameValuePair("valid", valid));
			params.add(new BasicNameValuePair("payment", payment_payu));
			params.add(new BasicNameValuePair("module", module_payu));
			params.add(new BasicNameValuePair("total_paid", total_paid_payu));
			params.add(new BasicNameValuePair("total_paid_tax_incl",
					total_paid_tax_incl_payu));
			params.add(new BasicNameValuePair("total_paid_tax_excl",
					total_paid_tax_excl_payu));
			params.add(new BasicNameValuePair("total_paid_real",
					total_paid_real_payu));
			params.add(new BasicNameValuePair("total_products", total_products));
			params.add(new BasicNameValuePair("total_products_wt",
					total_products_wt));
			params.add(new BasicNameValuePair("conversion_rate",
					conversion_rate));
			params.add(new BasicNameValuePair("product_id", product_id));
			params.add(new BasicNameValuePair("product_quantity",
					product_quantity));
			System.out.println("params payU.." + params.toString());
			JSONObject json = new JSONParser().makeHttpRequest2(args[0],
					"POST", params);
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			// TODO Auto-generated method stub
			super.onPostExecute(jsonObject);
			dialog.dismiss();
			String vStatus = "";
			String vOrder_id = "";
			try {
				System.out.println("order responce..." + jsonObject.toString());
				if (jsonObject.has("order_data")) {
					JSONObject orderDataObject = jsonObject
							.getJSONObject("order_data");
					vStatus = orderDataObject.getString("status");
					vOrder_id = orderDataObject.getJSONObject("Order Id")
							.getString("0");
					if (vStatus.equals("success")) {
						Toast.makeText(
								PaymentOptions.this,
								"Order_Id:= " + vOrder_id
										+ "\nOrder Added Successfully",
								Toast.LENGTH_SHORT).show();
						is_first_time_invoce = true;
						sessionManager.setOrderID(vOrder_id);
						sessionManager.setCartsDTOs(new ArrayList<CartsDTO>());
						MainActivity.cart_refresh_status = 2;
						if (ShippingAddress.shippingAddress != null) {
							ShippingAddress.shippingAddress.finish();
						}
						if (vSelected_Payment_Gatway.trim().equals("PAYU")) {
							if (sessionManager.getOrderID().trim().length() > 0) {
								new getInvoiceDataByOrderID()
										.execute("http://ealpha.com/webservice/order.php?action=get");
							}
						} else if (vSelected_Payment_Gatway.trim().equals(
								"HDFC")) {
							if (sessionManager.getOrderID().trim().length() > 0) {
								new getInvoiceDataByOrderID()
										.execute("http://ealpha.com/webservice/order.php?action=get");
							}
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(PaymentOptions.this,
						"getting null responce from server...",
						Toast.LENGTH_SHORT).show();
			}

		}
	}

	class getAsynkTaskForHdfc extends AsyncTask<String, Void, JSONObject> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new ProgressDialog(PaymentOptions.this);
			dialog.setMessage("processing");
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			// TODO Auto-generated method stub

			// http://demo.ealpha.com/webservice/order.php?action=add&id_customer=2&id_address_delivery=5&id_address_invoice=5&id_cart=14&id_currency=1&id_lang=1&id_carrier=10�t_state=1&valid=1&payment=Cashondeliveryplus(COD+)&module=cashondeliveryplus&total_paid=299&total_paid_tax_incl=299&total_paid_tax_excl=299&total_paid_real=299&total_products=299&total_products_wt=299&conversion_rate=1&product_id=9&product_quantity=3

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("id_customer", sessionManager
					.getUserDetail().getCustomer_id()));

			System.out.println("Cod is happing ..");
			params.add(new BasicNameValuePair("id_address_delivery",
					id_address_delivery));
			params.add(new BasicNameValuePair("id_address_invoice",
					id_address_invoice));
			params.add(new BasicNameValuePair("id_cart", id_cart));
			params.add(new BasicNameValuePair("id_currency", id_currency));
			params.add(new BasicNameValuePair("id_lang", id_lang));
			params.add(new BasicNameValuePair("id_carrier", id_carrier));
			params.add(new BasicNameValuePair("current_state", current_state));
			params.add(new BasicNameValuePair("valid", valid));
			params.add(new BasicNameValuePair("payment", payment_hdfc));
			params.add(new BasicNameValuePair("module", module_hdfc));
			params.add(new BasicNameValuePair("total_paid", total_paid_payu));
			params.add(new BasicNameValuePair("total_paid_tax_incl",
					total_paid_tax_incl_payu));
			params.add(new BasicNameValuePair("total_paid_tax_excl",
					total_paid_tax_excl_payu));
			params.add(new BasicNameValuePair("total_paid_real",
					total_paid_real_payu));
			params.add(new BasicNameValuePair("total_products", total_products));
			params.add(new BasicNameValuePair("total_products_wt",
					total_products_wt));
			params.add(new BasicNameValuePair("conversion_rate",
					conversion_rate));
			params.add(new BasicNameValuePair("product_id", product_id));
			params.add(new BasicNameValuePair("product_quantity",
					product_quantity));
			System.out.println("params.." + params.toString());
			JSONObject json = new JSONParser().makeHttpRequest2(args[0],
					"POST", params);
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			// TODO Auto-generated method stub
			super.onPostExecute(jsonObject);
			dialog.dismiss();
			String vStatus = "";
			String vOrder_id = "";
			try {
				System.out.println("order responce..." + jsonObject.toString());
				if (jsonObject.has("order_data")) {
					JSONObject orderDataObject = jsonObject
							.getJSONObject("order_data");
					vStatus = orderDataObject.getString("status");
					vOrder_id = orderDataObject.getJSONObject("Order Id")
							.getString("0");
					if (vStatus.equals("success")) {
						Toast.makeText(
								PaymentOptions.this,
								"Order_Id:= " + vOrder_id
										+ "\nOrder Added Successfully",
								Toast.LENGTH_SHORT).show();
						is_first_time_invoce = true;
						sessionManager.setOrderID(vOrder_id);
						sessionManager.setCartsDTOs(new ArrayList<CartsDTO>());
						MainActivity.cart_refresh_status = 2;
						if (ShippingAddress.shippingAddress != null) {
							ShippingAddress.shippingAddress.finish();
						}
						if (vSelected_Payment_Gatway.trim().equals("PAYU")) {
							if (sessionManager.getOrderID().trim().length() > 0) {
								new getInvoiceDataByOrderID()
										.execute("http://ealpha.com/webservice/order.php?action=get");
							}
						} else if (vSelected_Payment_Gatway.trim().equals(
								"HDFC")) {
							if (sessionManager.getOrderID().trim().length() > 0) {
								new getInvoiceDataByOrderID()
										.execute("http://ealpha.com/webservice/order.php?action=get");
							}
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(PaymentOptions.this,
						"getting null responce from server...",
						Toast.LENGTH_SHORT).show();
			}

		}
	}

	class getInvoiceDataByOrderID extends AsyncTask<String, Void, JSONObject> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new ProgressDialog(PaymentOptions.this);
			dialog.setMessage("processing");
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			// TODO Auto-generated method stub
			// http://demo.ealpha.com/webservice/order.php?action=get&order_id=6
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("order_id", sessionManager
					.getOrderID()));
			System.out.println("params.." + params.toString());
			JSONObject json = new JSONParser().makeHttpRequest2(args[0],
					"POST", params);
			return json;
		}

		@SuppressLint("SetJavaScriptEnabled")
		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			// TODO Auto-generated method stub
			super.onPostExecute(jsonObject);
			dialog.dismiss();
			String vStatus = "";
			System.out.println("responding..." + jsonObject.toString());
			try {
				if (jsonObject.has("order_data")) {
					JSONObject order_dataObject = jsonObject
							.getJSONObject("order_data");
					vStatus = order_dataObject.getString("status");
					if (vStatus.trim().equals("success")) {
						JSONObject orderObjcet = order_dataObject
								.getJSONObject("detail").getJSONObject("order");
						String vinvoice_number = orderObjcet
								.getString("invoice_number");
						System.out.println("vinvoice_number.."
								+ vinvoice_number);
						Toast.makeText(PaymentOptions.this,
								"invoice_number:=" + vinvoice_number,
								Toast.LENGTH_SHORT).show();
						if (vSelected_Payment_Gatway.trim().equals("PAYU")) {
							// Intent i = new Intent(Intent.ACTION_VIEW);
							String customer_firstname = sessionManager
									.getCustomeDetail().getCust_fname();
							String email = sessionManager.getUserDetail()
									.getEmail();
							String product_info = "this is product details";
							String customer_phone = sessionManager
									.getCustomeDetail().getCust_mobile_no();
							String order_id = sessionManager.getOrderID();
							/*
							 * String vParam =
							 * "http://www.ealpha.com/PayU/PayUMoney_form.php?ap_invoice_id="
							 * + vinvoice_number + "&ap_amount=" + total_paid +
							 * "&customer_firstname=" + customer_firstname +
							 * "&email=" + email + "&product_info=" +
							 * product_info + "&customer_phone=" +
							 * customer_phone + "&order_id=" + order_id;
							 * System.out.println("vParam....." +
							 * vParam.toString()); i.setData(Uri.parse(vParam));
							 * startActivity(i);
							 */
							//
							WebView webview = new WebView(PaymentOptions.this);
							webview.getSettings().setJavaScriptEnabled(true);

							setContentView(webview);
							String url = "http://www.ealpha.com/PayU/PayUMoney_form.php?ap_invoice_id=";
							String postData = "ap_invoice_id="
									+ vinvoice_number + "&ap_amount="
									+ total_paid_payu + "&customer_firstname="
									+ customer_firstname + "&email=" + email
									+ "&product_info=" + product_info
									+ "&customer_phone=" + customer_phone
									+ "&order_id=" + order_id;

							/*
							 * + "order_id" + URLEncoder.encode(order_id, "") +
							 * "invoice_number" +
							 * URLEncoder.encode(vinvoice_number, "")
							 */
							;

							webview.postUrl(url, postData.getBytes());

						} else if (vSelected_Payment_Gatway.trim().equals(
								"HDFC")) {
							Intent i = new Intent(Intent.ACTION_VIEW);
							i.setData(Uri
									.parse("http://www.ealpha.com/HDFC/HdfcSslAPI.php?ap_invoice_id="
											+ vinvoice_number
											+ "&ap_amount="
											+ total_paid_payu));
							startActivity(i);
						}
					}
				}
			} catch (Exception e) {

			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
