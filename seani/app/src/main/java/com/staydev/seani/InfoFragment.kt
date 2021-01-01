package com.staydev.seani

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.squareup.picasso.Picasso
import com.staydev.seani.helper.SharedPrefManager

class InfoFragment:Fragment(){

    private lateinit var root:View
    private lateinit var mFoto:ImageView
    private lateinit var mNama:TextView
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mIconSignOut: ImageButton
    private lateinit var mTentang:CardView
    private lateinit var mSyarat:CardView
    private lateinit var mPrivacy:CardView
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_info, container, false)
        mFoto = root.findViewById(R.id.fotoUser)
        mNama = root.findViewById(R.id.namaUser)

        mTentang = root.findViewById(R.id.tentangkami)
        mSyarat = root.findViewById(R.id.SK)
        mPrivacy = root.findViewById(R.id.KP)

        mTentang.setOnClickListener {
            startActivity(Intent(context, AboutusActivity::class.java))
        }
        mSyarat.setOnClickListener {
            startActivity(Intent(context, SyaratActivity::class.java))
        }
        mPrivacy.setOnClickListener {
            startActivity(Intent(context, PrivaciActivity::class.java))
        }



        mIconSignOut = root.findViewById(R.id.iconSignOut)
        mIconSignOut.setOnClickListener {
            logout()
        }

        getData()
        return root
    }
    private  fun  getData(){

        val acct = GoogleSignIn.getLastSignedInAccount(context)
        if (acct != null) {
            val personName = acct.displayName
            val personEmail = acct.email

            val personPhoto = acct.photoUrl

//


            mNama.text = personEmail
            Picasso.get().load(personPhoto).into(mFoto)
//            Glide.with(this).load(personPhoto.toString()).into(imageView)
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
            mGoogleSignInClient = context?.let { GoogleSignIn.getClient(it, gso) }!!


        }

    }

    private fun logout(){
        mGoogleSignInClient.signOut().addOnCompleteListener {
            activity?.let { SharedPrefManager.getInstance(it).logout() }
        }
    }
}