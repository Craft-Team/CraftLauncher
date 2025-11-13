package com.rhgdf.craftlauncher;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.rhgdf.craftlauncher.activity.MainActivity;
import com.rhgdf.craftlauncher.fragments.EulaFragment;
import com.rhgdf.craftlauncher.upgrade.UpdateChecker;

/**
 * CraftApplication.java
 * Class ini mengatur alur aplikasi dari splash -> cek update -> EULA -> MainActivity
 */
public class CraftApplication extends Application {

    private static CraftApplication instance;
    private static final String PREFS_NAME = "CraftLauncherPrefs";
    private static final String KEY_EULA_ACCEPTED = "eula_accepted";

    public static CraftApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    /**
     * Memulai alur aplikasi dari SplashActivity
     * Alur: Cek Update -> EULA -> MainActivity
     */
    public void startAppFlow(FragmentActivity activity) {
        // Langkah 1: Cek pembaruan
        checkForUpdates(activity);
    }

    /**
     * Langkah 1: Cek pembaruan aplikasi
     */
    private void checkForUpdates(FragmentActivity activity) {
        // Gunakan checkAuto() untuk cek otomatis tanpa toast "sudah terbaru"
        UpdateChecker.getInstance().checkAuto(activity, isUpdated -> {
            // Setelah cek update (ada atau tidak), lanjut ke EULA
            // Lanjut ke langkah 2: Cek EULA
            checkEulaStatus(activity);
        });
    }

    /**
     * Langkah 2: Cek status EULA
     * Jika sudah pernah diterima, langsung ke MainActivity
     * Jika belum, tampilkan EULA
     */
    private void checkEulaStatus(FragmentActivity activity) {
        if (isEulaAccepted()) {
            // EULA sudah pernah diterima, langsung ke MainActivity
            navigateToMainActivity(activity);
        } else {
            // EULA belum diterima, tampilkan fragment EULA
            showEulaFragment(activity);
        }
    }

    /**
     * Langkah 3: Tampilkan EULA Fragment
     */
    private void showEulaFragment(FragmentActivity activity) {
        EulaFragment eulaFragment = EulaFragment.newInstance(accepted -> {
            if (accepted) {
                // Simpan status EULA diterima
                saveEulaAccepted(true);
                Toast.makeText(activity, "EULA diterima", Toast.LENGTH_SHORT).show();
                // Lanjut ke MainActivity
                navigateToMainActivity(activity);
            } else {
                // EULA ditolak, tutup aplikasi
                Toast.makeText(activity, "EULA ditolak, aplikasi ditutup", Toast.LENGTH_SHORT).show();
                activity.finish();
            }
        });

        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, eulaFragment)
                .commitAllowingStateLoss();
    }

    /**
     * Langkah 4: Navigasi ke MainActivity
     */
    private void navigateToMainActivity(FragmentActivity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * Cek apakah EULA sudah pernah diterima
     */
    public boolean isEulaAccepted() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_EULA_ACCEPTED, false);
    }

    /**
     * Simpan status EULA
     */
    private void saveEulaAccepted(boolean accepted) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_EULA_ACCEPTED, accepted).apply();
    }

    /**
     * Reset status EULA (untuk testing atau reset aplikasi)
     */
    public void resetEulaStatus() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_EULA_ACCEPTED, false).apply();
    }
}
