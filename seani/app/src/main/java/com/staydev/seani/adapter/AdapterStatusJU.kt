package com.staydev.seani.adapter

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.staydev.seani.ActivityBeranda
import com.staydev.seani.R
import com.staydev.seani.helper.SharedPrefManager
import com.staydev.seani.helper.Urls
import com.staydev.seani.helper.VolleySingleton
import com.staydev.seani.model.Mlogin
import com.staydev.seani.model.Msewa
import org.json.JSONException
import org.json.JSONObject
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class AdapterStatusJU(
        private val list: ArrayList<Msewa>,
        private val context: Context

) : RecyclerView.Adapter<AdapterStatusJU.ViewHolder>(){
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val mIdSewa: TextView = view.findViewById(R.id.idSewa)
        val mTanggal : TextView = view.findViewById(R.id.tanggalSewa)
        val mTotal : TextView = view.findViewById(R.id.totalSewa)
        val mEdit : ImageView = view.findViewById(R.id.accept)

    }
    private lateinit var mDialogdetail:View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_ju, parent, false)





        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterStatusJU.ViewHolder, position: Int) {
        val  sewa = list[position]
        var total = sewa.total
        var tanggal = sewa.tgl_sewa
        var id_sewa = sewa.id_sewa



        val str: String = NumberFormat.getNumberInstance(Locale.US).format(total)
        val parser = SimpleDateFormat("yyyy-MM-dd")
        val formatter = SimpleDateFormat("dd MMM yyyy")
        val formattedDate = formatter.format(parser.parse(tanggal))

        holder.mIdSewa.text = sewa.id_sewa
        holder.mTanggal.text = formattedDate
        holder.mTotal.text = str



        holder.mEdit.setOnClickListener {
            Toast.makeText(context, "jadwal disetujui", Toast.LENGTH_SHORT).show()
            update(id_sewa, tanggal)
        }


    }
    override fun getItemCount(): Int = list.size

    private fun update(id_sewa:String, tanggal:String) {


        //if everything is fine

        val stringRequest = object : StringRequest(
                Method.PUT, Urls.URL_JU + "/"+ id_sewa,
                Response.Listener { response ->
                    Log.d("response", response)


                    try {
                        //converting response to json object
                        val obj = JSONObject(response)

                        //Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()

                        //if no error in response
                        if (obj.getString("success") == "true") {
                            //Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()
                            getData()
                        } else {
                            Toast.makeText(
                                    context,
                                    obj.getString("message"),
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error -> Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["tgl_sewa"] = tanggal
                params["status"] = "disetujui"

                Log.e("param", params.toString())
                return params
            }
        }
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest)
    }
    private fun getData() {
        val user = SharedPrefManager.getInstance(context).mlogin
        val token = user.token.toString()
        val id_user = user.id_user.toString()


        val stringRequest = object : StringRequest(
                Method.POST, Urls.URL_JadwalUlang,
                Response.Listener { response ->
                    Log.d("respons", response);


                    try {
                        //converting response to json object
                        val obj = JSONObject(response)


                        //Toast.makeText(activity, obj.getString("message"), Toast.LENGTH_LONG).show()

                        //if no error in response
                        if (obj.getString("success") == "true") {
                            list.clear()

                            Log.i("data", obj.getString("data"));
                            val dataJson = obj.getJSONArray("data")

                            for (i in 0 until dataJson.length()) {
                                val data = dataJson.getJSONObject(i)
                                list.add(
                                        Msewa(
                                                data.getString("id_sewa"),
                                                data.getInt("id_user"),
                                                data.getString("tgl_sewa"),
                                                data.getInt("total"),
                                                data.getString("status"),
                                                data.getDouble("lahan")
                                        )
                                )
                            }
                            notifyDataSetChanged()

                        } else {
                            Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT)
                                    .show()

                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(
                            context,
                            error.message,
                            Toast.LENGTH_SHORT
                    ).show()
                    Log.d("eror", error.message.toString())
                }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {

                val params = HashMap<String, String>()
                params["id_user"] = id_user
                return params
            }
            override fun getHeaders(): Map<String, String> {
                val headers: MutableMap<String, String> = HashMap()
                headers["Accept"] = "application/json"
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest)
    }
}