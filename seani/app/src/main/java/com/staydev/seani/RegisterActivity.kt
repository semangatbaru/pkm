package com.staydev.seani

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.squareup.picasso.Picasso
import com.staydev.seani.helper.SharedPrefManager
import com.staydev.seani.helper.Urls
import com.staydev.seani.helper.VolleySingleton
import com.staydev.seani.model.Mlogin
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream

class RegisterActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mEmail : EditText
    private lateinit var mNama : EditText
    private lateinit var mAlamat : EditText
    private lateinit var mNoHp : EditText
    private lateinit var mFoto : ImageView

    private lateinit var mRegister : ImageButton
    private lateinit var mBatal : ImageButton


    private lateinit var bitmap: Bitmap
    private  var encode:String=""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mEmail = findViewById(R.id.emailRegister)
        mEmail.isEnabled = false;
        mNama = findViewById(R.id.namaRegister)
        mAlamat = findViewById(R.id.alamatRegister)
        mNoHp = findViewById(R.id.notlpRegister)
        mFoto = findViewById(R.id.imageRegister)

        mRegister = findViewById(R.id.btnRegister)
        mBatal = findViewById(R.id.btnBatal)

        mRegister.setOnClickListener {
            regis()
        }

        getData()



    }


    private  fun  getData(){
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            val personName = acct.displayName
            val personEmail = acct.email

            val personPhoto = acct.photoUrl
            Log.d("info", personEmail!!)
            Log.d("info", personName!!)

            Toast.makeText(this, personEmail, Toast.LENGTH_SHORT).show()

//
            mNama.setText(personName)
            mEmail.setText(personEmail)
            Picasso.get().load(personPhoto).into(mFoto)
//            Glide.with(this).load(personPhoto.toString()).into(imageView)
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        }

    }
    private fun regis() {
        //first getting the values

        base()
        val nama = mNama.text.toString()
        val email = mEmail.text.toString()
        val alamat = mAlamat.text.toString()
        val no_telp = mNoHp.text.toString()
        val foto = "data:image/jpeg;base64,"+encode

        //validating inputs
        if (TextUtils.isEmpty(nama)) {
            mNama.error = "Masukkan Nama Anda"
            mNama.requestFocus()
            return
        }

        if (TextUtils.isEmpty(email)) {
            mEmail.error = "Masukkan Email Anda"
            mEmail.requestFocus()
            return
        }
        if (TextUtils.isEmpty(alamat)) {
            mAlamat.error = "Masukkan Alamat Anda"
            mAlamat.requestFocus()
            return
        }
        if (TextUtils.isEmpty(no_telp)) {
            mNoHp.error = "Masukkan Nomer Telfon"
            mNoHp.requestFocus()
            return
        }
        //if everything is fine

        val stringRequest = object : StringRequest(
            Method.POST, Urls.URL_Register,
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
                            userJson.getString("email"),
                            userJson.getString("token")
                        )
                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(applicationContext).mUserLogin(mUser)
                        //starting the MainActivity

                        finish()
                        startActivity(Intent(applicationContext, Kritik::class.java))
                        Toast.makeText(applicationContext,obj.getString("message"),Toast.LENGTH_SHORT).show()

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
                val params = HashMap<String, String>()
                params["email"] = email
                params["nama"] = nama
                params["alamat"] = alamat
                params["no_telp"] = no_telp
                params["foto"] = foto
                Log.e("param", params.toString())
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

    private fun base(){
        val drawable: BitmapDrawable = mFoto.drawable as BitmapDrawable
        bitmap = drawable.bitmap
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        encode = Base64.encodeToString(byteArray, Base64.DEFAULT);
        Log.e("en", encode)
    }
}