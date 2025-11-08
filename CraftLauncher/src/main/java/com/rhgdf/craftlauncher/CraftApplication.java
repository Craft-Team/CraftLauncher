package com.rhgdf.craftlauncher;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.rhgdf.craftlauncher.fragments.EulaFragment;
import com.rhgdf.craftlauncher.upgrade.UpdateChecker;

/**
 * CraftApplication.java
 * Class ini dijalankan saat aplikasi start.
 * Cocok untuk inisialisasi global seperti update checker.
 */
public class CraftApplication extends Application {

    private static CraftApplication instance;

    public static CraftApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // Contoh: cek update saat aplikasi start
        checkForUpdates();
    }

    /**
     * Memeriksa pembaruan
     */
    private void checkForUpdates() {
        // Kita butuh Context untuk menampilkan Toast / dialog
        Context context = getApplicationContext();

        UpdateChecker.check(context, isUpdated -> {
            if (isUpdated) {
                // Sudah versi terbaru, bisa lanjut
                showToast("Aplikasi sudah terbaru.");
            } else {
                // Ada versi baru, UpdateChecker akan menampilkan dialog download
                showToast("Versi baru tersedia!");
            }
        });
    }

    /**
     * Utility: tampilkan Toast
     */
    private void showToast(String msg) {
        Handler handler = new Handler(getMainLooper());
        handler.post(() -> Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show());
    }

    /**
     * Contoh menampilkan EULA dari assets di Activity tertentu
     */
    public void showEula(FragmentActivity activity) {
        EulaFragment eulaFragment = EulaFragment.newInstance(accepted -> {
            if (accepted) {
                Toast.makeText(activity, "EULA diterima", Toast.LENGTH_SHORT).show();
            } else {
                activity.finish();
            }
        });

        FragmentManager fm = activity.getSupportFragmentManager();
        fm.beginTransaction()
                .replace(android.R.id.content, eulaFragment)
                .commitAllowingStateLoss();
    }
}
