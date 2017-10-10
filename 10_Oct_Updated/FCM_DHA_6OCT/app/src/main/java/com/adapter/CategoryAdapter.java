package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.DTO.CategoryDTO;
import com.example.dharamraz.R;

import java.util.ArrayList;


public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CategoryDTO> categoryDTOs;
    private CategoryDTO categoryDTO;

    public CategoryAdapter(Context context,
                           ArrayList<CategoryDTO> categoryDTOs) {
        this.context = context;
        this.categoryDTOs = categoryDTOs;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
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
                    .inflate(R.layout.category_row, null);
            viewHolder = new ViewHolder();
            viewHolder.category_name = (TextView) convertView.findViewById(R.id.category_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        categoryDTO = categoryDTOs.get(position);
        viewHolder.category_name.setText(toTitleCase(categoryDTO.getCat_name().replace("_", " ")));
        return convertView;
    }

    private static class ViewHolder {
        private TextView category_name;
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
