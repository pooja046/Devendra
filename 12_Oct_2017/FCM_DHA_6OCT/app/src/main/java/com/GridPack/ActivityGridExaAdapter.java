//package com.GridPack;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.example.dharamraz.R;
//import com.d.t.o.HomeGetterSetter;
//import com.squareup.picasso.Picasso;
//
//import java.util.List;
//
//public class ActivityGridExaAdapter extends ArrayAdapter<HomeGetterSetter> {
//
//    List<HomeGetterSetter> modelList;
//    Context context;
//    private LayoutInflater mInflater;
//
//    // Constructors
//    public ActivityGridExaAdapter(Context context, List<HomeGetterSetter> objects) {
//        super(context, 0, objects);
//        this.context = context;
//        this.mInflater = LayoutInflater.from(context);
//        modelList = objects;
//    }
//
//    @Override
//    public HomeGetterSetter getItem(int position) {
//        return modelList.get(position);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        final ViewHolder vh;
//        if (convertView == null) {
//            View view = mInflater.inflate(R.layout.country_items, parent, false);
//            vh = ViewHolder.create((RelativeLayout) view);
//            view.setTag(vh);
//        } else {
//            vh = (ViewHolder) convertView.getTag();
//        }
//
//        HomeGetterSetter item = getItem(position);
//
//      //  vh.textViewName.setText(item.getName());
//        //vh.textViewEmail.setText(item.getEmail());
//        Picasso.with(context).load(item.getImage()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.imageView);
//
//        return vh.rootView;
//    }
//
//    /**
//     * ViewHolder class for layout.<br />
//     * <br />
//     * Auto-created on 2016-01-05 00:50:26 by Android Layout Finder
//     * (http://www.buzzingandroid.com/tools/android-layout-finder)
//     */
//    private static class ViewHolder {
//        public final RelativeLayout rootView;
//        public final ImageView imageView;
//       // public final TextView textViewName;
//       // public final TextView textViewEmail;
//
//        private ViewHolder(RelativeLayout rootView, ImageView imageView) {
//            this.rootView = rootView;
//            this.imageView = imageView;
//          //  this.textViewName = textViewName;
//           // this.textViewEmail = textViewEmail;
//        }
//
//        public static ViewHolder create(RelativeLayout rootView) {
//            ImageView imageView = (ImageView) rootView.findViewById(R.id.img);
//            //TextView textViewName = (TextView) rootView.findViewById(R.id.tv_country_name1);
//            //TextView textViewEmail = (TextView) rootView.findViewById(R.id.textViewEmail);
//            return new ViewHolder(rootView, imageView);
//        }
//    }
//}