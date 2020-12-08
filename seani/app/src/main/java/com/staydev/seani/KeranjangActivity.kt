package com.staydev.seani

//import com.staydev.seani.adapter.AdapterKeranjang

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.staydev.seani.adapter.AdapterKeranjang
import com.staydev.seani.model.ContentKeranjang
import com.staydev.seani.model.Mkeranjang

class KeranjangActivity : AppCompatActivity() {
    private lateinit var keranjang:ArrayList<Mkeranjang>


    private lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang)

        keranjang = ArrayList()
        mRecyclerView = findViewById(R.id.RecyclerKeranjang)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = AdapterKeranjang(ContentKeranjang.KERANJANG_LIST, this)
        Log.d("size", keranjang.size.toString())


    }


}