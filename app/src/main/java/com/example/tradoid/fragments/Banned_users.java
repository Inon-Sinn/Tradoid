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
import com.example.tradoid.Adapters.User_List_RecycleView_Adapter;
import com.example.tradoid.Data_handling.stock_view_model;
import com.example.tradoid.Data_handling.user_view_model;
import com.example.tradoid.R;

public class Banned_users extends Fragment {

    RecyclerView recyclerView;
    user_view_model view_model;
    User_List_RecycleView_Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_banned_users, container, false);

        // Connect to View Model
        view_model = new ViewModelProvider(this).get(user_view_model.class);
        view_model.setFragment("banned");

        // Creating the Recycle View - the list
        recyclerView = view.findViewById(R.id.recyclerView_banned_users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        // Calling the Adapter
        adapter = new User_List_RecycleView_Adapter(getActivity(),view_model.getData_list());
        recyclerView.setAdapter(adapter);

        return view;
    }

    // Update the Adapter after each search
    public void UpdateAdapter(String newText){
        view_model.filterData(newText);
        adapter.updateList(view_model.getData_list());
    }
}