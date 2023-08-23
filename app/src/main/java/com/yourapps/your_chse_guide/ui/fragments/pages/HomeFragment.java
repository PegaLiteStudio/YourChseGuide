package com.yourapps.your_chse_guide.ui.fragments.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.yourapps.your_chse_guide.adapters.SubjectsFragmentAdaptor;
import com.yourapps.your_chse_guide.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Arts"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Commerce"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Science"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Optional"));


        binding.viewPager.setAdapter(new SubjectsFragmentAdaptor(getChildFragmentManager(), 4));
        binding.viewPager.setOffscreenPageLimit(4);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        return binding.getRoot();
    }
}