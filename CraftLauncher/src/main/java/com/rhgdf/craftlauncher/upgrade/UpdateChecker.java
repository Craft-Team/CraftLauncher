package com.rhgdf.craftlauncher.upgrade;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.rhgdf.craftlauncher.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UpdateChecker {

    private static final String UPDATE_URL = "https://moonxrepo.com/version.json";
    private static final int CURRENT_VERSION_CODE = 1;

    public interface UpdateCallback {
        void onCheckCompleted(boolean isUpdated);
    }

    public static void check(Context context, UpdateCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            boolean isUpdated = true;
            try {
                URL url = new URL(UPDATE_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) result.append(line);
                reader.close();

                JSONArray array = new JSONArray(result.toString());
                JSONObject updateInfo = array.getJSONObject(0);

                int versionCode = updateInfo.getInt("versionCode");
                String versionName = updateInfo.getString("versionName");
                String downloadUrl = updateInfo.getString("url");

                if (versionCode > CURRENT_VERSION_CODE) {
                    showUpdateDialog(context, versionName, downloadUrl, updateInfo);
                    isUpdated = false;
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Gagal memeriksa pembaruan.", Toast.LENGTH_SHORT).show();
            }

            boolean finalIsUpdated = isUpdated;
            ((android.app.Activity) context).runOnUiThread(() -> callback.onCheckCompleted(finalIsUpdated));
        });
    }

    private static void showUpdateDialog(Context context, String versionName, String downloadUrl, JSONObject updateInfo) {
        try {
            JSONArray descArray = updateInfo.getJSONArray("description");
            JSONObject descObj = descArray.getJSONObject(0);
            String descText = descObj.getString("text");

            new AlertDialog.Builder(context)
                    .setTitle("Update tersedia v" + versionName)
                    .setMessage(descText)
                    .setPositiveButton("Unduh", (dialog, which) -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(downloadUrl));
                        context.startActivity(intent);
                    })
                    .setNegativeButton("Nanti", null)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
