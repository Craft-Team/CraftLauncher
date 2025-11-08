package com.rhgdf.craftlauncher.upgrade;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.rhgdf.craftlauncher.R;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * UpdateChat
 * Menampilkan dialog pembaruan dengan info versi dan changelog
 */
public class UpdateChat {

    public static void show(Context context, JSONObject updateData) {
        try {
            String versionName = updateData.getString("versionName");
            String downloadUrl = updateData.getString("url");

            // Deskripsi (ambil yang lang en)
            String desc = "";
            if (updateData.has("description")) {
                JSONArray descArray = updateData.getJSONArray("description");
                for (int i = 0; i < descArray.length(); i++) {
                    JSONObject item = descArray.getJSONObject(i);
                    if (item.optString("lang", "").equalsIgnoreCase("en")) {
                        desc = item.optString("text", "");
                        break;
                    }
                }
            }

            // Judul dari string.xml + versi
            String title = context.getString(R.string.update_message_checking) + " v" + versionName;

            new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(desc.isEmpty() ? context.getString(R.string.update_message_available) : desc)
                    .setPositiveButton(context.getString(R.string.update_button_download), (d, w) -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(downloadUrl));
                        context.startActivity(intent);
                    })
                    .setNegativeButton(context.getString(R.string.update_button_cancel), null)
                    .show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
