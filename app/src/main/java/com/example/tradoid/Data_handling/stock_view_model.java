package com.example.tradoid.Data_handling;

import android.widget.Filter;
import android.widget.Filterable;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;



public class stock_view_model extends ViewModel implements Filterable {

    //TODO test usign static
    user_data user;
    Data_Layer data;
    List<stock_data> data_list;
    List<stock_data> full_data_list;

    // when we want list that
    public void all_stocks(String fragment) {
        this.data = new Data_Layer(fragment);
        this.data_list = data.get_Stocks_data();
        this.full_data_list = new ArrayList<>(data_list);
    }

    public void setUser(user_data user, String fragment){
        this.user = user;
        this.data = new Data_Layer(user, fragment);
        this.data_list = data.get_Stocks_data();
        this.full_data_list = new ArrayList<>(data_list);
    }

    // getter for the list of data
    public List<stock_data> getData_list(){
        return data_list;
    }

    public void filterData(String query){
        //filter outside of RecycleView Adapter through data_list
        getFilter().filter(query);
    }

    @Override
    public Filter getFilter() {
        return stockFilter;
    }

    // building our own filter, works in the background so the app wont freeze
    private final Filter stockFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<stock_data> filteredList = new ArrayList<>();

            // if there was the search length was 0 or null(no search) just return everything
            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(full_data_list);
            }
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (stock_data item: full_data_list) {
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
