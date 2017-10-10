//package com.main;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.d.t.o.CountriesGetterSetter;
//import com.d.t.o.HomeGetterSetter;
//import com.example.dharamraz.R;
//
//import java.util.ArrayList;
//
//public class CountryAdapter extends BaseAdapter {
//    private Context context;
//    private ArrayList<CountriesGetterSetter> arrayListCountries;
//    private CountriesGetterSetter countriesGetterSetter;
//
//    public CountryAdapter(Context context,
//                          ArrayList<CountriesGetterSetter> arrayListCountries) {
//        this.context = context;
//        this.arrayListCountries = arrayListCountries;
//    }
//    public CountryAdapter(Context applicationContext, int country_items, ArrayList<HomeGetterSetter> arrayList_imageslider) {
//    }
//
//    @Override
//    public int getCount() {
//        // TODO Auto-generated method stub
//        return arrayListCountries.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        // TODO Auto-generated method stub
//        return arrayListCountries.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        // TODO Auto-generated method stub
//        return position;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        final ViewHolder viewHolder;
//        if (convertView == null) {
//            convertView = (( LayoutInflater ) context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
//                    .inflate(R.layout.country_items, null);
//            viewHolder = new ViewHolder();
//            viewHolder.name = ( TextView ) convertView.findViewById(R.id.tv_country_name1);
//
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = ( ViewHolder ) convertView.getTag();
//        }
//        countriesGetterSetter = arrayListCountries.get(position);
//        viewHolder.name.setText(countriesGetterSetter.getName());
//
//        return convertView;
//    }
//
//    private static class ViewHolder {
//        private TextView name;
//        private ImageView image;
//
//    }
//}
