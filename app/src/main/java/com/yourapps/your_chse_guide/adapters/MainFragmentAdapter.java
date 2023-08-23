package com.yourapps.your_chse_guide.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.yourapps.your_chse_guide.ui.fragments.pages.AboutFragment;
import com.yourapps.your_chse_guide.ui.fragments.pages.HomeFragment;
import com.yourapps.your_chse_guide.ui.fragments.pages.YoutubeFragment;

public class MainFragmentAdapter extends FragmentPagerAdapter {
    int tab_count;

    HomeFragment homeFragment = new HomeFragment();
    YoutubeFragment dashboardFragment = new YoutubeFragment();
    AboutFragment notificationsFragment = new AboutFragment();

    public MainFragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tab_count = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return dashboardFragment;
            case 1:
                return homeFragment;
            case 2:
                return notificationsFragment;

        }
        return homeFragment;
    }

    @Override
    public int getCount() {
        return tab_count;


    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "Arts";

        } else if (position == 1) {
            title = "Commerce";


        } else if (position == 2) {
            title = "Science";

        } else if (position == 3) {
            title = "optional";

        }
        return title;
    }

}
