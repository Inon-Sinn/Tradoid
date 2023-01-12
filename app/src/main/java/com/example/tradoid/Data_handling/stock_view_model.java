package com.example.tradoid.Data_handling;

import android.widget.Filter;
import android.widget.Filterable;

import androidx.lifecycle.ViewModel;

import com.example.tradoid.backend.HttpUtils;
import com.example.tradoid.backend.Stock;
import com.example.tradoid.backend.User;

import java.util.ArrayList;
import java.util.List;



public class stock_view_model extends ViewModel implements Filterable {

    List<Stock> data_list;
    List<Stock> full_data_list;

    // when we want list that
    public void setStockList(List<Stock> stockList) {
        this.data_list = stockList;
        this.full_data_list = new ArrayList<>(data_list);
    }

    // getter for the list of data
    public List<Stock> getDataList(){
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
            List<Stock> filteredList = new ArrayList<>();

            // if there was the search length was 0 or null(no search) just return everything
            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(full_data_list);
            }
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Stock item: full_data_list) {
                    if (item.getStockId().toLowerCase().contains(filterPattern)){
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
