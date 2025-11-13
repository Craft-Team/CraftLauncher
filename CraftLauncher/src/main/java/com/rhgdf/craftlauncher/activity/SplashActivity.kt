package com.rhgdf.craftlauncher.activity

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.rhgdf.craftlauncher.CraftApplication
import com.rhgdf.craftlauncher.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Tampilkan splash selama 2 detik, lalu mulai alur aplikasi
        Handler(Looper.getMainLooper()).postDelayed({
            startApplicationFlow()
        }, 2000)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Handle perubahan konfigurasi untuk multi-window/split screen
    }

    private fun startApplicationFlow() {
        // Panggil CraftApplication untuk mengatur seluruh alur
        // Alur: Cek Update -> EULA -> MainActivity
        CraftApplication.getInstance().startAppFlow(this)
    }
}
