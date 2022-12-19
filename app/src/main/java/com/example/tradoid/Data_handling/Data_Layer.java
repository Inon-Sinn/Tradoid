package com.example.tradoid.Data_handling;


import android.widget.Filter;
import android.widget.Filterable;

import com.example.tradoid.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

// This class implements all calls to the Database
public class Data_Layer {

    public List<example_Item> example_list = new ArrayList<>();
    List<example_Item> shuffle;

    public Data_Layer() {
        String s1[] = {"Samuel Sinn","Revital","Liel","Tehila","Inon","Ofir","Gilad","Noa","Yair","Mapel"};
        String s2[] = {"1","2","3","4","5","6","7","8","9","10"};
        int images[] = {R.drawable.ic_arrow_back, R.drawable.ic_ban, R.drawable.ic_bookmark,
                R.drawable.ic_dashboard, R.drawable.ic_identity, R.drawable.ic_kebab_menu,
                R.drawable.ic_menu, R.drawable.ic_portfolio, R.drawable.ic_list, R.drawable.ic_logout};
        for (int i = 0; i < s1.length; i++) {
            example_list.add(new example_Item(s1[i],s2[i],images[i]));
        }
        shuffle = new ArrayList<>(example_list);
    }

    public List<example_Item> getData(){
        return example_list;
    }

    public void setData(List<example_Item> newList){
        example_list.clear();
        example_list.addAll(newList);
    };

    public List<example_Item> shuffle(int seed){
        Collections.shuffle(shuffle, new Random(seed));
        return shuffle;
    }


}


// needs to be implemented to be filterable
//    @Override
//    public Filter getFilter() {
//        return examplFilter;
//    }
//
//    // building our own filter, works in the background so the app wont freeze
//    private Filter examplFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            List<example_Item> filteredList = new ArrayList<>();
//
//            // if there was the search length was 0 or null(no search) just return everything
//            if (constraint == null || constraint.length() == 0){
//                filteredList.addAll(example_list_full);
//            }
//            else{
//                String filterPattern = constraint.toString().toLowerCase().trim();
//                for (example_Item item: example_list_full) {
//                    if (item.getData1().toLowerCase().contains(filterPattern)){
//                        filteredList.add(item);
//                    }
//                }
//            }
//            FilterResults results = new FilterResults();
//            results.values = filteredList;
//
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            example_list.clear();
//            example_list.addAll((List) results.values);
//            notifyDataSetChanged();
//        }
//    };