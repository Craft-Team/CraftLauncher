package com.rhgdf.craftlauncher.upgrade;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * UpdateChat
 * Dialog pembaruan dengan info versi dan changelog
 * Mirip dengan FCL UpdateDialog dengan tombol Ignore, Download, dan Nanti
 */
public class UpdateChat {

    /**
     * Tampilkan dialog update dengan data dari JSON
     * @param context Context (sebaiknya Activity)
     * @param updateData JSONObject berisi versionCode, versionName, url, description
     */
    public static void show(Context context, JSONObject updateData) {
        if (!(context instanceof Activity)) {
            Toast.makeText(context, "UpdateChat memerlukan Activity context", Toast.LENGTH_SHORT).show();
            return;
        }

        Activity activity = (Activity) context;
        
        try {
            // Ambil data dari JSON
            int versionCode = updateData.getInt("versionCode");
            String versionName = updateData.getString("versionName");
            String downloadUrl = updateData.getString("url");
            String date = updateData.optString("date", "-");

            // Ambil deskripsi dari array description
            String descText = "";
            if (updateData.has("description")) {
                JSONArray descArray = updateData.getJSONArray("description");
                if (descArray.length() > 0) {
                    JSONObject descObj = descArray.getJSONObject(0);
                    descText = descObj.optString("text", "Pembaruan tersedia untuk diunduh.");
                }
            }

            // Jika tidak ada deskripsi, gunakan default
            if (descText.isEmpty()) {
                descText = "Versi baru tersedia. Silakan unduh untuk mendapatkan fitur terbaru dan perbaikan bug.";
            }

            // Format pesan dengan info lengkap
            String message = "📅 Tanggal: " + date + "\n\n" +
                           "📝 Changelog:\n" + descText;

            // Tampilkan dialog dengan 3 tombol: Ignore, Nanti, Unduh
            new AlertDialog.Builder(activity)
                    .setTitle("🎉 Update tersedia v" + versionName)
                    .setMessage(message)
                    .setPositiveButton("⬇️ Unduh", (dialog, which) -> {
                        // Download APK
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(downloadUrl));
                        activity.startActivity(intent);
                    })
                    .setNegativeButton("⏰ Nanti", null)
                    .setNeutralButton("🚫 Abaikan Versi Ini", (dialog, which) -> {
                        // Ignore update untuk versi ini
                        UpdateChecker.setIgnore(context, versionCode);
                        Toast.makeText(context, "Update v" + versionName + " diabaikan", Toast.LENGTH_SHORT).show();
                    })
                    .setCancelable(true)
                    .show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Gagal menampilkan info pembaruan: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Tampilkan dialog update dengan parameter manual (tanpa JSON)
     * @param context Context (sebaiknya Activity)
     * @param versionCode Version code
     * @param versionName Nama versi (mis. "1.2.0")
     * @param downloadUrl URL download APK
     * @param description Deskripsi update
     */
    public static void show(Context context, int versionCode, String versionName, String downloadUrl, String description) {
        if (!(context instanceof Activity)) {
            Toast.makeText(context, "UpdateChat memerlukan Activity context", Toast.LENGTH_SHORT).show();
            return;
        }

        Activity activity = (Activity) context;

        try {
            // Jika tidak ada deskripsi, gunakan default
            String descText = description;
            if (descText == null || descText.isEmpty()) {
                descText = "Versi baru tersedia. Silakan unduh untuk mendapatkan fitur terbaru dan perbaikan bug.";
            }

            String message = "📝 Changelog:\n" + descText;

            // Tampilkan dialog dengan 3 tombol
            new AlertDialog.Builder(activity)
                    .setTitle("🎉 Update tersedia v" + versionName)
                    .setMessage(message)
                    .setPositiveButton("⬇️ Unduh", (dialog, which) -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(downloadUrl));
                        activity.startActivity(intent);
                    })
                    .setNegativeButton("⏰ Nanti", null)
                    .setNeutralButton("🚫 Abaikan Versi Ini", (dialog, which) -> {
                        UpdateChecker.setIgnore(context, versionCode);
                        Toast.makeText(context, "Update v" + versionName + " diabaikan", Toast.LENGTH_SHORT).show();
                    })
                    .setCancelable(true)
                    .show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Gagal menampilkan info pembaruan: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Tampilkan dialog update sederhana (backward compatibility)
     * @param context Context (sebaiknya Activity)
     * @param versionName Nama versi
     * @param downloadUrl URL download
     * @param description Deskripsi
     */
    public static void show(Context context, String versionName, String downloadUrl, String description) {
        show(context, 0, versionName, downloadUrl, description);
    }
}
