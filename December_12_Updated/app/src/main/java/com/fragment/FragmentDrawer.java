package com.fragment;

/**
 * Created by Ravi on 29/07/15.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.DTO.CategoryDTO;
import com.DTO.NavDrawerItem;
import com.adapter.NavigationDrawerAdapter;
import com.example.dharamraz.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

// FragmentDrawer use for navigation drawer...
public class FragmentDrawer extends Fragment {
    private RecyclerView recyclerView;
    private TextView txtusername;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    public static NavigationDrawerAdapter adapter;
    private View containerView;
    //    public static String[] titles = null;
    private FragmentDrawerListener drawerListener;
    public static FragmentDrawer fragmentDrawer;
    public static final Integer[] images = {R.drawable.home_new,
            R.drawable.home_new, R.drawable.home_new, R.drawable.home_new,
            R.drawable.home_new, R.drawable.home_new, R.drawable.home_new,
            R.drawable.home_new, R.drawable.home_new, R.drawable.home_new};
    public static List<NavDrawerItem> nevigationNavDrawerItems;
    private NavDrawerItem navDrawerItem;

    //R.mipmap.driver
    public FragmentDrawer() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

//    public static List<NavDrawerItem> getData() {
//        List<NavDrawerItem> data = new ArrayList<>();
//        // preparing navigation drawer items
//        for (int i = 0; i < titles.length; i++) {
//            NavDrawerItem navItem = new NavDrawerItem();
//            navItem.setTitle(titles[i]);
//            navItem.setImageID(images[i]);
//            data.add(navItem);
//        }
//        return data;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (getActivity() != null) {
                fragmentDrawer = this;
            }
        } catch (Exception e) {

        }
//        titles = new String[10];
//        titles = new String[]{"Home", "Bingo", "Casino", "Free Spins", "Lottery", "Poker", "Roulette", "Slots", "About Us", "Share App"};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        try {
            recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
            txtusername = (TextView) layout.findViewById(R.id.txtusername);
            txtusername.setText(toTitleCase("DharamRaz - Jackpot Junction"));
        } catch (Exception e) {

        }
        setAdapterInitializewithData();
        recyclerView.setLayoutManager(new
                LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new
                RecyclerTouchListener(getActivity(), recyclerView, new
                ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        drawerListener.onDrawerItemSelected(view, position);
                        mDrawerLayout.closeDrawer(containerView);
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));
        return layout;
    }

    public void setAdapterInitializewithData() {
        nevigationNavDrawerItems = new ArrayList<>();
        adapter = new
                NavigationDrawerAdapter(getActivity(), nevigationNavDrawerItems);
        recyclerView.setAdapter(adapter);
    }

    public void notifyAdapter(ArrayList<CategoryDTO> categoryDTOs) {
        if (categoryDTOs != null) {
            nevigationNavDrawerItems.clear();
// for Home
            navDrawerItem = new NavDrawerItem();
            navDrawerItem.setTitle("Home");
            navDrawerItem.setImageID(R.drawable.home_new);
            navDrawerItem.setPosition("Home");
            nevigationNavDrawerItems.add(navDrawerItem);
            for (int i = 0; i < categoryDTOs.size(); i++) {
                navDrawerItem = new NavDrawerItem();
                navDrawerItem.setTitle(toTitleCase(categoryDTOs.get(i).getCat_name().replace("_", " ")));
                navDrawerItem.setImageID(R.drawable.home_new);
                navDrawerItem.setPosition(i + "");
                nevigationNavDrawerItems.add(navDrawerItem);
            }
            // for About Us
            navDrawerItem = new NavDrawerItem();
            navDrawerItem.setTitle("About Us");
            navDrawerItem.setImageID(R.drawable.home_new);
            navDrawerItem.setPosition("About Us");
            nevigationNavDrawerItems.add(navDrawerItem);

            // for Share App
            navDrawerItem = new NavDrawerItem();
            navDrawerItem.setTitle("Share App");
            navDrawerItem.setImageID(R.drawable.home_new);
            navDrawerItem.setPosition("Share App");
            nevigationNavDrawerItems.add(navDrawerItem);
            adapter.notifyDataSetChanged();
        }
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //  KeyboardUtil.hideSoftKeyboard(containerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
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
}
