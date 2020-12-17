package com.staydev.seani

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.staydev.seani.helper.SharedPrefManager
import com.staydev.seani.helper.Urls
import com.staydev.seani.helper.VolleySingleton
import org.json.JSONException
import org.json.JSONObject

class PrivaciActivity : AppCompatActivity() {
    private lateinit var mIsi: TextView
    private lateinit var mBack : AppCompatImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privaci)
        mIsi = findViewById(R.id.isiKP)
        mBack = findViewById(R.id.icon_back_privacy)
        mBack.setOnClickListener {
            onBackPressed()
        }
        privacy()
    }
    private fun privacy() {
        val user = SharedPrefManager.getInstance(this).mlogin
        val token = user.token.toString()

        val stringRequest = object : StringRequest(
                Method.POST, Urls.URL_Config,
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
                            val isi = obj.getString("value")
                            mIsi.text = isi.toString()


                        } else {
                            startActivity(Intent(applicationContext, RegisterActivity::class.java))
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["id_config"] = "3"
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
}