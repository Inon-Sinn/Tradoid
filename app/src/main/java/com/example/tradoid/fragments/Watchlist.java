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
import com.example.tradoid.Data_handling.stock_market_view_model;
import com.example.tradoid.R;

// A Fragment of the Watchlist tab in the Stock Market Activity
public class Watchlist extends Fragment {

    RecyclerView recyclerView;
    stock_market_view_model view_model;
    Stock_Market_RecycleView_Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_watchlist, container, false);

        // Connect to View Model
        view_model = new ViewModelProvider(this).get(stock_market_view_model.class);
        view_model.setFragment("watchlist");

        // Creating the Recycle View - the list
        recyclerView = view.findViewById(R.id.recyclerView_watchlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        // Calling the Adapter
        adapter = new Stock_Market_RecycleView_Adapter(getActivity(),view_model.getData_list());
        recyclerView.setAdapter(adapter);

        return view;
    }

    // Update the Adapter after each search
    public void UpdateAdapter(String newText){
        view_model.filterData(newText);
        adapter.updateList(view_model.getData_list());
    }
}