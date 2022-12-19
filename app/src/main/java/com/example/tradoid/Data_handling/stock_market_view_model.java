package com.example.tradoid.Data_handling;

import android.widget.Filter;
import android.widget.Filterable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tradoid.fragments.Stock;

import java.util.ArrayList;
import java.util.List;

public class stock_market_view_model extends ViewModel implements Filterable {

    public Data_Layer data = new Data_Layer();
    public List<example_Item> data_list = data.getData();
    public MutableLiveData<List<example_Item>> searchResults = new MutableLiveData<List<example_Item>>(data_list);
    public List<example_Item> example_list_full = new ArrayList<>(data_list);
    public int seed = 1;

    public List<example_Item> newData(){
//        List<example_Item> shuf = data.filter();
        return searchResults.getValue();
    }

    public void filterData(String query){
        //filter outside of RecycleView Adapter through data_list
        getFilter().filter(query);
        searchResults.setValue(data.getData());
    }

    public LiveData<List<example_Item>> getSearchResults(){
        return searchResults;
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    // building our own filter, works in the background so the app wont freeze
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<example_Item> filteredList = new ArrayList<>();

            // if there was the search length was 0 or null(no search) just return everything
            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(example_list_full);
            }
            else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (example_Item item: example_list_full) {
                    if (item.getData1().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            data_list.clear();
            data_list.addAll((List) results.values);
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data_list.clear();
            data_list.addAll((List) results.values);
        }
    };

}
