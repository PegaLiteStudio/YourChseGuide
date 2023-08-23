package com.yourapps.your_chse_guide.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.yourapps.your_chse_guide.ui.fragments.streams.SubjectsFragment;

public class SubjectsFragmentAdaptor extends FragmentPagerAdapter {
    int tab_count;


    public SubjectsFragmentAdaptor(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tab_count = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new SubjectsFragment(position);
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
