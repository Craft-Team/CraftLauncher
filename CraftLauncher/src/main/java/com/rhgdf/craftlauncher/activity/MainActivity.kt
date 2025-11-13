package com.rhgdf.craftlauncher.activity

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rhgdf.craftlauncher.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // MainActivity adalah layar utama launcher setelah EULA diterima
        // Tambahkan logika launcher di sini (mis. tampilkan daftar server, dll)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Handle perubahan konfigurasi untuk multi-window/split screen
    }
}
