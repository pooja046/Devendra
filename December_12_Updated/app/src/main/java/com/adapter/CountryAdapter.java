package com.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.DTO.CountryDTO;
import com.example.dharamraz.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sys1 on 1/29/14.
 */
public class CountryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CountryDTO> countryDTOs;
    private CountryDTO countryDTO;
    private Typeface typeface_regular, typeface_light, typeface_bold;
    private List<CountryDTO> filteredData;
    private ItemFilter mFilter = new ItemFilter();

    public CountryAdapter(Context context, ArrayList<CountryDTO> countryDTOs) {
        this.context = context;
        this.countryDTOs = countryDTOs;
        this.filteredData = countryDTOs;

    }

    @Override
    public int getCount() {
        return filteredData.size();
    }

    @Override
    public CountryDTO getItem(int i) {
        return filteredData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView,
                        ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = ((LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.country_name = (TextView) convertView
                    .findViewById(R.id.name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        countryDTO = filteredData.get(position);
        viewHolder.country_name.setText(countryDTO.getName());
        return convertView;
    }

    private static class ViewHolder {
        private TextView country_name;
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            final List<CountryDTO> list = countryDTOs;
            int count = list.size();
            final ArrayList<CountryDTO> nlist = new ArrayList<CountryDTO>(count);
            String compayerString = "";
            for (int i = 0; i < count; i++) {
                CountryDTO dto = list.get(i);
                compayerString = dto.getName().toString();
                if (compayerString.toLowerCase().contains(filterString)) {
                    nlist.add(dto);
                }
            }
            results.values = nlist;
            results.count = nlist.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            filteredData = (ArrayList<CountryDTO>) results.values;
            notifyDataSetChanged();
        }
    }
}
