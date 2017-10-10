package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.DTO.GamesDTO;
import com.example.dharamraz.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 26/9/17.
 */

public class ViewPagerAdapter extends BaseAdapter {
    private Context context;
    public List selectedPositions;
    private GamesDTO gamesDTO;
    public ArrayList<GamesDTO> gamesDTOs;

    public ViewPagerAdapter(Context context, ArrayList<GamesDTO> gamesDTOs) {
        this.context = context;
        this.gamesDTOs = gamesDTOs;
        selectedPositions = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return this.gamesDTOs.size();
    }

    @Override
    public Object getItem(int position) {
        return this.gamesDTOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = ((LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.view_pager_row, null);
            viewHolder = new ViewHolder();
            viewHolder.game_image = (ImageView) convertView.findViewById(R.id.game_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            gamesDTO = gamesDTOs.get(position);
            Picasso.with(context).load(gamesDTO.getImage()).placeholder(R.drawable.white_box).error(R.drawable.white_box).into(viewHolder.game_image);
//            Glide.with(activity).load(recommended_image_dto.getStrRecommendedImage())
//                    .thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(viewHolder.is_active);
//
//            if (recommended_image_dto.is_selected()) {
//                viewHolder.is_active.setBackgroundResource(R.drawable.rounded_gray);
//            } else {
//                viewHolder.is_active.setBackgroundResource(R.drawable.rounded_white);
//            }
        } catch (Exception e) {

        }
        return convertView;
    }

    private static class ViewHolder {
        private ImageView game_image;
    }
}
