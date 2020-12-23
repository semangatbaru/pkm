package com.staydev.seani

import android.os.Bundle
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.staydev.seani.model.ContentKeranjang
import com.staydev.seani.model.Mkeranjang
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class PesananActivity : AppCompatActivity() {
    private lateinit var keranjang:ArrayList<Mkeranjang>
    private lateinit var mBack: AppCompatImageButton
    private lateinit var mId: TextView
    private lateinit var mQty: TextView
    private lateinit var mGrandTotal: TextView
    private lateinit var mDate:CalendarView
    private lateinit var valueGrandTotal: String
    private lateinit var valueQty: String
    private lateinit var mLuas:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesanan)
        keranjang = ArrayList()
        mId = findViewById(R.id.id_alat)
        mQty = findViewById(R.id.qty)
        mGrandTotal = findViewById(R.id.grandTotal)
        mDate = findViewById(R.id.tglSewa)
        mLuas  =  findViewById(R.id.luasLahan)
        mLuas.doAfterTextChanged {
            if (mLuas.text.toString().isNotEmpty()){
                mGrandTotal.isVisible=true
                getTotal()
            }else{
                mGrandTotal.isVisible=false
            }
        }
        mGrandTotal.isVisible=false

        mDate.minDate = System.currentTimeMillis()

        mBack = findViewById(R.id.icon_back_pesanan)
        mBack.setOnClickListener {
            onBackPressed()
        }

        getData()


    }
    private fun getTotal(){
        val okok = ContentKeranjang.totalQTY() * mLuas.text.toString().toDouble()

        valueGrandTotal = NumberFormat.getNumberInstance(Locale.US).format(okok)
        mGrandTotal.text = valueGrandTotal


    }
    private fun getData(){
        val namaAlat = StringBuilder()
        val qtyAlat = StringBuilder()

        for (cat in ContentKeranjang.KERANJANG_LIST){


            namaAlat.append(cat.nama_alat + "\n")

            valueQty = NumberFormat.getNumberInstance(Locale.US).format(cat.harga)
            qtyAlat.append(valueQty, "\n")



        }

        mId.text = namaAlat
        mQty.text = qtyAlat





    }
}

