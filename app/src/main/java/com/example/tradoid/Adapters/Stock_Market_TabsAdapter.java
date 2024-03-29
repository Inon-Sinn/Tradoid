package com.example.tradoid.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tradoid.fragments.Portfolio;
import com.example.tradoid.fragments.Stocks;
import com.example.tradoid.fragments.Bookmarks;

public class Stock_Market_TabsAdapter extends FragmentStateAdapter {


    public Stock_Market_TabsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new Bookmarks();
            case 2:
                return new Portfolio();
            default:
                return new Stocks();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
