package com.staydev.seani

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton


class ActivityKeranjang : AppCompatActivity() {
    private lateinit var mBack : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang)

        mBack = findViewById(R.id.icon_back_keranjang)
        mBack.setOnClickListener{

        }
    }
}


