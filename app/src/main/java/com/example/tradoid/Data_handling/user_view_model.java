package com.example.tradoid.Data_handling;

import android.widget.Filter;
import android.widget.Filterable;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class user_view_model extends ViewModel implements Filterable {

    Data_Layer data;
    List<user_data> data_list;
    List<user_data> full_data_list;

    public void setFragment(String fragment) {
        this.data = new Data_Layer(fragment);
        this.data_list = data.get_User_data();
        this.full_data_list = new ArrayList<>(data_list);
    }

    // getter for the list of data
    public List<user_data> getData_list(){
        return data_list;
    }

    public void filterData(String query){
        //filter outside of RecycleView Adapter through data_list
        getFilter().filter(query);
    }

    @Override
    public Filter getFilter() {
        return userFilter;
    }

    // building our own filter, works in the background so the app wont freeze
    private final Filter userFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<user_data> filteredList = new ArrayList<>();

            // if there was the search length was 0 or null(no search) just return everything
            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(full_data_list);
            }
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (user_data item: full_data_list) {
                    if (item.toFilterBy().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            data_list.clear();
            data_list.addAll(filteredList);
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    };
}
