package com.example.tradoid.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.tradoid.Adapters.RecycleView_Adapter;
import com.example.tradoid.Data_handling.stock_market_view_model;
import com.example.tradoid.R;
//import com.example.tradoid.StockMarket_ViewModel;

// A Fragment of the Stock tab in the Stock Market Activity
public class Stock extends Fragment{

    RecyclerView recyclerView;
    stock_market_view_model view_model;
    RecycleView_Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stock, container, false);

        // Connect to View Model
        view_model = new ViewModelProvider(this).get(stock_market_view_model.class);

        // Creating the Recycle View - the list
        recyclerView = view.findViewById(R.id.recyclerView_stock);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        // Calling the Adapter
        adapter = new RecycleView_Adapter(getActivity(),view_model.newData());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toast.makeText(getActivity(),"OnViewCreated",Toast.LENGTH_SHORT).show();
        view_model.getSearchResults().observe(getViewLifecycleOwner(),searchResults ->{
            adapter.updateList(searchResults);
        });
    }

    public void UpdateAdapter(String newText){
        view_model.filterData(newText);
        view_model.getSearchResults().observe(getViewLifecycleOwner(),searchResults ->{
            adapter.updateList(searchResults);
        });
    }
}