package com.staydev.seani.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.staydev.seani.R
import com.staydev.seani.model.ContentKeranjang
import com.staydev.seani.model.Malat
import com.staydev.seani.model.Mkeranjang
import com.staydev.seani.model.Msewa
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterStatus (
        private val list: ArrayList<Msewa>,
        private val context: Context

) : RecyclerView.Adapter<AdapterStatus.ViewHolder>(){
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val mIdSewa: TextView = view.findViewById(R.id.idSewa)
        val mTanggal : TextView = view.findViewById(R.id.tanggalSewa)
        val mTotal : TextView = view.findViewById(R.id.totalSewa)
        val mDetail : ImageView = view.findViewById(R.id.detailSewa)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_status, parent, false)





        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterStatus.ViewHolder, position: Int) {
        val  sewa = list[position]
        var total = sewa.total



        val str: String = NumberFormat.getNumberInstance(Locale.US).format(total)
        holder.mIdSewa.text = sewa.id_sewa
        holder.mTanggal.text = sewa.tgl_sewa
        holder.mTotal.text = str



        holder.mDetail.setOnClickListener {
            Toast.makeText(context, "detail", Toast.LENGTH_SHORT).show()
        }


    }

    override fun getItemCount(): Int = list.size
}