package com.staydev.seani

import android.R.attr.x
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.staydev.seani.helper.SharedPrefManager
import com.staydev.seani.helper.Urls
import com.staydev.seani.helper.VolleySingleton
import com.staydev.seani.model.ContentKeranjang
import com.staydev.seani.model.Mkeranjang
import org.json.JSONException
import org.json.JSONObject
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set


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
    private lateinit var mSubmit:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesanan)
        keranjang = ArrayList()
        mId = findViewById(R.id.id_alat)
        mQty = findViewById(R.id.qty)
        mGrandTotal = findViewById(R.id.grandTotal)
        mDate = findViewById(R.id.tglSewa)
        mLuas  =  findViewById(R.id.luasLahan)
        mSubmit = findViewById(R.id.btnSubmit)


        mSubmit.setOnClickListener {
            postTransaksi()


        }

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
    private fun postTransaksi() {
        //first getting the values
        val user = SharedPrefManager.getInstance(this).mlogin
        val token = user.token.toString()
        val id_user = user.id_user

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val tgl_sewa = sdf.format(Date(mDate.date))

        val lahan = mLuas.text.toString()



        //validating inputs
        if (TextUtils.isEmpty(lahan)) {
            mLuas.error = "Masukkan Nama Anda"
            mLuas.requestFocus()
            return
        }
        //if everything is fine

        val stringRequest = object : StringRequest(
                Method.POST, Urls.URL_Transaksi,
                Response.Listener { response ->
                    Log.d("response", response)


                    try {
                        //converting response to json object
                        val obj = JSONObject(response)

                        //Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()

                        //if no error in response
                        if (obj.getString("success") == "true") {
                            //Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()

                            //getting the user from the response


                            finish()
                            startActivity(Intent(applicationContext, ActivityNotification::class.java))
                            Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_SHORT).show()

                        } else {
                            Toast.makeText(
                                    this,
                                    obj.getString("message"),
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = LinkedHashMap<String, String>()
                params["tgl_sewa"] = tgl_sewa.toString()
                params["total"] = ContentKeranjang.grandTotal().toString()
                params["status"] = "diproses"
                params["id_user"] = id_user.toString()
                params["lahan"] = lahan
                for ((i, cat) in ContentKeranjang.KERANJANG_LIST.withIndex()){
                    params["alats[$i][id_alat]"] = cat.id_alat.toString()
                    params["alats[$i][harga]"] = cat.harga.toString()
                }
                Log.e("param", params.toString())
                return params
            }
            override fun getHeaders(): Map<String, String> {
                val headers: MutableMap<String, String> = HashMap()
                headers["Accept"] = "application/json"
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }
        VolleySingleton.getInstance(applicationContext).addToRequestQueue(stringRequest)
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

