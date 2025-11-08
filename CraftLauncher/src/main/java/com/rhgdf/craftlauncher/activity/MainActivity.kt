package com.rhgdf.craftlauncher.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rhgdf.craftlauncher.R
import com.rhgdf.craftlauncher.fragments.EulaFragment

class MainActivity : AppCompatActivity(), EulaFragment.EulaCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, EulaFragment(this))
            .commit()
    }

    override fun onEulaResult(accepted: Boolean) {
        if (accepted) {
            // lanjut ke login atau main launcher
        } else {
            finish()
        }
    }
}
