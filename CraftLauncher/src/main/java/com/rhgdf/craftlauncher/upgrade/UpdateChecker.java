package com.rhgdf.craftlauncher.upgrade;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * UpdateChecker
 * Cek pembaruan aplikasi dari server
 * Mirip dengan FCL UpdateChecker dengan fitur auto-check, manual check, dan ignore update
 */
public class UpdateChecker {

    private static final String UPDATE_URL = "https://moonxrepo.com/version.json";
    private static final String PREFS_NAME = "CraftLauncherPrefs";
    private static final String KEY_IGNORE_UPDATE = "ignore_update_version";

    private static UpdateChecker instance;
    private boolean isChecking = false;

    public static UpdateChecker getInstance() {
        if (instance == null) {
            instance = new UpdateChecker();
        }
        return instance;
    }

    public boolean isChecking() {
        return isChecking;
    }

    public interface UpdateCallback {
        void onCheckCompleted(boolean isUpdated);
    }

    /**
     * Cek update manual (dari menu/tombol)
     * Menampilkan toast "Memeriksa pembaruan..." dan alert jika sudah terbaru
     */
    public void checkManually(Context context, UpdateCallback callback) {
        check(context, true, true, callback);
    }

    /**
     * Cek update otomatis (saat app start)
     * Tidak menampilkan toast dan alert jika sudah terbaru
     */
    public void checkAuto(Context context, UpdateCallback callback) {
        check(context, false, false, callback);
    }

    /**
     * Cek update dengan opsi custom
     */
    public void check(Context context, boolean showCheckingToast, boolean showAlertIfLatest, UpdateCallback callback) {
        if (isChecking) {
            Toast.makeText(context, "Sedang memeriksa pembaruan...", Toast.LENGTH_SHORT).show();
            return;
        }

        isChecking = true;

        if (showCheckingToast) {
            new Handler(Looper.getMainLooper()).post(() -> 
                Toast.makeText(context, "Memeriksa pembaruan...", Toast.LENGTH_SHORT).show()
            );
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            boolean isUpdated = true;
            HttpURLConnection conn = null;
            
            try {
                URL url = new URL(UPDATE_URL);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                int responseCode = conn.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new RuntimeException("HTTP error code: " + responseCode);
                }

                StringBuilder result = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) result.append(line);
                }

                // Parse JSON array
                JSONArray array = new JSONArray(result.toString());
                
                // Cari versi yang lebih baru
                int currentVersion = getCurrentVersionCode(context);
                JSONObject latestUpdate = null;
                
                for (int i = 0; i < array.length(); i++) {
                    JSONObject version = array.getJSONObject(i);
                    int versionCode = version.getInt("versionCode");
                    
                    if (versionCode > currentVersion) {
                        // Cek apakah versi ini di-ignore
                        if (!isIgnored(context, versionCode)) {
                            latestUpdate = version;
                            break;
                        }
                    }
                }

                if (latestUpdate != null) {
                    // Ada update baru
                    isUpdated = false;
                    JSONObject finalUpdate = latestUpdate;
                    new Handler(Looper.getMainLooper()).post(() -> {
                        showUpdateDialog(context, finalUpdate);
                    });
                } else {
                    // Sudah versi terbaru
                    if (showAlertIfLatest) {
                        new Handler(Looper.getMainLooper()).post(() -> 
                            Toast.makeText(context, "Aplikasi sudah versi terbaru", Toast.LENGTH_SHORT).show()
                        );
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                new Handler(Looper.getMainLooper()).post(() -> 
                    Toast.makeText(context, "Gagal memeriksa pembaruan: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
                isChecking = false;
            }

            boolean finalIsUpdated = isUpdated;
            new Handler(Looper.getMainLooper()).post(() -> {
                if (callback != null) {
                    callback.onCheckCompleted(finalIsUpdated);
                }
            });
        });
    }

    /**
     * Tampilkan dialog update menggunakan UpdateChat
     */
    private void showUpdateDialog(Context context, JSONObject updateInfo) {
        UpdateChat.show(context, updateInfo);
    }

    /**
     * Dapatkan version code aplikasi saat ini
     */
    public static int getCurrentVersionCode(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 1; // Default version code
        }
    }

    /**
     * Dapatkan version name aplikasi saat ini
     */
    public static String getCurrentVersionName(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0.0"; // Default version name
        }
    }

    /**
     * Cek apakah versi tertentu di-ignore
     */
    public static boolean isIgnored(Context context, int versionCode) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_IGNORE_UPDATE, -1) == versionCode;
    }

    /**
     * Set versi untuk di-ignore
     */
    public static void setIgnore(Context context, int versionCode) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_IGNORE_UPDATE, versionCode).apply();
    }

    /**
     * Reset ignore (untuk testing)
     */
    public static void resetIgnore(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(KEY_IGNORE_UPDATE).apply();
    }
}
