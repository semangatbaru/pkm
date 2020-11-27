package com.staydev.seani

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.squareup.picasso.Picasso

class RegisterActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mEmail : EditText
    private lateinit var mNama : EditText
    private lateinit var mAlamat : EditText
    private lateinit var mNoHp : EditText
    private lateinit var mFoto : ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mEmail = findViewById(R.id.emailRegister)
        mEmail.isEnabled = false;
        mNama = findViewById(R.id.namaRegister)
        mAlamat = findViewById(R.id.alamatRegister)
        mNoHp = findViewById(R.id.notlpRegister)
        mFoto = findViewById(R.id.imageRegister)

        getData()


    }


    fun  getData(){
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            val personName = acct.displayName
            val personEmail = acct.email
            val personId = acct.id
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
}