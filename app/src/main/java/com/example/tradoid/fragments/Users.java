package com.example.tradoid.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.tradoid.Adapters.User_List_RecycleView_Adapter;
import com.example.tradoid.Data_handling.user_data;
import com.example.tradoid.Data_handling.user_view_model;
import com.example.tradoid.R;
import com.example.tradoid.backend.*;
import com.example.tradoid.firebase.model.UserListViewModel;
import com.google.gson.Gson;

import java.util.List;

public class Users extends Fragment {

    RecyclerView recyclerView;
    user_view_model view_model;
    User_List_RecycleView_Adapter adapter;
    boolean build = false;

    String adminId;

    public HttpUtils client = new HttpUtils();

    public Users(String adminId){
        this.adminId = adminId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        // Creating the Recycle View - the list
        recyclerView = view.findViewById(R.id.recyclerView_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        // Connect to View Model
        view_model = new ViewModelProvider(this).get(user_view_model.class);

        //getting the user list
        Response response = client.sendGet("user_list");
        if (response.passed()){
            UserList userList = new Gson().fromJson(response.getData(), UserList.class);
            view_model.setUserList(userList.getUserList());

            // Calling the Adapter
            adapter = new User_List_RecycleView_Adapter(getActivity(), view_model.getData_list(), adminId);
            recyclerView.setAdapter(adapter);

            build = true;
        }
        return view;
    }

    // Update the Adapter after each search
    public void UpdateAdapter(String newText){
        if (build) {
            view_model.filterData(newText);
            adapter.updateList(view_model.getData_list());
        }
    }
}