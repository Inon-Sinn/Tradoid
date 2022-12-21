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
import java.util.Locale;

public class stock_market_view_model extends ViewModel  { //implements Filterable

    /*
    This View Model was changed to be more general, as i understood that each
    fragment creates a new View Model
     */

    Data_Layer data;
    List<stock_data> data_list;
    List<stock_data> example_list_full;

    public void setFragment(String fragment) {
        this.data = new Data_Layer(fragment);
        this.data_list = data.get_Stocks_data();
        this.example_list_full = new ArrayList<>(data_list);
    }

    public List<stock_data> newData(){
        return data_list;
    }

    public void filterData(String query){
        //filter outside of RecycleView Adapter through data_list
//        getFilter().filter(query);
    }

//    @Override
//    public Filter getFilter() {
//        return exampleFilter;
//    }
//
//    // building our own filter, works in the background so the app wont freeze
//    private Filter exampleFilter = new Filter() {
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
//            data_list.clear();
//            data_list.addAll((List) results.values);
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            data_list.clear();
//            data_list.addAll((List) results.values);
//        }
//    };

}
