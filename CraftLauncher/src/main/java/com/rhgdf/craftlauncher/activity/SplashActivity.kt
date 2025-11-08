package com.rhgdf.craftlauncher.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.rhgdf.craftlauncher.R
import com.rhgdf.craftlauncher.upgrade.UpdateChecker

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Tampilkan splash selama 2 detik lalu cek update
        Handler(mainLooper).postDelayed({
            UpdateChecker.check(this) { isUpdated ->
                if (isUpdated) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    UpdateChecker.promptUpdate(this)
                }
            }
        }, 2000)
    }
}
