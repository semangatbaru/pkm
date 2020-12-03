package com.staydev.seani

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.staydev.seani.adapter.AdapterAlat
import com.staydev.seani.helper.SharedPrefManager
import com.staydev.seani.helper.Urls
import com.staydev.seani.helper.VolleySingleton
import com.staydev.seani.model.Malat
import org.json.JSONException
import org.json.JSONObject

class SewaFragment : Fragment() {
    private lateinit var root:View
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var list:ArrayList<Malat>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_sewa, container, false)

        list = ArrayList()


        Toast.makeText(context, "oke", Toast.LENGTH_LONG).show()
        mRecyclerView = root.findViewById(R.id.RecyclerSewa)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)

        getAlat()
        return root
    }
    private fun getAlat() {
        val user = SharedPrefManager.getInstance(requireContext()).mlogin
        val token = user.token.toString()

        val stringRequest = object : StringRequest(
                Method.GET, Urls.URL_Alat,
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

                            for (i in 0 until dataJson.length()){
                                val data = dataJson.getJSONObject(i)
                                list.add(
                                        Malat(
                                                data.getInt("id_alat"),
                                                data.getString("nama_alat"),
                                                data.getInt("stok"),
                                                data.getInt("harga"),
                                                data.getString("foto"),
                                                data.getString("deskripsi")
                                        )
                                )
                            }
                            mRecyclerView.adapter= AdapterAlat(list,requireContext())

                        } else {
                            Toast.makeText(activity, obj.getString("message"), Toast.LENGTH_SHORT)
                                    .show()

                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(
                            activity,
                            error.message,
                            Toast.LENGTH_SHORT
                    ).show()
                    Log.d("eror", error.message.toString())
                }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {

                val params = HashMap<String, String>()
                return params
            }
            override fun getHeaders(): Map<String, String> {
                val headers: MutableMap<String, String> = HashMap()
                headers["Accept"] = "application/json"
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }


        VolleySingleton.getInstance(requireContext()).addToRequestQueue(stringRequest)
    }
}