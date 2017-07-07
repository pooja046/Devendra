package com.ealpha.wishlist;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.ealpha.R;
import com.ealpha.homeclick.ProductDetailActivityPP;
import com.ealpha.main.MainActivity;
import com.ps.DTO.CartsDTO;
import com.ps.DTO.ProductGetterSetter;
import com.ps.utility.JSONParser;
import com.ps.utility.SessionManager;

public class WishListFragment extends Fragment {
	private ListView wish_list;
	private ArrayList<ProductGetterSetter> wishArrayList;
	private ProductGetterSetter wish_data;
	private int position_final = -1;
	private WishListAdapter wishListAdapter;
	private String vProduct_id = "", vCustomer_id = "1";
	private SessionManager sessionManager;
	public static WishListFragment myWishListFragment;

	public WishListFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.wishlist, container, false);
		MainActivity.view_pagination_index = 1;
		initialize(rootView);
		return rootView;
	}

	private void initialize(View view) {
		// TODO Auto-generated method stub
		wish_list = (ListView) view.findViewById(R.id.wish_list);
		myWishListFragment = this;
		sessionManager = new SessionManager(getActivity());
		if (sessionManager.isLogin()) {
			vCustomer_id = sessionManager.getUserDetail().getCustomer_id();
			new getAllWishListAsyncTask()
					.execute("http://www.ealpha.com/mob/customers.php?customers=wishlist_view");
		} else {
			Toast.makeText(getActivity(), "User not logged in.",
					Toast.LENGTH_SHORT).show();
			vCustomer_id = "1";
		}
	}

	class getAllWishListAsyncTask extends AsyncTask<String, Void, JSONObject> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(getActivity());
			dialog.setMessage("Please Wait...");
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id_customer", vCustomer_id));
			JSONObject json = new JSONParser().makeHttpRequest2(args[0],
					"POST", params);
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			super.onPostExecute(jsonObject);
			String vStatus = "", vMessage = "No Wishlist Data Available.";
			JSONArray wishlist_data_array = null;
			dialog.dismiss();
			try {
				wishArrayList = new ArrayList<ProductGetterSetter>();
				try {
					System.out.println("response..." + jsonObject.toString());
					JSONObject customer_wishlist_object = jsonObject
							.getJSONObject("view_wishlist_data");
					vStatus = customer_wishlist_object.getString("status");
					wishlist_data_array = customer_wishlist_object
							.getJSONObject("message").getJSONArray(
									"wishlist_data");
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (vStatus.trim().equals("Success")) {
					try {
						int total_data = 0;
						if (wishlist_data_array.length() == 0) {
							total_data = 0;
						} else {
							total_data = wishlist_data_array.length() - 1;
						}
						JSONObject wishlist_data_object = wishlist_data_array
								.getJSONObject(total_data);
						try {
							JSONArray product_image_array = wishlist_data_object
									.getJSONArray("product_image");
							JSONArray product_link_array = wishlist_data_object
									.getJSONArray("product_link");
							JSONArray product_name_array = wishlist_data_object
									.getJSONArray("product_name");
							JSONArray price_array = wishlist_data_object
									.getJSONArray("price");
							JSONArray availability = wishlist_data_object
									.getJSONArray("quantity");
							for (int j = 0; j < product_image_array.length(); j++) {
								wish_data = new ProductGetterSetter();
								wish_data
										.setId_product(product_link_array
												.getString(j)
												.replace(
														"http://ealpha.com//mob/customers.php?get_data=product_data&product_id=",
														""));
								wish_data.setImage(product_image_array
										.getString(j));
								wish_data.setPdt_link(product_link_array
										.getString(j));
								wish_data.setName(product_name_array
										.getString(j));
								wish_data.setRs(""
										+ (int) Float.parseFloat(price_array
												.getString(j)));
								wish_data.setAvailability(availability
										.getString(j));
								wish_data
										.setAdd_to_cart(isProductAddedIntoCart(product_link_array
												.getString(j)
												.replace(
														"http://ealpha.com//mob/customers.php?get_data=product_data&product_id=",
														"")));
								wishArrayList.add(wish_data);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
						// }
					} catch (Exception e) {
						// TODO: handle exception
					}
				} else {
					Toast.makeText(getActivity(), vMessage, Toast.LENGTH_SHORT)
							.show();
				}
			} catch (Exception e) {
				System.out.println("gggg..." + e.toString());
			}
			if (MainActivity.mainActivity != null) {
//				MainActivity.mainActivity
//						.setBadgeOnCart(MainActivity.wish_p_ids.size());
			}
			wishListAdapter = new WishListAdapter(getActivity(), wishArrayList);
			wish_list.setAdapter(wishListAdapter);
		}
	}

	public void removeAction(int position) {
		position_final = position;
		vProduct_id = wishArrayList.get(position).getId_product();
		MainActivity.wish_p_ids.remove(vProduct_id);
		MainActivity.wish_p_ids = new ArrayList<String>(
				new LinkedHashSet<String>(MainActivity.wish_p_ids));
		deletefromWishList().show();
	}

	public void detailFromWishList(int position) {
		// p_potition = position;
		Intent intent_popular_grid = new Intent(getActivity(),
				ProductDetailActivityPP.class);
		intent_popular_grid.putExtra("product_link_popular",
				wishArrayList.get(position).getPdt_link());
		startActivity(intent_popular_grid);
	}

	public void addToCartAction(int position) {
		position_final = position;
		vProduct_id = wishArrayList.get(position).getId_product();
		if (wishArrayList.get(position).isAdd_to_cart()) {
			wishArrayList.get(position).setAdd_to_cart(false);
			wishListAdapter.notifyDataSetChanged();
			new addToCartAsyncTask()
					.execute("http://www.ealpha.com/mob/customers.php?customers=remove_add_to_cart&id_customer="
							+ sessionManager.getUserDetail().getCustomer_id()
							+ "&id_product=" + vProduct_id);
		} else {
			MainActivity.cartsDTO = new CartsDTO();
			System.out.println("id_product...."
					+ wishArrayList.get(position).getId_product());
			System.out.println("product_name...."
					+ wishArrayList.get(position).getName());
			System.out.println("unit_price...."
					+ wishArrayList.get(position).getRs());
			System.out.println("product_img...."
					+ wishArrayList.get(position).getImage());
			System.out.println("product_link...."
					+ wishArrayList.get(position).getPdt_link());
			System.out.println("availability...."
					+ wishArrayList.get(position).getAvailability());
			MainActivity.cartsDTO.setId_product(wishArrayList.get(position)
					.getId_product());
			MainActivity.cartsDTO.setProduct_name(wishArrayList.get(position)
					.getName());
			MainActivity.cartsDTO.setUnit_price(wishArrayList.get(position)
					.getRs());
			MainActivity.cartsDTO.setTotal_price(wishArrayList.get(position)
					.getRs());
			MainActivity.cartsDTO.setProduct_img(wishArrayList.get(position)
					.getImage());
			MainActivity.cartsDTO.setProduct_link(wishArrayList.get(position)
					.getPdt_link());
			MainActivity.cartsDTO.setQuantity(1);
			MainActivity.cartsDTO.setAvailability(wishArrayList.get(position)
					.getAvailability());
			wishArrayList.get(position).setAdd_to_cart(true);
			wishListAdapter.notifyDataSetChanged();
			new addToCartAsyncTask()
					.execute("http://www.ealpha.com/mob/customers.php?customers=add_to_cart&id_customer="
							+ sessionManager.getUserDetail().getCustomer_id()
							+ "&id_product=" + vProduct_id + "&quantity=1");
		}
	}

	public boolean isProductAddedIntoCart(String vProduct_ID) {
		System.out.println("vProduct_ID..." + vProduct_ID);
		boolean isAddedInoCart = false;
		ArrayList<CartsDTO> cartsDTOs = new ArrayList<CartsDTO>();
		try {
			cartsDTOs = sessionManager.getCartsDTOs();
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (cartsDTOs == null) {
			cartsDTOs = new ArrayList<>();
		}
		System.out.println("cartsDTOs..." + cartsDTOs.size());
		for (int i = 0; i < cartsDTOs.size(); i++) {
			if (cartsDTOs.get(i).getId_product().equals(vProduct_ID)) {
				isAddedInoCart = true;
				break;
			}
		}
		return isAddedInoCart;
	}

	private void removeProductFromCart(String vProduct_idNew) {
		// TODO Auto-generated method stub
		ArrayList<CartsDTO> cartsDTOs = new ArrayList<CartsDTO>();
		try {
			cartsDTOs = sessionManager.getCartsDTOs();
		} catch (Exception e) {
			// TODO: handle exception
		}
		for (int i = 0; i < cartsDTOs.size(); i++) {
			if (cartsDTOs.get(i).getId_product().equals(vProduct_idNew)) {
				cartsDTOs.remove(i);
				break;
			}
		}
		sessionManager.setCartsDTOs(cartsDTOs);
	}

	public AlertDialog deletefromWishList() {
		AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getActivity())
				// set message, title, and icon
				.setTitle("ealpha")
				.setMessage("Are you sure, to remove from the wish list ?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.dismiss();
								if (sessionManager.isLogin()) {
									new deleteFromWishListAsyncTask()
											.execute("http://www.ealpha.com/mob/customers.php?customers=wishlist_remove");
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

	class addToCartAsyncTask extends AsyncTask<String, Void, JSONObject> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			JSONObject json = new JSONParser().makeHttpRequest2(args[0],
					"POST", params);
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			super.onPostExecute(jsonObject);
			String vStatus = "";
			String vMessage = "";
			try {
				System.out.println("add_to_cart..." + jsonObject.toString());
				JSONObject add_to_cart = null;
				if (jsonObject.has("add_to_cart")) {
					add_to_cart = jsonObject.getJSONObject("add_to_cart");
					vStatus = add_to_cart.getString("status");
					String vCartId = add_to_cart
							.getString("message")
							.replace(
									"Product successfully added to your shopping cart, Your Cart Id Is : ",
									"");
					if (vCartId != null) {
						if (vCartId.trim().length() > 0) {
							MainActivity.cartsDTO.setId_cart(vCartId);
							ArrayList<CartsDTO> cartsDTOs = new ArrayList<>();
							try {
								cartsDTOs = sessionManager.getCartsDTOs();
							} catch (Exception e) {

							}
							if (cartsDTOs == null) {
								cartsDTOs = new ArrayList<>();
							}
							if (cartsDTOs.size() > 0) {
								cartsDTOs = sessionManager.getCartsDTOs();
								cartsDTOs.add(MainActivity.cartsDTO);
								sessionManager.setCartsDTOs(cartsDTOs);
							} else {
								cartsDTOs.add(MainActivity.cartsDTO);
								sessionManager.setCartsDTOs(cartsDTOs);
							}
						} else {
							MainActivity.cartsDTO.setId_cart(vCartId);
							ArrayList<CartsDTO> cartsDTOs = new ArrayList<>();
							try {
								cartsDTOs = sessionManager.getCartsDTOs();
							} catch (Exception e) {

							}
							if (cartsDTOs == null) {
								cartsDTOs = new ArrayList<>();
							}
							if (cartsDTOs.size() > 0) {
								cartsDTOs = sessionManager.getCartsDTOs();
								cartsDTOs.add(MainActivity.cartsDTO);
								sessionManager.setCartsDTOs(cartsDTOs);
							} else {
								cartsDTOs.add(MainActivity.cartsDTO);
								sessionManager.setCartsDTOs(cartsDTOs);
							}
						}
					}
					vMessage = "item added into cart.";
				} else if (jsonObject.has("customer_remove_add_to_cart")) {
					add_to_cart = jsonObject
							.getJSONObject("customer_remove_add_to_cart");
					vMessage = "item removed from cart.";
					vStatus = add_to_cart.getString("status");
					removeProductFromCart(vProduct_id);
				}
				if (vStatus.trim().equals("Success")) {
					Toast.makeText(getActivity(), vMessage, Toast.LENGTH_SHORT)
							.show();
				} else if (vStatus.trim().equals("Error")) {
					Toast.makeText(getActivity(), vMessage, Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(getActivity(), "item not added into cart.",
							Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {

			}
		}
	}

	class deleteFromWishListAsyncTask extends
			AsyncTask<String, Void, JSONObject> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(getActivity());
			dialog.setMessage("Please Wait...");
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id_product", vProduct_id));
			params.add(new BasicNameValuePair("id_customer", sessionManager
					.getUserDetail().getCustomer_id()));
			Log.d("mTitel", "accept invitation params=" + params);
			JSONObject json = new JSONParser().makeHttpRequest2(args[0],
					"POST", params);
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			super.onPostExecute(jsonObject);
			String vStatus = "", vMessage = "not removed.";
			dialog.dismiss();
			try {
				try {
					System.out.println("response..." + jsonObject.toString());
					JSONObject customer_wishlist_object = jsonObject
							.getJSONObject("wishlist_remove");
					vStatus = customer_wishlist_object.getString("status");
					vMessage = customer_wishlist_object.getString("message");
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (vStatus.trim().equals("Success")) {
					Toast.makeText(getActivity(), "Wishlist Removed.",
							Toast.LENGTH_SHORT).show();
					WishListFragment.myWishListFragment.removeFromList();
				} else {
					Toast.makeText(getActivity(), vMessage + ".",
							Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {

			}
			if (MainActivity.mainActivity != null) {
//				MainActivity.mainActivity
//						.setBadgeOnCart(MainActivity.wish_p_ids.size());
			}
		}
	}

	public void removeFromList() {
		wishArrayList.remove(position_final);
		wishListAdapter.notifyDataSetChanged();
	}
}
