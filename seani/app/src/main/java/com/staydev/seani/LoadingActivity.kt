package com.staydev.seani

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.staydev.seani.helper.SharedPrefManager
import com.staydev.seani.helper.Urls
import com.staydev.seani.helper.VolleySingleton
import com.staydev.seani.model.Mlogin
import org.json.JSONException
import org.json.JSONObject

class LoadingActivity : AppCompatActivity() {
    private lateinit var emailq : String
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        check()
    }
    private fun check() {
        //first getting the values

        val acct = GoogleSignIn.getLastSignedInAccount(this)
        val personEmail = acct!!.email
        if (personEmail != null) {
            emailq = personEmail
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        //base()
        val email = emailq
        //if everything is fine

        val stringRequest = object : StringRequest(
                Method.POST, Urls.URL_Check,
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
                            val userJson = obj.getJSONObject("data")

                            //creating a new user object

                            val mUser = Mlogin(
                                    userJson.getInt("id_user"),
                                    userJson.getString("email"),
                                    userJson.getString("token")
                            )
                            //storing the user in shared preferences
                            SharedPrefManager.getInstance(applicationContext).mUserLogin(mUser)
                            //starting the MainActivity

                            finish()
                            startActivity(Intent(applicationContext, ActivityBeranda::class.java))
                            Toast.makeText(applicationContext,obj.getString("message"), Toast.LENGTH_SHORT).show()

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
                params["email"] = email
                return params
            }
//            override fun getHeaders(): Map<String, String> {
//                val params: MutableMap<String, String> = HashMap()
//                params["Content-Type"] = "application/json"
//                return params
//            }
        }
        VolleySingleton.getInstance(applicationContext).addToRequestQueue(stringRequest)
    }


}