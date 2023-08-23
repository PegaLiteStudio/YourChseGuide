package com.yourapps.your_chse_guide.ui.activity;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.yourapps.your_chse_guide.adapters.BooksAdapter;
import com.yourapps.your_chse_guide.databinding.ActivitySelectBookBinding;
import com.yourapps.your_chse_guide.functions.utils.Utils;
import com.yourapps.your_chse_guide.models.SelectModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * <strong>Create by SahilTheCat</strong>,
 * <p>
 * Date: 07-03-2023
 * </p>
 * <p> This Activity will show all the Books (final PDF names)</p>
 * <p>Stream->Subject->Book</p>
 */
public class SelectBookActivity extends AppCompatActivity {
    List<SelectModel> select;

    ActivitySelectBookBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);

        //TODO banner ad id
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);


        String stream = getIntent().getStringExtra("stream");
        String subject = getIntent().getStringExtra("subject");

        Objects.requireNonNull(getSupportActionBar()).setTitle(subject);

        getWindow().setNavigationBarColor(Color.WHITE);

        select = new ArrayList<>();


        try {
            Iterator<?> keys = Utils.data.getJSONObject(stream).getJSONObject(subject).keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                select.add(new SelectModel(key));

            }
            BooksAdapter adapter = new BooksAdapter(this, stream, subject, select);

            binding.recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}