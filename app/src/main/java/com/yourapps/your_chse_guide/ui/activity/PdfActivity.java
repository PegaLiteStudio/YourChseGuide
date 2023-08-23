package com.yourapps.your_chse_guide.ui.activity;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.sigma.niceswitch.NiceSwitch;
import com.yourapps.your_chse_guide.R;
import com.yourapps.your_chse_guide.databinding.ActivityPdfBinding;
import com.yourapps.your_chse_guide.functions.loader.Loader;
import com.yourapps.your_chse_guide.functions.loader.LoaderCallBack;

import java.io.File;
import java.util.Objects;

public class PdfActivity extends AppCompatActivity {

    ActivityPdfBinding binding;
    AlertDialog alertDialog;
    ProgressDialog progressDialog, progressDialog2;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Prevent from screenshot and screen recording */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        binding = ActivityPdfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        /* Interstitial Ads */
        AdRequest adRequest = new AdRequest.Builder().build();


        //TODO ad interstitial ad id
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.d(TAG, loadAdError.toString());
                mInterstitialAd = null;
            }
        });

        Objects.requireNonNull(getSupportActionBar()).setTitle(getIntent().getStringExtra("name"));

        /* Checks if the PDF already exist in storage */
        if (new File(getCacheDir(), getIntent().getStringExtra("path").replaceAll("/", "-") + ".pdf").exists()) {
            /* Show the pdf from storage */
            binding.pdfView.fromFile(new File(getCacheDir(), getIntent().getStringExtra("path").replaceAll("/", "-") + ".pdf")).linkHandler(null).load();
            loadBannerAd();
        } else {
            progressDialog = new ProgressDialog(PdfActivity.this);
            progressDialog.setTitle(getIntent().getStringExtra("name"));
            progressDialog.setMessage("Checking...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();

            progressDialog2 = new ProgressDialog(PdfActivity.this);
            progressDialog2.setTitle(getIntent().getStringExtra("name"));
            progressDialog2.setMessage("Downloading...");
            progressDialog2.setIndeterminate(false);
            progressDialog2.setCancelable(false);
            progressDialog2.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

            /* Download the PDF */
            Loader loader = new Loader(PdfActivity.this, getIntent().getStringExtra("path"), progressDialog, progressDialog2, new LoaderCallBack() {
                @Override
                public void Success(String path) {
                    progressDialog2.dismiss();
                    binding.pdfView.fromFile(new File(getCacheDir(), getIntent().getStringExtra("path").replaceAll("/", "-") + ".pdf")).onDraw((canvas, pageWidth, pageHeight, displayedPage) -> {
                    }).load();
                    loadBannerAd();
                }

                @Override
                public void Failed() {
                    progressDialog.dismiss();
                    alertDialog = new AlertDialog.Builder(PdfActivity.this).setTitle("Pdf Not Available!").setMessage("This Pdf Is Currently Not Available").setPositiveButton("Exit", null).setCancelable(true).show();
                    Button exit = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    exit.setOnClickListener(view -> finish());

                }
            });
            loader.start();

            /* Cancel the download Process & exit the activity */
            progressDialog2.setButton(DialogInterface.BUTTON_POSITIVE, "Cancel", (dialog, which) -> {
                loader.cancel();
                finish();
            });

        }

    }

    private void loadBannerAd() {
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);

        //TODO add banner ad ids
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        binding.adView.loadAd(new AdRequest.Builder().build());

    }

    @Override
    public void onBackPressed() {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(this);
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (progressDialog2 != null && progressDialog2.isShowing()) {
            progressDialog2.dismiss();
        }
        super.onDestroy();
    }

    /**
     * Switch for enabling od disabling Dask Theme
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.switch_action, menu);
        MenuItem itemSwitch = menu.findItem(R.id.switch_action_bar);

        itemSwitch.setActionView(R.layout.use_switch);

        NiceSwitch sw = itemSwitch.getActionView().findViewById(R.id.switch2);

        sw.setOnCheckedChangedListener(checked -> {
            {
                if (checked) {
                    binding.pdfView.fromFile(new File(getCacheDir(), getIntent().getStringExtra("path").replaceAll("/", "-") + ".pdf")).nightMode(true).load();
                } else {
                    binding.pdfView.fromFile(new File(getCacheDir(), getIntent().getStringExtra("path").replaceAll("/", "-") + ".pdf")).nightMode(false).load();
                }
            }
        });
        return true;
    }

}