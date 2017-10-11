package com.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.DTO.CategoryDTO;
import com.DTO.GamesDTO;
import com.GridPack.GamesActivity;
import com.GridPack.UrlActivity;
import com.example.dharamraz.R;
import com.main.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class GamesHomeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CategoryDTO> categoryDTOs;
    private CategoryDTO categoryDTO;

    public GamesHomeAdapter(Context context,
                            ArrayList<CategoryDTO> categoryDTOs) {
        this.context = context;
        this.categoryDTOs = categoryDTOs;
    }

    @Override
    public int getCount() {
        // TODO categoryDTOs-generated method stub
        return categoryDTOs.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return categoryDTOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = ((LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.games_grid_rows_on_home, null);
            viewHolder = new ViewHolder();
            viewHolder.game_name = (TextView) convertView.findViewById(R.id.game_name);
            viewHolder.game_img_one = (ImageView) convertView.findViewById(R.id.game_img_one);
            viewHolder.game_img_two = (ImageView) convertView.findViewById(R.id.game_img_two);
            viewHolder.see_all = (LinearLayout) convertView.findViewById(R.id.see_all);
            viewHolder.game_one = (LinearLayout) convertView.findViewById(R.id.game_one);
            viewHolder.game_two = (LinearLayout) convertView.findViewById(R.id.game_two);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        categoryDTO = categoryDTOs.get(position);
        viewHolder.game_name.setText(toTitleCase(categoryDTO.getCat_name().replace("_", " ")));
        viewHolder.see_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.categoryDTO = categoryDTOs.get(position);
                context.startActivity(new Intent(context, GamesActivity.class));
            }
        });
        try {
            Picasso.with(context).load(categoryDTO.getGamesDTOs().get(0).getImage()).placeholder(R.drawable.white_box).error(R.drawable.white_box).into(viewHolder.game_img_one);
            viewHolder.game_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        MainActivity.categoryDTO = categoryDTOs.get(position);
                        MainActivity.vUrl = categoryDTOs.get(position).getGamesDTOs().get(0).getLinkDTO().getUrl();
                    } catch (Exception e) {

                    }
                    context.startActivity(new Intent(context, UrlActivity.class));
                }
            });
            Picasso.with(context).load(categoryDTO.getGamesDTOs().get(1).getImage()).placeholder(R.drawable.white_box).error(R.drawable.white_box).into(viewHolder.game_img_two);
            viewHolder.game_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        MainActivity.categoryDTO = categoryDTOs.get(position);
                        MainActivity.vUrl = categoryDTOs.get(position).getGamesDTOs().get(1).getLinkDTO().getUrl();
                    } catch (Exception e) {

                    }
                    context.startActivity(new Intent(context, UrlActivity.class));
                }
            });
        } catch (Exception e) {

        }
        return convertView;
    }

    private static class ViewHolder {
        private ImageView game_img_one, game_img_two;
        private TextView game_name;
        private LinearLayout see_all, game_one, game_two;
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
