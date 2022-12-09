package com.example.tradoid.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tradoid.fragments.Portfolio;
import com.example.tradoid.fragments.Stock;
import com.example.tradoid.fragments.Watchlist;

public class TabsAdapter extends FragmentStateAdapter {


    public TabsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Stock();
            case 1:
                return new Watchlist();
            case 2:
                return new Portfolio();
            default:
                return new Stock();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
