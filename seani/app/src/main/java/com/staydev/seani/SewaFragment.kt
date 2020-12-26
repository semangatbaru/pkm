package com.staydev.seani
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.staydev.seani.adapter.AdapterAlat
import com.staydev.seani.helper.SharedPrefManager
import com.staydev.seani.helper.Urls
import com.staydev.seani.helper.VolleySingleton
import com.staydev.seani.model.ContentKeranjang
import com.staydev.seani.model.Malat
import com.staydev.seani.model.Mkeranjang
import org.json.JSONException
import org.json.JSONObject


class SewaFragment : Fragment() {
    private lateinit var root:View
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var list:ArrayList<Malat>
    private lateinit var mButtonProses:Button
    private lateinit var mIconKeranjang:AppCompatImageButton
    private lateinit var mSwipeRefresh: SwipeRefreshLayout
    private lateinit var keranjang:ArrayList<Mkeranjang>
    private lateinit var mIsi:AppCompatTextView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_sewa, container, false)

        mButtonProses = root.findViewById(R.id.btnLanjutkan)
        mIconKeranjang = root.findViewById(R.id.icon_keranjang)
        mIsi = root.findViewById(R.id.isiKeranjang)
//        mButtonProses.isVisible  = false
        keranjang = ArrayList()

        mButtonProses.setOnClickListener {
            startActivity(Intent(context, KeranjangActivity::class.java))
        }
        mIconKeranjang.setOnClickListener {
            startActivity(Intent(context, KeranjangActivity::class.java))
        }
        if(keranjang.size == 0){
            mIsi.text = "0"
        }


        list = ArrayList()


        mRecyclerView = root.findViewById(R.id.RecyclerSewa)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(activity, 2)

        mSwipeRefresh = root.findViewById(R.id.swipeRefreshAlat)
        mSwipeRefresh.setOnRefreshListener {
            getAlat()
            mSwipeRefresh.isRefreshing = false

        }

        getAlat()
        return root
    }
    private fun getAlat() {
        val user = SharedPrefManager.getInstance(requireContext()).mlogin
        val token = user.token.toString()
        val id_user = user.id_user.toString()

        Toast.makeText(context, id_user, Toast.LENGTH_SHORT).show()


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

                            for (i in 0 until dataJson.length()) {
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
                            mRecyclerView.adapter = AdapterAlat(list, requireContext(), mIsi)

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

    override fun onResume() {
        super.onResume()
        mIsi.text = ContentKeranjang.KERANJANG_LIST.size.toString()
        Log.e("isis", ContentKeranjang.KERANJANG_LIST.size.toString())
    }
}