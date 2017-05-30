package com.ps.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.ealpha.main.Splash;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ps.DTO.AddressDTO;
import com.ps.DTO.CartColorSizeDTO;
import com.ps.DTO.CartsDTO;
import com.ps.DTO.UserDTO;

@SuppressLint("CommitPrefEdits")
public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    // Shared Preferences
    SharedPreferences pref_globel;
    Editor editor;
    // Context
    Context _context;

    // Sharedpref file name
    public static final String PREF_NAME_GLOBEL = "Ealpha_DB_GLOBEL";

    // Editor for Shared preferences
    Editor editor_globel;

    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Sharedpref file name
    public static final String PREF_NAME = "Ealpha_DB";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLogin";
    private static final String USER_DETAIL = "user_info";
    private static final String WISH_LIST_IDS = "wish_list_ids";
    private static final String CART_LIST_IDS = "cart_list_ids";
    private static final String CUSTOMER_DETAIL = "customer_info";
    private static final String ADDRESS_INFO_LIST = "address_info_list";
    private static final String CARTS_DATA = "carts_data";
    private static final String KEY_ORDER_ID = "order_id";
    public static final String KEY_USER_TITLE = "user_title";
    public static final String KEY_USER_DOB = "user_dbo";
    private static final String OFF_LINE_DATA = "off_line_data";
    private static final String OFF_LINE_TITLE = "off_line_titles";
    public static String UNINSTALL_PREF_DATA = "";

    // Constructor
    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        pref_globel = _context.getSharedPreferences(PREF_NAME_GLOBEL,
                PRIVATE_MODE);
        editor_globel = pref_globel.edit();
        editor = pref.edit();
    }

	/*
     * public void setUserDetail2(ShippingAddressDto2 shippingAddressDto2) { //
	 * Storing login value as TRUE Gson gson = new Gson(); String vUserDTO =
	 * gson.toJson(shippingAddressDto2); editor.putString(USER_DETAIL,
	 * vUserDTO); // commit changes editor.commit(); }
	 */

    public void setUserDetail(UserDTO userDTO) {
        // Storing login value as TRUE
        Gson gson = new Gson();
        String vUserDTO = gson.toJson(userDTO);
        editor.putString(USER_DETAIL, vUserDTO);
        // commit changes
        editor.commit();
    }

    public UserDTO getUserDetail() {
        Gson gson = new Gson();
        String vjson = pref.getString(USER_DETAIL, "");
        UserDTO userDTO = gson.fromJson(vjson, UserDTO.class);
        return userDTO;
    }

    public void setCustomerDetail(AddressDTO addressDTO) {
        // Storing login value as TRUE
        Gson gson = new Gson();
        String vUserDTO = gson.toJson(addressDTO);
        editor.putString(CUSTOMER_DETAIL, vUserDTO);
        // commit changes
        editor.commit();
    }

    public AddressDTO getCustomeDetail() {
        Gson gson = new Gson();
        String vjson = pref.getString(CUSTOMER_DETAIL, "");
        AddressDTO userDTO = gson.fromJson(vjson, AddressDTO.class);
        return userDTO;
    }

    public void setAddressList(ArrayList<AddressDTO> addressDTOs) {
        // Storing login value as TRUE
        Gson gson = new Gson();
        String vSelectedGroupDTO = gson.toJson(addressDTOs);
        editor.putString(ADDRESS_INFO_LIST, vSelectedGroupDTO);
        editor.commit();
    }

    @SuppressWarnings("unchecked")
    public ArrayList<AddressDTO> getAddressList() {
        Gson gson = new Gson();
        String vjson = pref.getString(ADDRESS_INFO_LIST, "");
        java.lang.reflect.Type listType = new TypeToken<ArrayList<AddressDTO>>() {
        }.getType();
        ArrayList<AddressDTO> wishListids = (ArrayList<AddressDTO>) gson
                .fromJson(vjson, listType);
        return wishListids;
    }

    public void setWishListIds(ArrayList<String> wishListids) {
        // Storing login value as TRUE
        Gson gson = new Gson();
        String vSelectedGroupDTO = gson.toJson(wishListids);
        editor.putString(WISH_LIST_IDS, vSelectedGroupDTO);
        editor.commit();
    }

    @SuppressWarnings("unchecked")
    public ArrayList<String> getWishListIds() {
        Gson gson = new Gson();
        String vjson = pref.getString(WISH_LIST_IDS, "");
        java.lang.reflect.Type listType = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> wishListids = (ArrayList<String>) gson.fromJson(
                vjson, listType);
        return wishListids;
    }

    public void setCartsDTOs(ArrayList<CartsDTO> cartsDTOs) {
        // Storing login value as TRUE
        Gson gson = new Gson();
        String vSelectedGroupDTO = gson.toJson(cartsDTOs);
        editor.putString(CARTS_DATA, vSelectedGroupDTO);
        editor.commit();
    }

    public ArrayList<CartsDTO> getCartsDTOs() {
        Gson gson = new Gson();
        String vjson = pref.getString(CARTS_DATA, "");
        java.lang.reflect.Type listType = new TypeToken<ArrayList<CartsDTO>>() {
        }.getType();
        ArrayList<CartsDTO> cartsDTOs = (ArrayList<CartsDTO>) gson.fromJson(
                vjson, listType);
        return cartsDTOs;
    }

    // set headers values for each request...
    public void setOrderID(String vOrder_id) {
        editor.putString(KEY_ORDER_ID, vOrder_id);
        editor.commit();
    }

    public String getOrderID() {
        return pref.getString(KEY_ORDER_ID, "");
    }

    public void setCartColorSizeListIds(
            ArrayList<CartColorSizeDTO> cartColorSizeDTOs) {
        // Storing login value as TRUE
        Gson gson = new Gson();
        String vSelectedGroupDTO2 = gson.toJson(cartColorSizeDTOs);
        editor.putString(CART_LIST_IDS, vSelectedGroupDTO2);
        editor.commit();
    }

    @SuppressWarnings("unchecked")
    public ArrayList<CartColorSizeDTO> getCartColorSizeListIds() {
        Gson gson = new Gson();
        String vjson2 = pref.getString(CART_LIST_IDS, "");
        java.lang.reflect.Type listType = new TypeToken<ArrayList<CartColorSizeDTO>>() {
        }.getType();
        ArrayList<CartColorSizeDTO> cartListids = (ArrayList<CartColorSizeDTO>) gson
                .fromJson(vjson2, listType);
        return cartListids;
    }

    // login session...
    public void login() {
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    /**
     * Quick check for Install *
     */
    // Get Installed State...
    public boolean isLogin() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void logOut() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, Splash.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        _context.startActivity(i);
    }

    // set headers values for each request...
    public void setTitleAndDOB(String vTitle, String vDOB) {
        editor_globel.putString(KEY_USER_TITLE, vTitle);
        editor_globel.putString(KEY_USER_DOB, vDOB);
        editor_globel.commit();
    }

    public HashMap<String, String> getTitleAndDOB() {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put(KEY_USER_TITLE, pref_globel.getString(KEY_USER_TITLE, ""));
        headers.put(KEY_USER_DOB, pref_globel.getString(KEY_USER_DOB, ""));
        return headers;
    }


    public void setOffLineTitles(
            List<String> offLineData) {
        Gson gson = new Gson();
        String vOffLineData = gson.toJson(offLineData);
        editor_globel.putString(OFF_LINE_TITLE, vOffLineData);
        editor_globel.commit();
    }

    public List<String> getOffLineTitle() {
        Gson gson = new Gson();
        String vjson = pref_globel.getString(OFF_LINE_TITLE, "");
        java.lang.reflect.Type listType = new TypeToken<List<String>>() {
        }.getType();
        List<String> offLineData = (List<String>) gson.fromJson(
                vjson, listType);
        return offLineData;
    }


    public void setOffLineData(
            Map<String, List<String>> offLineData) {
        Gson gson = new Gson();
        String vOffLineData = gson.toJson(offLineData);
        editor_globel.putString(OFF_LINE_DATA, vOffLineData);
        editor_globel.commit();
    }

    public Map<String, List<String>> getOffLineData() {
        Gson gson = new Gson();
        String vjson = pref_globel.getString(OFF_LINE_DATA, "");
        java.lang.reflect.Type listType = new TypeToken<Map<String, List<String>>>() {
        }.getType();
        Map<String, List<String>> offLineData = (Map<String, List<String>>) gson.fromJson(
                vjson, listType);
        return offLineData;
    }

    public String getUninstallData() {
        return UNINSTALL_PREF_DATA = "{\"main_menu\":[\"Men\",\"Women\",\"Kid\",\"Health Care\",\"Mobiles & Tablets\",\"Electronics\",\"Home Decor & Furnishing\",\"Home Utility\",\"Sairandhri\",\"Spiritual Items\",\"Food Zone\",\"Special Sale\"],\"sub_menu\":{\"Men\":[\"Clothing\",\"Accessories\",\"Footwear\",\"Personal Care\",\"Winter Wear\"],\"Women\":[\"Personal Care\",\"Ethnic Wear\",\"Accessories\",\"Footwear\",\"Western Wear\",\"Winter Wear\",\"Night Wears\",\"Froks\"],\"Kid\":[\"Boys Apparel\",\"Boys Footware\",\"Girls Apparel\",\"Girls Footwear\",\"Accessories\",\"Sports\",\"Baby Care\"],\"Health Care\":[\"Bath & Body\",\"Bioscience\",\"Baby Care\"],\"Mobiles & Tablets\":[\"Mobile Phones\",\"Mobile Accessories\",\"Tablets\"],\"Electronics\":[\"Home & Kitchen Appliances\",\"Computers & Accessories\",\"Others\"],\"Home Decor & Furnishing\":[\"Home Furnishing\",\"Home Decor & Gifting Ideas\"],\"Home Utility\":[\"Crockery\",\"Cookware\",\"Kitchen Storage\",\"Dinner Ware\",\"Utility Products\",\"Car Accesories\"],\"Sairandhri\":[\"Bridal Saree\",\"Bridal Suit\",\"Bridal Lahengas\",\"Sarees\",\"Suits\",\"Lahengas\",\"Kurti\",\"Gowns\",\"Indo Western\",\"Printed\",\"Tunics\"],\"Spiritual Items\":[\"Yantra\",\"Rudraksha\",\"Gemstones\",\"Jap Mala\",\"Other\",\"Deity Dresses\"],\"Food Zone\":[\"Candy\"],\"Special Sale\":[\"Sale Under 199\",\"Sale Under 299\",\"Sale Under 399\",\"Sale Under 499\",\"Deal Of the Week\"]},\"main_menu_link\":{\"Men\":[\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=206&start_limit=0&end_limit=20\"],\"Women\":[\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=200&start_limit=0&end_limit=20\"],\"Kid\":[\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=222&start_limit=0&end_limit=20\"],\"Health Care\":[\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=248&start_limit=0&end_limit=20\"],\"Mobiles & Tablets\":[\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=500&start_limit=0&end_limit=20\"],\"Electronics\":[\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=153&start_limit=0&end_limit=20\"],\"Home Decor & Furnishing\":[\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=28&start_limit=0&end_limit=20\"],\"Home Utility\":[\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=464&start_limit=0&end_limit=20\"],\"Sairandhri\":[\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=600&start_limit=0&end_limit=20\"],\"Spiritual Items\":[\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=616&start_limit=0&end_limit=20\"],\"Food Zone\":[\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=612&start_limit=0&end_limit=20\"],\"Special Sale\":[\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=634&start_limit=0&end_limit=20\"]},\"sub_menu_link\":{\"Men\":[\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=207&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=208&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=209&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=369&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=584&start_limit=0&end_limit=20\"],\"Women\":[\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=20&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=201&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=202&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=203&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=354&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=583&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=629&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=630&start_limit=0&end_limit=20\"],\"Kid\":[\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=223&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=224&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=371&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=372&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=373&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=623&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=628&start_limit=0&end_limit=20\"],\"Health Care\":[\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=387&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=388&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=598&start_limit=0&end_limit=20\"],\"Mobiles & Tablets\":[\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=501&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=509&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=516&start_limit=0&end_limit=20\"],\"Electronics\":[\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=428&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=480&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=588&start_limit=0&end_limit=20\"],\"Home Decor & Furnishing\":[\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=61&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=276&start_limit=0&end_limit=20\"],\"Home Utility\":[\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=465&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=466&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=467&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=468&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=585&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=625&start_limit=0&end_limit=20\"],\"Sairandhri\":[\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=601&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=602&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=603&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=604&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=605&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=606&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=607&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=608&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=609&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=610&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=621&start_limit=0&end_limit=20\"],\"Spiritual Items\":[\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=617&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=618&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=619&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=620&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=622&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=626&start_limit=0&end_limit=20\"],\"Food Zone\":[\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=613&start_limit=0&end_limit=20\"],\"Special Sale\":[\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=635&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=636&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=637&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=638&start_limit=0&end_limit=20\",\"http://ealpha.com/mob/customers.php?get_data=category_data&category_id=639&start_limit=0&end_limit=20\"]}}";
    }
}