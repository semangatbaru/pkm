package com.staydev.seani.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.staydev.seani.R
import com.staydev.seani.model.ContentKeranjang
import com.staydev.seani.model.Malat
import com.staydev.seani.model.Mkeranjang
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log


class AdapterAlat(
        private val list: ArrayList<Malat>,
        private val context: Context,
        private val  mIsi:TextView

) : RecyclerView.Adapter<AdapterAlat.ViewHolder>(){
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val mNamaAlat:TextView = view.findViewById(R.id.namaAlat)
        val mFoto :ImageView = view.findViewById(R.id.fotoAlat)
        val mDeskripsi :TextView = view.findViewById(R.id.deskripsiAlat)
        val mHarga :TextView = view.findViewById(R.id.hargaAlat)
        val mBtnSewa :TextView = view.findViewById(R.id.btnsewa)

    }
    private lateinit var keranjang:ArrayList<Mkeranjang>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_sewa, parent, false)


        keranjang = ArrayList()
        view.setOnClickListener {
            Toast.makeText(context, "ini itemnya", Toast.LENGTH_SHORT).show()
        }

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterAlat.ViewHolder, position: Int) {
        val  alat = list[position]
        var id_alat = alat.id_alat
        var nama_alat = alat.nama_alat
        var harga = alat.harga
        var foto = alat.foto


        val str: String = NumberFormat.getNumberInstance(Locale.US).format(harga)
        holder.mNamaAlat.text = alat.nama_alat
        holder.mHarga.text = str
        holder.mDeskripsi.text = alat.desktripsi.toString()
        var url = alat.foto
        Picasso.get().load(url).into(holder.mFoto)

        holder.mBtnSewa.setOnClickListener {
           if (!ContentKeranjang.isAlatSelected(id_alat)){
               ContentKeranjang.addAlat(id_alat,nama_alat, harga, foto)
               Toast.makeText(context, "Berhasil Menambahkan Item Ke Keranjang", Toast.LENGTH_SHORT).show()
               mIsi.text = ContentKeranjang.KERANJANG_LIST.size.toString()
           }
        }
    }
    override fun getItemCount(): Int = list.size
}