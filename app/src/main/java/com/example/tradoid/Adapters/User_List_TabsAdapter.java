package com.example.tradoid.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.tradoid.fragments.Banned_users;
import com.example.tradoid.fragments.Users;

public class User_List_TabsAdapter extends FragmentStateAdapter {

    String adminId;

    public User_List_TabsAdapter(@NonNull FragmentActivity fragmentActivity, String adminId) {
        super(fragmentActivity);
        this.adminId = adminId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new Banned_users(adminId);
        }
        return new Users(adminId);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
