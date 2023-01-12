package com.example.tradoid.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.tradoid.Adapters.Stock_Market_RecycleView_Adapter;
import com.example.tradoid.Data_handling.stock_view_model;
import com.example.tradoid.Data_handling.user_data;
import com.example.tradoid.R;
import com.example.tradoid.Stock_Market;
import com.example.tradoid.backend.HttpUtils;
import com.example.tradoid.backend.Response;
import com.example.tradoid.backend.StockList;
import com.example.tradoid.backend.User;
import com.google.gson.Gson;

// A Fragment of the Watchlist tab in the Stock Market Activity
public class Bookmarks extends Fragment {

    RecyclerView recyclerView;
    stock_view_model view_model;
    Stock_Market_RecycleView_Adapter adapter;

    User user;

    public HttpUtils client = new HttpUtils();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // get UserID;
        user = ((Stock_Market) requireActivity()).getUser();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        // Connect to View Model
        view_model = new ViewModelProvider(this).get(stock_view_model.class);

        // TODO change to bookmarked
        Response response = client.sendGet("bookmarked/" + user.getUserId());
        if (response.passed()){
            StockList stockList = new Gson().fromJson(response.getData(), StockList.class);
            view_model.setStockList(stockList.getStockList());
        }


        // Creating the Recycle View - the list
        recyclerView = view.findViewById(R.id.recyclerView_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        // Calling the Adapter
        adapter = new Stock_Market_RecycleView_Adapter(getActivity(),view_model.getDataList(), user);
        recyclerView.setAdapter(adapter);

        return view;
    }

    // Update the Adapter after each search
    public void UpdateAdapter(String newText){
        view_model.filterData(newText);
        adapter.updateList(view_model.getDataList());
    }
}