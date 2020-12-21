package com.staydev.seani

//import com.staydev.seani.adapter.AdapterKeranjang

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.staydev.seani.adapter.AdapterKeranjang
import com.staydev.seani.model.ContentKeranjang
import com.staydev.seani.model.Mkeranjang

class KeranjangActivity : AppCompatActivity() {
    private lateinit var keranjang:ArrayList<Mkeranjang>
    private lateinit var mBackKeranjang: AppCompatImageButton
    private lateinit var mProses: Button


    private lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang)

        mBackKeranjang = findViewById(R.id.icon_back_keranjang)
        mProses = findViewById(R.id.btnProses)
        mProses.setOnClickListener {
            startActivity(Intent(this, PesananActivity::class.java))
        }
        mBackKeranjang.setOnClickListener{
            onBackPressed()
        }
        keranjang = ArrayList()
        mRecyclerView = findViewById(R.id.RecyclerKeranjang)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = AdapterKeranjang(ContentKeranjang.KERANJANG_LIST, this)


    }


}