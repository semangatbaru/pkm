package com.staydev.seani

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {
    private lateinit var root:View
    private lateinit var mFloat: FloatingActionButton
    private lateinit var mNotification: AppCompatImageButton


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
         root = inflater.inflate(R.layout.fragment_home, container, false)

        mFloat = root.findViewById(R.id.floating)
        mNotification = root.findViewById(R.id.icon_notification)
        mNotification.setOnClickListener{
            startActivity(Intent(context, ActivityNotification::class.java))
        }
        mFloat.setOnClickListener { view ->
            Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()
        }

        return root
    }
}