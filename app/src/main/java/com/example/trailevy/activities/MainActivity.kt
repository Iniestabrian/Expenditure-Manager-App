package com.example.trailevy.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trailevy.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
    }
}



