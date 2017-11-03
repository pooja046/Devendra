package com.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.DTO.SliderDTO;
import com.GridPack.UrlActivity;
import com.example.dharamraz.R;
import com.main.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by eclipse on 9/10/17.
 */

public class CustomPagerAdapter extends PagerAdapter {
    private ArrayList<SliderDTO> sliderDTOs;
    Context mContext;
    LayoutInflater mLayoutInflater;

    public CustomPagerAdapter(Context context, ArrayList<SliderDTO> sliderDTOs) {
        this.mContext = context;
        this.sliderDTOs = sliderDTOs;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return sliderDTOs.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
        ImageView image_new = (ImageView) itemView.findViewById(R.id.image_new);
        Picasso.with(mContext).load(sliderDTOs.get(position).getImage()).placeholder(R.drawable.white_box).error(R.drawable.white_box).into(image_new);
        image_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    MainActivity.vUrl = sliderDTOs.get(position).getUrl();
                } catch (Exception e) {

                }
                mContext.startActivity(new Intent(mContext, UrlActivity.class));
            }
        });
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}