package com.always.trust.ratelibary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.always.rateuslib.RateDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RateDialog().create(this, "").show()
    }
}