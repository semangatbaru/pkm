package com.staydev.seani.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.staydev.seani.R
import com.staydev.seani.model.Malat


class AdapterAlat(
        private val list:ArrayList<Malat>,
        private val context: Context

) : RecyclerView.Adapter<AdapterAlat.ViewHolder>(){
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val mNamaAlat:TextView = view.findViewById(R.id.namaAlat)
        val mFoto :ImageView = view.findViewById(R.id.fotoAlat)
        val mStok :TextView = view.findViewById(R.id.stokAlat)
        val mHarga :TextView = view.findViewById(R.id.hargaAlat)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_sewa, parent, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterAlat.ViewHolder, position: Int) {
        val  alat = list[position]
        holder.mNamaAlat.text = alat.nama_alat
        holder.mHarga.text = alat.harga.toString()
        holder.mStok.text = alat.stok.toString()
        var url = alat.foto
        Picasso.get().load(url).into(holder.mFoto)

    }

    override fun getItemCount(): Int = list.size
}