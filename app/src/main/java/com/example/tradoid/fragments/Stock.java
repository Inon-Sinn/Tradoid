package com.example.tradoid.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.tradoid.Adapters.RecycleView_Adapter;
import com.example.tradoid.R;

// A Fragment of the Stock tab in the Stock Market Activity
public class Stock extends Fragment {

    RecyclerView recyclerView;

    // TODO only for testing remove later
    String s1[], s2[];
    int images[] = {R.drawable.ic_arrow_back, R.drawable.ic_ban, R.drawable.ic_bookmark,
            R.drawable.ic_dashboard, R.drawable.ic_identity, R.drawable.ic_kebab_menu,
            R.drawable.ic_menu, R.drawable.ic_portfolio, R.drawable.ic_list, R.drawable.ic_logout};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stock, container, false);

        // list for the Recycler views TODO remove later
        s1 = getResources().getStringArray(R.array.my_family);
        s2 = getResources().getStringArray(R.array.Order);


        recyclerView = view.findViewById(R.id.recyclerView_stock);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        RecycleView_Adapter adapter = new RecycleView_Adapter(getActivity(),s1,s2,images);
        recyclerView.setAdapter(adapter);

        return view;
    }


}