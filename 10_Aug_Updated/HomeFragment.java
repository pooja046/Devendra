package com.ealpha.main;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.ealpha.R;
import com.ealpha.homeclick.AdapterHomeSlider;
import com.ealpha.homeclick.BannerClick;
import com.ealpha.homeclick.NotificationActivity;
import com.ealpha.homeclick.ProductDetailActivityBanner;
import com.ealpha.homeclick.ProductDetailActivityPP;
import com.ealpha.homeclick.SliderClick;
import com.ealpha.support.ProductAdapterGridChild;
import com.ealpha.utility.Helper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ps.DTO.BannerDTO;
import com.ps.DTO.CartsDTO;
import com.ps.DTO.ProductDTO;
import com.ps.DTO.SliderDTO;
import com.ps.utility.JSONParser;
import com.ps.utility.SessionManager;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    private ViewPager viewpager;
    private ListView list_product_type;
    private ArrayList<SliderDTO> sliderDTOs;
    private SliderDTO sliderDTO;

    public static ArrayList<ProductDTO> productArrayList;
    private ProductDTO productDTO;

    private ArrayList<BannerDTO> bannerDTOs;
    private BannerDTO bannerDTO;

    private ProgressDialog progressDialog;
    private ProductAdapterGridChild productAdapterGridChild;

    private ImageView img_btn_1, img_btn_2, img_btn_3, img_btn_4, img_btn_5;
    private DisplayImageOptions options;

    public static int p_potition = 0;
    ArrayList<BannerDTO> link_list = new ArrayList<BannerDTO>();

    public static HomeFragment homeFragment;
    private SessionManager sessionManager;
    private String vProduct_ID = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container,
                false);
        homeFragment = this;
        MainActivity.view_pagination_index = 0;
        ChildItemsFragment.childItemsFragment = null;
        initialize(rootView);
        return rootView;
    }

    public void setListViewData() {
        productArrayList = new ArrayList<>();
        productAdapterGridChild = new ProductAdapterGridChild(getActivity(),
                productArrayList);
        list_product_type.setAdapter(productAdapterGridChild);
    }

    private void initialize(View view) {
        // TODO Auto-generated method stub

        ScrollView scroll = (ScrollView) view.findViewById(R.id.start_scroll);
        scroll.setFocusableInTouchMode(true);
        scroll.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        sessionManager = new SessionManager(getActivity());
        try {
            list_product_type = (ListView) view
                    .findViewById(R.id.list_product_type);
            setListViewData();
            img_btn_1 = (ImageView) view.findViewById(R.id.img_btn_1);
            img_btn_1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (bannerDTOs
                            .get(0).getBanner_link().contains("get_data=product_data")) {
                        showProductDetailFromBanner(0);
                    } else {
                        Intent intent_banner_btn_1 = new Intent(getActivity(),
                                BannerClick.class);
                        intent_banner_btn_1.putExtra("banner_url", bannerDTOs
                                .get(0).getBanner_link());
                        startActivity(intent_banner_btn_1);
                    }
                }
            });

            img_btn_2 = (ImageView) view.findViewById(R.id.img_btn_2);
            img_btn_2.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (bannerDTOs
                            .get(1).getBanner_link().contains("get_data=product_data")) {
                        showProductDetailFromBanner(1);
                    } else {
                        Intent intent_banner_btn_2 = new Intent(getActivity(),
                                BannerClick.class);
                        intent_banner_btn_2.putExtra("banner_url", bannerDTOs
                                .get(1).getBanner_link());
                        startActivity(intent_banner_btn_2);
                    }

                }
            });

            img_btn_3 = (ImageView) view.findViewById(R.id.img_btn_3);
            img_btn_3.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (bannerDTOs
                            .get(2).getBanner_link().contains("get_data=product_data")) {
                        showProductDetailFromBanner(2);
                    } else {
                        Intent intent_banner_btn_3 = new Intent(getActivity(),
                                BannerClick.class);
                        intent_banner_btn_3.putExtra("banner_url", bannerDTOs
                                .get(2).getBanner_link());
                        getActivity().startActivity(intent_banner_btn_3);
                    }
                }
            });
            img_btn_4 = (ImageView) view.findViewById(R.id.img_btn_4);
            img_btn_4.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (bannerDTOs
                            .get(3).getBanner_link().contains("get_data=product_data")) {
                        showProductDetailFromBanner(3);
                    } else {
                        Intent intent_banner_btn_4 = new Intent(getActivity(),
                                BannerClick.class);
                        intent_banner_btn_4.putExtra("banner_url", bannerDTOs
                                .get(3).getBanner_link());
                        getActivity().startActivity(intent_banner_btn_4);
                    }
                }
            });
            img_btn_5 = (ImageView) view.findViewById(R.id.img_btn_5);

            img_btn_5.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (bannerDTOs
                            .get(4).getBanner_link().contains("get_data=product_data")) {
                        showProductDetailFromBanner(4);
                    } else {
                        Intent intent_banner_btn_5 = new Intent(getActivity(),
                                BannerClick.class);
                        intent_banner_btn_5.putExtra("banner_url", bannerDTOs
                                .get(4).getBanner_link());
                        getActivity().startActivity(intent_banner_btn_5);
                    }
                }
            });

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setIndeterminate(true);
            progressDialog.setTitle("Please wait");
            // progressDialog.setCancelable(false);
            progressDialog.setCancelable(true);
            progressDialog.setMessage("Please wait");
            progressDialog.show();

            new homeAsynchTask()
                    .execute("http://ealpha.com/mob/customers.php?customer=home_page_product");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void showProductDetailFromBanner(int position) {
        MainActivity.cartsDTO = new CartsDTO();
        MainActivity.cartsDTO.setId_product("");
        MainActivity.cartsDTO.setProduct_name("");
        MainActivity.cartsDTO.setUnit_price("0.0");
        MainActivity.cartsDTO.setTotal_price("0.0");
        MainActivity.cartsDTO.setProduct_img(bannerDTOs
                .get(position).getBanner_image());
        MainActivity.cartsDTO.setProduct_link(bannerDTOs
                .get(position).getBanner_link());
        MainActivity.cartsDTO.setQuantity(1);
        MainActivity.cartsDTO.setAvailability("0");
        Intent intent = new Intent(getActivity(), ProductDetailActivityBanner.class);
        intent.putExtra("link", bannerDTOs
                .get(position).getBanner_link());
        startActivity(intent);
    }
    // public void searchOnHomeFragment(String vSearcText) {
    // if (vSearcText.trim().length() > 0) {
    // progressDialog = new ProgressDialog(getActivity());
    // progressDialog.setIndeterminate(true);
    // progressDialog.setTitle("Please wait");
    // progressDialog.setCancelable(true);
    // progressDialog.setMessage("Please wait");
    // progressDialog.show();
    // new homeAsynchTask()
    // .execute("http://www.ealpha.com/mob/customers.php?customers=search_api&search="
    // + vSearcText + "&start_limit=1&end_limit=100");
    // } else {
    //
    // }
    // }

    class homeAsynchTask extends AsyncTask<String, Void, JSONObject> {
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
            progressDialog.dismiss();
            // ----------for slider--------------
            try {
                System.out
                        .println("slider response..." + jsonObject.toString());
                JSONArray sliderImageArray = jsonObject
                        .getJSONObject("home_page").getJSONObject("message")
                        .getJSONArray("slider");
                JSONArray sliderLinkArray = jsonObject
                        .getJSONObject("home_page").getJSONObject("message")
                        .getJSONArray("slider_link");
                sliderDTOs = new ArrayList<SliderDTO>();
                for (int i = 0; i < sliderImageArray.length(); i++) {
                    sliderDTO = new SliderDTO();
                    sliderDTO.setSlider_image(sliderImageArray.getString(i));
                    sliderDTO.setImage_link(sliderLinkArray.getString(i));
                    sliderDTOs.add(sliderDTO);
                }
                setSlider();
            } catch (Exception e) {
                // TODO: handle exception
            }
            // ----------for popular grid view--------------
            try {
                JSONArray productNameArray = jsonObject
                        .getJSONObject("home_page").getJSONObject("message")
                        .getJSONArray("product_name_popular");

                JSONArray productImageArray = jsonObject
                        .getJSONObject("home_page").getJSONObject("message")
                        .getJSONArray("product_img_popular");

                JSONArray productPriceArray = jsonObject
                        .getJSONObject("home_page").getJSONObject("message")
                        .getJSONArray("product_price_popular");

                JSONArray productLinkArray = jsonObject
                        .getJSONObject("home_page").getJSONObject("message")
                        .getJSONArray("product_link_popular");
                for (int i = 0; i < productNameArray.length(); i++) {
                    productDTO = new ProductDTO();
                    productDTO
                            .setProduct_id(productLinkArray
                                    .getString(i)
                                    .replace(
                                            "http://ealpha.com//mob/customers.php?get_data=product_data&product_id=",
                                            ""));
                    productDTO.setProduct_name(productNameArray.getString(i));
                    productDTO.setProduct_img(productImageArray.getString(i));
                    productDTO.setProduct_price(productPriceArray.getString(i));
                    productDTO.setProduct_link(productLinkArray.getString(i));
                    if (sessionManager.isLogin()) {
                        if (MainActivity.wish_p_ids.contains(productDTO
                                .getProduct_id())) {
                            productDTO.setWish_to_list(true);
                        } else {
                            productDTO.setWish_to_list(false);
                        }
                    } else {
                        productDTO.setWish_to_list(false);
                    }
                    productArrayList.add(productDTO);
                }
                setNewPorductListData();
            } catch (Exception e) {
                // TODO: handle exception
            }

            // try {
            // JSONArray productNameArray = jsonObject
            // .getJSONObject("search_result").getJSONArray("message")
            // .getJSONObject(0).getJSONArray("name");
            //
            // JSONArray productImageArray = jsonObject
            // .getJSONObject("search_result").getJSONArray("message")
            // .getJSONObject(0).getJSONArray("product_img");
            //
            // JSONArray productPriceArray = jsonObject
            // .getJSONObject("search_result").getJSONArray("message")
            // .getJSONObject(0).getJSONArray("product_price");
            //
            // JSONArray productLinkArray = jsonObject
            // .getJSONObject("search_result").getJSONArray("message")
            // .getJSONObject(0).getJSONArray("product_link");
            //
            // // setProductArrayList(new ArrayList<ProductDTO>());
            // for (int i = 0; i < productNameArray.length(); i++) {
            // productDTO = new ProductDTO();
            // productDTO
            // .setProduct_id(productLinkArray
            // .getString(i)
            // .replace(
            // "http://ealpha.com/mob/customers.php?get_data=product_data&product_id=",
            // ""));
            // productDTO.setProduct_name(productNameArray.getString(i));
            // productDTO.setProduct_img(productImageArray.getString(i));
            // productDTO.setProduct_price(productPriceArray.getString(i));
            // productDTO.setProduct_link(productLinkArray.getString(i));
            // if (sessionManager.isLogin()) {
            // if (MainActivity.wish_p_ids.contains(productDTO
            // .getProduct_id())) {
            // productDTO.setWish_to_list(true);
            // } else {
            // productDTO.setWish_to_list(false);
            // }
            // } else {
            // productDTO.setWish_to_list(false);
            // }
            // // getProductArrayList().add(productDTO);
            // }
            // setGridData();
            // } catch (Exception e) {
            // // TODO: handle exception
            // }

            // ----------for Banner Bottom grid view--------------
            try {
                JSONArray bannerImageArray = jsonObject
                        .getJSONObject("home_page").getJSONObject("message")
                        .getJSONArray("banner_image");
                JSONArray bannerLinkArray = jsonObject
                        .getJSONObject("home_page").getJSONObject("message")
                        .getJSONArray("banner_url");
                bannerDTOs = new ArrayList<BannerDTO>();
                for (int i = 0; i < bannerImageArray.length(); i++) {
                    bannerDTO = new BannerDTO();
                    bannerDTO.setBanner_image(bannerImageArray.getString(i));
                    bannerDTO.setBanner_link(bannerLinkArray.getString(i));
                    bannerDTOs.add(bannerDTO);
                }
                setBannerData();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    public void setSlider() {
        viewpager.setAdapter(new AdapterHomeSlider(getActivity(), sliderDTOs));
        viewpager.postDelayed(new Runnable() {
            int i = 0;

            @Override
            public void run() {
                if (i == sliderDTOs.size()) {
                    i = 0;
                }
                viewpager.setCurrentItem(i);
                i++;
                viewpager.postDelayed(this, 2000);
            }
        }, 10000);
    }

    public void openGridfromSliderItemClick(int position) {
        startActivity(new Intent(getActivity(), SliderClick.class).putExtra(
                "slider_link", sliderDTOs.get(position).getImage_link()));
    }

    public void setNewPorductListData() {
        productAdapterGridChild.notifyDataSetChanged();
        Helper.getListViewSize(list_product_type);
        list_product_type
                .setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        // TODO Auto-generated method stub
                        p_potition = position;
                        MainActivity.cartsDTO = new CartsDTO();
                        System.out.println("id_product...."
                                + productArrayList.get(position)
                                .getProduct_id());
                        System.out.println("product_name...."
                                + productArrayList.get(position)
                                .getProduct_name());
                        System.out.println("unit_price...."
                                + productArrayList.get(position)
                                .getProduct_price());
                        System.out.println("product_img...."
                                + productArrayList.get(position)
                                .getProduct_img());
                        System.out.println("product_link...."
                                + productArrayList.get(position)
                                .getProduct_link());
                        System.out.println("availability...."
                                + productArrayList.get(position)
                                .getAvailability());
                        MainActivity.cartsDTO.setId_product(productArrayList
                                .get(position).getProduct_id());
                        MainActivity.cartsDTO.setProduct_name(productArrayList
                                .get(position).getProduct_name());
                        MainActivity.cartsDTO.setUnit_price(productArrayList
                                .get(position).getProduct_price());
                        MainActivity.cartsDTO.setTotal_price(productArrayList
                                .get(position).getProduct_price());
                        MainActivity.cartsDTO.setProduct_img(productArrayList
                                .get(position).getProduct_img());
                        MainActivity.cartsDTO.setProduct_link(productArrayList
                                .get(position).getProduct_link());
                        MainActivity.cartsDTO.setQuantity(1);
                        MainActivity.cartsDTO.setAvailability(productArrayList
                                .get(position).getAvailability());
                        Intent intent_popular_grid = new Intent(getActivity(),
                                ProductDetailActivityPP.class);
                        intent_popular_grid.putExtra("product_link_popular",
                                productArrayList.get(position)
                                        .getProduct_link());
                        getActivity().startActivity(intent_popular_grid);
                    }
                });
    }

    public void setBannerData() {
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        try {

            ImageLoader.getInstance().displayImage(
                    bannerDTOs.get(0).getBanner_image(), img_btn_1, options);
        } catch (Exception e) {

        }
        try {
            ImageLoader.getInstance().displayImage(
                    bannerDTOs.get(1).getBanner_image(), img_btn_2, options);
        } catch (Exception e) {

        }
        try {
            ImageLoader.getInstance().displayImage(
                    bannerDTOs.get(2).getBanner_image(), img_btn_3, options);
        } catch (Exception e) {

        }
        try {
            ImageLoader.getInstance().displayImage(
                    bannerDTOs.get(3).getBanner_image(), img_btn_4, options);
        } catch (Exception e) {

        }
        try {
            ImageLoader.getInstance().displayImage(
                    bannerDTOs.get(4).getBanner_image(), img_btn_5, options);
        } catch (Exception e) {

        }
    }

    public void wistToList(int position) {
        if (!sessionManager.isLogin()) {
            Toast.makeText(getActivity(), "User not logged in.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        vProduct_ID = productArrayList.get(position).getProduct_id();
        if (productArrayList.get(position).isWish_to_list()) {
            productArrayList.get(position).setWish_to_list(false);
            MainActivity.wish_p_ids.remove(vProduct_ID);
            MainActivity.wish_p_ids = new ArrayList<String>(
                    new LinkedHashSet<String>(MainActivity.wish_p_ids));
            productAdapterGridChild.notifyDataSetChanged();
            new addAndRemoveWishListAsyncTask()
                    .execute("http://www.ealpha.com/mob/customers.php?customers=wishlist_remove");
        } else {
            productArrayList.get(position).setWish_to_list(true);
            MainActivity.wish_p_ids.add(vProduct_ID);
            MainActivity.wish_p_ids = new ArrayList<String>(
                    new LinkedHashSet<String>(MainActivity.wish_p_ids));
            productAdapterGridChild.notifyDataSetChanged();
            new addAndRemoveWishListAsyncTask()
                    .execute("http://www.ealpha.com/mob/customers.php?customers=wishlist_add");
        }
    }

    class addAndRemoveWishListAsyncTask extends
            AsyncTask<String, Void, JSONObject> {

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
            System.out.println("params..." + params.toString());
            JSONObject json = new JSONParser().makeHttpRequest2(args[0],
                    "POST", params);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            String vMessage = "";
            String vStatus = "";
            try {
                // {"wishlist_added_data":{"status":"Success","message":"The product was successfully added to your wishlist","wishlist_id":14}}
                // {"wishlist_remove":{"status":"Success","message":"Wishlist Removed "}}
                System.out.println("wish list..." + jsonObject.toString());
                JSONObject wishlist_added_data = null;
                if (jsonObject.has("wishlist_added_data")) {
                    wishlist_added_data = jsonObject
                            .getJSONObject("wishlist_added_data");
                    vStatus = wishlist_added_data.getString("status");
                    // vMessage = wishlist_added_data.getString("message");
                    vMessage = "item added into wishlist.";
                } else if (jsonObject.has("wishlist_remove")) {
                    wishlist_added_data = jsonObject
                            .getJSONObject("wishlist_remove");
                    vStatus = wishlist_added_data.getString("status");
                    vMessage = "item removed from wishlist.";
                }
                if (vStatus.trim().equals("Success")) {
                    Toast.makeText(getActivity(), "" + vMessage,
                            Toast.LENGTH_SHORT).show();
                } else if (vStatus.trim().equals("Error")) {
                    Toast.makeText(getActivity(), "" + vMessage,
                            Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {

            }
        }
    }

}