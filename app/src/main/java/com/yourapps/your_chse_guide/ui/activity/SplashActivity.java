package com.yourapps.your_chse_guide.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.yourapps.your_chse_guide.databinding.ActivitySplashBinding;
import com.yourapps.your_chse_guide.functions.retrofit.RetrofitClient;
import com.yourapps.your_chse_guide.functions.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).hide();

//        Thread thread = new Thread(this::loadData);
//        thread.start();

        loadData();
    }

    private void loadData() {
        binding.lottieAnimation.setVisibility(View.VISIBLE);
        RetrofitClient.getInstance().getApiInterfaces().getAppStatus(getVersionCode()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        JSONObject responseObject = new JSONObject(response.body().string());

                        String status = responseObject.getString("status");
                        binding.lottieAnimation.setVisibility(View.GONE);
                        if (status.equals("failed")) {
                            String msg = responseObject.getString("msg");
                            if (responseObject.getString("code").equals("001")) {
                                new AlertDialog.Builder(SplashActivity.this).setTitle("Update Required").setMessage("A new version of this app is available. Please update the app to continue").setCancelable(false).setPositiveButton("Update", (dialog, which) -> {
                                    dialog.dismiss();
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(msg));
                                    startActivity(browserIntent);
                                    finish();
                                }).setNegativeButton("Cancel", (dialog, which) -> {
                                    dialog.dismiss();
                                    finish();
                                }).show();
                                return;
                            }
                            new AlertDialog.Builder(SplashActivity.this).setTitle("Maintenance!").setMessage(msg).setCancelable(false).setPositiveButton("Exit", (dialog, which) -> {
                                dialog.dismiss();
                                finish();
                            }).show();
                            return;
                        }

                        if (status.equals("success")) {
                            Utils.data = responseObject.getJSONObject("data");
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
                            return;
                        }
                        Toast.makeText(SplashActivity.this, "Unknown Error Occurred", Toast.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                new AlertDialog.Builder(SplashActivity.this).setTitle("Error").setMessage("Please check your network connection and try again").setCancelable(false).setPositiveButton("Retry", (dialog, which) -> {
                    dialog.dismiss();
                    loadData();
                }).setNegativeButton("Exit", (dialog, which) -> {
                    dialog.dismiss();
                    finish();
                }).show();
                Toast.makeText(SplashActivity.this, "Unknown Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getVersionCode() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}