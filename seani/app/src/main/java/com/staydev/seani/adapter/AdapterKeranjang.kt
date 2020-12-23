package com.staydev.seani.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.staydev.seani.R
import com.staydev.seani.model.ContentKeranjang
import com.staydev.seani.model.Mkeranjang
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class AdapterKeranjang (
        private val keranjang: MutableList<Mkeranjang>,
        private val context: Context,

): RecyclerView.Adapter<AdapterKeranjang.ViewHolder>(){
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val mNamaItem: TextView = view.findViewById(R.id.namaItem)
        val mHargaItem: TextView = view.findViewById(R.id.hargaItem)
        val mGambarAlat:ImageView = view.findViewById(R.id.gambarAlat)
        val mHapus:ImageView = view.findViewById(R.id.apaini)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterKeranjang.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_keranjang, parent, false)

        view.setOnClickListener {
            Toast.makeText(context, "okok", Toast.LENGTH_SHORT).show()
        }




        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterKeranjang.ViewHolder, position: Int) {
        val keranjang = keranjang[position]
        var id_alat = keranjang.id_alat
        var nama_alat = keranjang.nama_alat
        var harga = keranjang.harga
        var foto = keranjang.foto


        val str: String = NumberFormat.getNumberInstance(Locale.US).format(harga)
        holder.mNamaItem.text = keranjang.nama_alat
        holder.mHargaItem.text = str

        var url = keranjang.foto
        Picasso.get().load(url).into(holder.mGambarAlat)

        holder.mHapus.setOnClickListener{
            ContentKeranjang.removeAlat(keranjang)
            notifyDataSetChanged()
        }
        holder.mGambarAlat.setOnClickListener{
            Log.d("asdf", "imag")
        }

    }

    override fun getItemCount(): Int = keranjang.size

}