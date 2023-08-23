package com.yourapps.your_chse_guide.functions.loader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;

import com.yourapps.your_chse_guide.functions.retrofit.RetrofitClient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * <strong>Create by SahilTheGeek</strong>,
 * <p>
 * Date: 07-03-2023
 * </p>
 * <p> This Class with DownLoad the PDF </p>
 */
public class Loader {
    Context context;
    String pathUrl;
    ProgressDialog progressDialog, progressDialog2;
    LoaderCallBack loaderCallBack;
    LoaderTask loaderTask;

    public Loader(Context context, String pathUrl, ProgressDialog progressDialog, ProgressDialog progressDialog2, LoaderCallBack callBack) {
        this.context = context;
        this.pathUrl = pathUrl;
        this.progressDialog = progressDialog;
        this.progressDialog2 = progressDialog2;
        loaderCallBack = callBack;
    }

    public void cancel() {
        loaderTask.cancel(true);
    }

    public void start() {
        loaderTask = new LoaderTask();
        loaderTask.execute(RetrofitClient.BASE_PDF_URL + pathUrl + ".pdf");
    }

    @SuppressLint("StaticFieldLeak")
    public class LoaderTask extends AsyncTask<String, Integer, String> {
        String FilePath = "";
        private PowerManager.WakeLock wakeLock;

        @Override
        protected String doInBackground(String... strings) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(strings[0]);
                Log.e("URL", url.toString());
                connection = (HttpURLConnection) url.openConnection();
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {

                    return "Server returned HTTP " + connection.getResponseCode() + " " + connection.getResponseMessage();
                }
                ((Activity) context).runOnUiThread(() -> {
                    progressDialog.dismiss();

                    progressDialog2.show();
                });

                int filelength = connection.getContentLength();
                input = connection.getInputStream();

                FilePath = context.getCacheDir() + "/" + pathUrl.replaceAll("/", "-") + ".pdf";

                output = new FileOutputStream(FilePath);
                byte[] data = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    if (filelength > 0) {
                        publishProgress((int) (total * 100 / filelength));
                    }
                    output.write(data, 0, count);
                }
            } catch (IOException e) {
                return e.toString();
            } finally {
                try {
                    if (output != null) {
                        output.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
            wakeLock.acquire(10 * 60 * 1000L);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog2.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            wakeLock.release();
            if (s != null) {
                Log.e("d error", s);

                loaderCallBack.Failed();
            } else {

                loaderCallBack.Success("");
            }
        }

        @Override
        protected void onCancelled() {
            wakeLock.release();
            super.onCancelled();
        }
    }
}
