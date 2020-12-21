package com.staydev.seani

import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import com.staydev.seani.model.ContentKeranjang
import com.staydev.seani.model.Mkeranjang

class PesananActivity : AppCompatActivity() {
    private lateinit var keranjang:ArrayList<Mkeranjang>
    private lateinit var mBack: AppCompatImageButton
    private lateinit var mId: TextView
    private lateinit var mId2: TextView
    private lateinit var mId3: TextView
    private lateinit var mDate:CalendarView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesanan)
        keranjang = ArrayList()
        mId = findViewById(R.id.id_alat)
        mId2 = findViewById(R.id.id_alat2)
        mId3 = findViewById(R.id.id_alat3)
        mDate = findViewById(R.id.tglSewa)

        mDate.minDate = System.currentTimeMillis()

        mBack = findViewById(R.id.icon_back_pesanan)
        mBack.setOnClickListener {
            onBackPressed()
        }
        getData()

    }
    private fun getData(){
        val builder = StringBuilder()
        for (cat in ContentKeranjang.KERANJANG_LIST){


            builder.append(cat.nama_alat + "\n")
            Log.e("okok", cat.id_alat.toString())
        }
        mId.text = builder
        mId2.text = builder
        mId3.text = builder




    }
}