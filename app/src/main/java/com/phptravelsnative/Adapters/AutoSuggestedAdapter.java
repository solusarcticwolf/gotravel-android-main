package com.phptravelsnative.Adapters;

import android.content.ClipData;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.phptravelsnative.Models.Auto_Model;
import com.phptravelsnative.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class AutoSuggestedAdapter extends ArrayAdapter<Auto_Model> {
    private LayoutInflater layoutInflater;
    List<Auto_Model> mCustomers;

    Gson gson = new Gson();

    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((Auto_Model)resultValue).getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null) {
                ArrayList<Auto_Model> suggestions = new ArrayList<Auto_Model>();
                ArrayList<Auto_Model> suggestions2 = new ArrayList<Auto_Model>();




                for (Auto_Model customer : mCustomers) {


                   // Log.d("constraint1test2 : ","   "+ customer.getCityCode());
                    if (customer.getCityCode().toLowerCase().contains(constraint.toString().toLowerCase()) ||customer.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(customer);



                        //Log.d("constraint1test : ",constraint.toString());

                    }
                }




                for (int i=0; i<suggestions.size();i++)
                {
                    Auto_Model model = suggestions.get(i);
                    if (model.getCityCode().toLowerCase().contains(constraint.toString().toLowerCase()))
                    {
                        suggestions2.add(model);
                    }


                }


                if (suggestions2.size()!=0)
                {
                    results.values = suggestions2;
                    results.count = suggestions2.size();
                }else {
                    results.values = suggestions;
                    results.count = suggestions.size();
                }


                //results.values = suggestions2;
               // results.count = suggestions2.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results != null && results.count > 0) {
                // we have filtered results
                addAll((ArrayList<Auto_Model>) results.values);
            } else {
                // no filter, add entire original list back in
                addAll(mCustomers);
            }
            notifyDataSetChanged();
        }
    };

    public AutoSuggestedAdapter(Context context, int textViewResourceId, List<Auto_Model> customers) {
        super(context, textViewResourceId, customers);
        // copy all the customers into a master list
        mCustomers = new ArrayList<Auto_Model>(customers.size());
        mCustomers.addAll(customers);
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.searching, null);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }
        Auto_Model customer = getItem(position);

        mViewHolder.name.setText(customer.getName());
        mViewHolder.ivIcon.setImageResource(customer.getImage_id());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class MyViewHolder {
        TextView name;
        ImageView ivIcon;

        public MyViewHolder(View item) {
            name = (TextView) item.findViewById(R.id.city_name);
            ivIcon=(ImageView)item.findViewById(R.id.iv_icon);
        }
    }
}
