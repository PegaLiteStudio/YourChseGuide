package com.yourapps.your_chse_guide.ui.activity;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.yourapps.your_chse_guide.adapters.ChapterAdapter;
import com.yourapps.your_chse_guide.databinding.ActivitySelectChapterBinding;
import com.yourapps.your_chse_guide.functions.utils.Utils;
import com.yourapps.your_chse_guide.models.SubjectsModel;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectChapterActivity extends AppCompatActivity {

    ActivitySelectChapterBinding binding;

    List<SubjectsModel> subjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectChapterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);

        //TODO add id
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);


        String stream = getIntent().getStringExtra("stream");
        String subject = getIntent().getStringExtra("subject");
        String book = getIntent().getStringExtra("book");

        Objects.requireNonNull(getSupportActionBar()).setTitle(book);

        getWindow().setNavigationBarColor(Color.WHITE);

        try {

            JSONArray array = Utils.data.getJSONObject(stream).getJSONObject(subject).getJSONArray(book);
            for (int i = 0; i < array.length(); i++) {
                subjects.add(new SubjectsModel(array.getString(i)));
            }
            ChapterAdapter adapter = new ChapterAdapter(stream, subject, book, subjects);

            binding.recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}