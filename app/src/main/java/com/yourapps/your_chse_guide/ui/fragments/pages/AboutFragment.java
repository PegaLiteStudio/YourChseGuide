package com.yourapps.your_chse_guide.ui.fragments.pages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.yourapps.your_chse_guide.databinding.FragmentAboutBinding;

public class AboutFragment extends Fragment {


    FragmentAboutBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAboutBinding.inflate(getLayoutInflater(), container, false);

        binding.instagram.setOnClickListener(v -> openURL("https://instagram.com/your_chse_guide?igshid=YmMyMTA2M2Y="));
        binding.telegram.setOnClickListener(v -> openURL("https://t.me/yourchsegroup"));
        binding.youtube.setOnClickListener(view -> openURL("https://youtube.com/c/yourCHSEguide"));
        binding.privacy.setOnClickListener(view -> openURL("https://sites.google.com/view/your-chse-guide/home"));
        binding.rateApp.setOnClickListener(view -> openURL("https://play.google.com/store/apps/details?id=com.yourapps.your_chse_guide"));


        binding.shareApp.setOnClickListener(view -> {

            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_SUBJECT, "Your CASE Guide");

            String shareMessage = "https://play.google.com/store/apps/details?id=com.yourapps.your_chse_guide";
            share.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(share, "Choose One"));


        });

        return binding.getRoot();
    }

    private void openURL(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}