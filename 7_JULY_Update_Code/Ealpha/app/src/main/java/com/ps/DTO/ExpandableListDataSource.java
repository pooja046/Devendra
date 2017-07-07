package com.ps.DTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.ealpha.main.MainActivity;
import com.ps.utility.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by msahakyan on 22/10/15.
 */
public class ExpandableListDataSource {
	public static HashMap<String, List<String>> expandableListData;
	public static List<String> drawerItems_Lstr;
	public static HashMap<String, String> keyItems;
	private Context context;

	public static Map<String, List<String>> getData(Context context) {
		return expandableListData;
	}

	public static void getSubCategoryFromServer(Context context) {

		new menuItemsAsynchTask(context)
				.execute("http://www.ealpha.com/mob/customers.php?customers=sub_category");
	}

	static class menuItemsAsynchTask extends
			AsyncTask<String, Void, JSONObject> {
		Context context;

		public menuItemsAsynchTask(Context context) {
			// TODO Auto-generated constructor stub
			this.context = context;
		}

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
			expandableListData = new HashMap<String, List<String>>();
			keyItems = new HashMap<String, String>();
			drawerItems_Lstr = new ArrayList<>();
			drawerItems_Lstr.add("Sign In");
			drawerItems_Lstr.add("Home");
			expandableListData.put("Sign In", new ArrayList<String>());
			expandableListData.put("Home", new ArrayList<String>());
			try {
				System.out.println("menu_items..." + jsonObject.toString());
				JSONArray main_menuArray = jsonObject.getJSONArray("main_menu");
				for (int i = 0; i < main_menuArray.length(); i++) {
					System.out.println("name...."
							+ main_menuArray.getString(i).trim());
					drawerItems_Lstr.add(main_menuArray.getString(i).trim());
				}
			} catch (Exception e) {

			}
			try {
				System.out.println("sub_menu..." + jsonObject.toString());
				JSONObject sub_menuObject = jsonObject
						.getJSONObject("sub_menu");
				JSONObject sub_menu_linkObject = jsonObject
						.getJSONObject("sub_menu_link");

				List<String> subItems = new ArrayList<>();
				for (int i = 2; i < drawerItems_Lstr.size(); i++) {
					subItems = new ArrayList<>();
					JSONArray sub_menuArray = sub_menuObject
							.getJSONArray(drawerItems_Lstr.get(i));
					JSONArray sub_menu_linkArray = sub_menu_linkObject
							.getJSONArray(drawerItems_Lstr.get(i));
					for (int j = 0; j < sub_menuArray.length(); j++) {
						subItems.add(sub_menuArray.getString(j));
						keyItems.put(drawerItems_Lstr.get(i) + "_"
								+ sub_menuArray.getString(j),
								sub_menu_linkArray.getString(j));
					}
					System.out.println(drawerItems_Lstr.get(i) + "...LOVE..."
							+ subItems.size());
					expandableListData.put(drawerItems_Lstr.get(i), subItems);
				}
				expandableListData.put("WishList", new ArrayList<String>());
				expandableListData.put("Cart", new ArrayList<String>());
				expandableListData.put("About Us", new ArrayList<String>());
				expandableListData.put("Log Out", new ArrayList<String>());
				drawerItems_Lstr.add("WishList");
				drawerItems_Lstr.add("Cart");
				drawerItems_Lstr.add("About Us");
				drawerItems_Lstr.add("Log Out");
				System.out.println(drawerItems_Lstr.size() + "...LOVE..."
						+ expandableListData.keySet());
				Intent in = new Intent(context, MainActivity.class);
				context.startActivity(in);
				((Activity) context).finish();
			} catch (Exception e) {

			}
		}
	}
}