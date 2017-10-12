package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.DTO.CategoryDTO;
import com.DTO.GamesDTO;
import com.example.dharamraz.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class GamesAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<GamesDTO> gamesDTOs;
    private GamesDTO gamesDTO;

    public GamesAdapter(Context context,
                        ArrayList<GamesDTO> gamesDTOs) {
        this.context = context;
        this.gamesDTOs = gamesDTOs;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return gamesDTOs.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return gamesDTOs.get(position);
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
                    .inflate(R.layout.games_rows, null);
            viewHolder = new ViewHolder();
            viewHolder.category_img = (ImageView) convertView.findViewById(R.id.category_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        gamesDTO = gamesDTOs.get(position);
        Picasso.with(context).load(gamesDTO.getImage()).placeholder(R.drawable.white_box).error(R.drawable.white_box).into(viewHolder.category_img);
        return convertView;
    }

    private static class ViewHolder {
        private ImageView category_img;
    }
}
