package com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.DTO.CategoryDTO;
import com.DTO.GamesDTO;
import com.DTO.LinkDTO;
import com.DTO.SliderDTO;
import com.GridPack.GamesActivity;
import com.GridPack.UrlActivity;
import com.adapter.CategoryAdapter;
import com.adapter.CustomPagerAdapter;
import com.adapter.GamesHomeAdapter;
import com.adapter.ViewPagerAdapter;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.MyApplication;
import com.constants.Constants;
import com.example.dharamraz.R;
import com.main.MainActivity;
import com.util.Helper;
import com.util.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Ravi on 29/07/15.
 */
public class HomeFragment extends Fragment {
    private GridView category_grid_view;
    private ListView home_bottom_grid_view;
    private CategoryAdapter categoryAdapter;
    private GamesHomeAdapter gamesHomeAdapter;
    public static ArrayList<CategoryDTO> categoryDTOs;
    private CategoryDTO categoryDTO;
    private LinearLayout see_all;
    private ArrayList<GamesDTO> gamesDTOs;
    private GamesDTO gamesDTO;
    private ArrayList<SliderDTO> sliderDTOs;
    private SliderDTO sliderDTO;
    private LinkDTO linkDTO;
    private GridView grid_view_horizontal;
    private ViewPagerAdapter viewPagerAdapter;
    private LinearLayout liner_pager;
    private SessionManager sessionManager;
    private ViewPager view_pager_main;
    private CustomPagerAdapter customPagerAdapter;
    private int delay = 3000; //milliseconds
    private int page = 0;
    private Handler handler;
    Runnable runnable = new Runnable() {
        public void run() {
            try {
                if (customPagerAdapter.getCount() == page) {
                    page = 0;
                } else {
                    page++;
                }
                view_pager_main.setCurrentItem(page, true);
                handler.postDelayed(this, delay);
            } catch (Exception e) {

            }
        }
    };

    public HomeFragment() {
        // Required empty public constructor
    }

    ImageView facebook1, twiter1, gplus1;
    TextView text_disclaimer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initialize(rootView);
        // Inflate the layout for this fragment
        return rootView;
    }

    public void initialize(View rootView) {
        category_grid_view = (GridView) rootView.findViewById(R.id.category_grid_view);
        home_bottom_grid_view = (ListView) rootView.findViewById(R.id.home_bottom_grid_view);
        see_all = (LinearLayout) rootView.findViewById(R.id.see_all);
        grid_view_horizontal = (GridView) rootView.findViewById(R.id.grid_view_horizontal);
        liner_pager = (LinearLayout) rootView.findViewById(R.id.liner_pager);
        view_pager_main = (ViewPager) rootView.findViewById(R.id.view_pager_main);

        facebook1 = (ImageView) rootView.findViewById(R.id.facebook);
        twiter1 = (ImageView) rootView.findViewById(R.id.twitter);
        gplus1 = (ImageView) rootView.findViewById(R.id.google_plus);
        // text_disclaimer.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.facebook:
                        facebookClick();
                        break;
                    case R.id.twitter:
                        twiterClick();
                        break;
                    case R.id.google_plus:
                        googlePlusClick();
                        break;
                }
            }
        };
        facebook1.setOnClickListener(listener);
        twiter1.setOnClickListener(listener);
        gplus1.setOnClickListener(listener);

        sessionManager = new SessionManager(getActivity());
        categoryDTOs = new ArrayList<>();

        categoryDTO = new CategoryDTO();
        categoryDTO.setCat_name("Slots");
        categoryDTOs.add(categoryDTO);

        categoryDTO = new CategoryDTO();
        categoryDTO.setCat_name("Casino");
        categoryDTOs.add(categoryDTO);

        categoryDTO = new CategoryDTO();
        categoryDTO.setCat_name("Free Spins");
        categoryDTOs.add(categoryDTO);

        categoryDTO = new CategoryDTO();
        categoryDTO.setCat_name("Lottery");
        categoryDTOs.add(categoryDTO);

        categoryDTO = new CategoryDTO();
        categoryDTO.setCat_name("Bingo");
        categoryDTOs.add(categoryDTO);

        categoryDTO = new CategoryDTO();
        categoryDTO.setCat_name("Roulette");
        categoryDTOs.add(categoryDTO);

        categoryDTO = new CategoryDTO();
        categoryDTO.setCat_name("Poker");
        categoryDTOs.add(categoryDTO);

        categoryAdapter = new CategoryAdapter(getActivity(), categoryDTOs);
        category_grid_view.setAdapter(categoryAdapter);

        gamesHomeAdapter = new GamesHomeAdapter(getActivity(), categoryDTOs);
        home_bottom_grid_view.setAdapter(gamesHomeAdapter);
        Helper.getListViewSize(home_bottom_grid_view);

        category_grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                MainActivity.categoryDTO = categoryDTOs.get(position);
                startActivity(new Intent(getActivity(), GamesActivity.class));
            }
        });
        see_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.categoryDTO = null;
                startActivity(new Intent(getActivity(), GamesActivity.class));
            }
        });
        getSlidersOnHome();
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllCategoryOnHome();
    }

    public void getSlidersOnHome() {
        StringRequest req = new StringRequest(Request.Method.POST, Constants.getMethodUrl(Constants.BANNER),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("response..." + response.toString());
                            sliderDTOs = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response);
                            try {
                                JSONArray sliderJSONArray = jsonObject.getJSONArray("sliders");
                                for (int i = 0; i < sliderJSONArray.length(); i++) {
                                    JSONObject bannerObject = sliderJSONArray.getJSONObject(i);
                                    sliderDTO = new SliderDTO();
                                    sliderDTO.setImage(bannerObject.getString("image"));
                                    sliderDTO.setUrl(bannerObject.getString("url"));
                                    sliderDTOs.add(sliderDTO);
                                }
                                handler = new Handler();
                                customPagerAdapter = new CustomPagerAdapter(getActivity(), sliderDTOs);
                                view_pager_main.setAdapter(customPagerAdapter);
                                view_pager_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                    @Override
                                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                    }

                                    @Override
                                    public void onPageSelected(int position) {
                                        page = position;
                                    }

                                    @Override
                                    public void onPageScrollStateChanged(int state) {

                                    }
                                });
                                handler.postDelayed(runnable, delay);
                            } catch (Exception e) {

                            }

                        } catch (Exception e) {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(req);
    }


    public void getAllCategoryOnHome() {
        StringRequest req = new StringRequest(Request.Method.POST, Constants.getMethodUrl(Constants.CATEGORY),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("response..." + response.toString());
                            categoryDTOs = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response);
                            try {
                                JSONArray categoriesJSONArray = jsonObject.getJSONArray("categories");
                                for (int i = 0; i < categoriesJSONArray.length(); i++) {
                                    categoryDTO = new CategoryDTO();
                                    categoryDTO.setCat_name(categoriesJSONArray.getString(i));
                                    categoryDTO.setGamesDTOs(new ArrayList<GamesDTO>());
                                    categoryDTOs.add(categoryDTO);
                                }
                            } catch (Exception e) {

                            }
                            Collections.sort(categoryDTOs,
                                    CategoryDTO.COMPARE_BY_CATEGORY_NAME);
                            categoryAdapter = new CategoryAdapter(getActivity(), categoryDTOs);
                            category_grid_view.setAdapter(categoryAdapter);
                            gamesHomeAdapter = new GamesHomeAdapter(getActivity(), categoryDTOs);
                            home_bottom_grid_view.setAdapter(gamesHomeAdapter);
                            Helper.getListViewSize(home_bottom_grid_view);
                            setDataOnMenu(categoryDTOs);
                            getAllGamesByCategoryIdOrCountryIdsVolley();
                        } catch (Exception e) {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(req);
    }

    public void setDataOnMenu(ArrayList<CategoryDTO> categoryDTOs) {
        if (FragmentDrawer.fragmentDrawer != null) {
            FragmentDrawer.fragmentDrawer.notifyAdapter(categoryDTOs);
        }
    }

    public void getAllGamesByCategoryIdOrCountryIdsVolley() {
        StringRequest req = new StringRequest(Request.Method.POST, Constants.getMethodUrl(Constants.GAMES_BY_CAT_ID_OR_COUNTRY_ID),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("response..." + response.toString());
                            gamesDTOs = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response);
                            try {
                                JSONArray gamesJSONArray = jsonObject.getJSONArray("games");
                                for (int i = 0; i < gamesJSONArray.length(); i++) {
                                    JSONObject gamesJSONObject = gamesJSONArray.getJSONObject(i);
                                    gamesDTO = new GamesDTO();
                                    gamesDTO.setId(gamesJSONObject.getString("id"));
                                    gamesDTO.setName(gamesJSONObject.getString("name"));
                                    gamesDTO.setImage(gamesJSONObject.getString("image"));
                                    gamesDTO.setCategory(gamesJSONObject.getString("category"));
                                    gamesDTO.setDesc(gamesJSONObject.getString("desc"));
                                    gamesDTO.setFeatured(gamesJSONObject.getString("featured"));
                                    linkDTO = new LinkDTO();
                                    try {
                                        JSONObject linkJSONObject = gamesJSONObject.getJSONObject("link");
                                        linkDTO.setUrl(linkJSONObject.getString("url"));
                                    } catch (Exception e) {

                                    }
                                    try {
                                        linkDTO.setUrl(gamesJSONObject.getString("url"));
                                    } catch (Exception e) {

                                    }
                                    gamesDTO.setLinkDTO(linkDTO);
                                    gamesDTOs.add(gamesDTO);
                                }
                                Collections.sort(gamesDTOs,
                                        GamesDTO.GAMES_BY_CATEGORY_NAME);
                                DisplayMetrics dm = new DisplayMetrics();
                                getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
                                float density = dm.density;
                                int totalWidth = (int) (gamesDTOs.size() * 135 * density);
                                int totalHeight = (int) (135 * density);
                                grid_view_horizontal.setColumnWidth(totalHeight);
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(totalWidth, totalHeight);
                                liner_pager.setLayoutParams(params);
                                grid_view_horizontal.setNumColumns(gamesDTOs.size());
                                viewPagerAdapter = new ViewPagerAdapter(getActivity(), gamesDTOs);
                                grid_view_horizontal.setAdapter(viewPagerAdapter);
                                grid_view_horizontal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        MainActivity.vUrl = gamesDTOs.get(i).getLinkDTO().getUrl();
                                        startActivity(new Intent(getActivity(), UrlActivity.class));
                                    }
                                });
                                sortMyFunction();
                            } catch (Exception e) {

                            }
                        } catch (Exception e) {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("category", "");
                String vCountry_Id = sessionManager.getCountryDetails().get(SessionManager.COUNTRY_ID);
                params.put("country_id", vCountry_Id);
                return params;
            }
        };
        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(req);
    }

    public void sortMyFunction() {
        for (int i = 0; i < categoryDTOs.size(); i++) {
            for (int j = 0; j < gamesDTOs.size(); j++) {
                if (categoryDTOs.get(i).getCat_name().equals(gamesDTOs.get(j).getCategory())) {
                    categoryDTOs.get(i).getGamesDTOs().add(gamesDTOs.get(j));
                }
            }
        }
        gamesHomeAdapter = new GamesHomeAdapter(getActivity(), categoryDTOs);
        home_bottom_grid_view.setAdapter(gamesHomeAdapter);
        Helper.getListViewSize(home_bottom_grid_view);
    }


    @Override
    public void onPause() {
        super.onPause();
        // handler.removeCallbacks(runnable);
    }

    public static String toTitleCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            try {
                sb.append(Character.toUpperCase(arr[i].charAt(0)))
                        .append(arr[i].substring(1)).append(" ");
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return sb.toString().trim();
    }


    public void facebookClick() {


        Toast.makeText(getActivity(), "Yehh .....facebook",
                Toast.LENGTH_LONG).show();
        return;


    }

    public void twiterClick() {


        Toast.makeText(getActivity(), "Yehh .....twiter",
                Toast.LENGTH_LONG).show();
        return;
    }

    public void googlePlusClick() {


        Toast.makeText(getActivity(), "Yehh .....Google plus",
                Toast.LENGTH_LONG).show();
        return;


    }

}
