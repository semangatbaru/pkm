package com.staydev.seani

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {
    private lateinit var root:View
    private lateinit var mFloat: FloatingActionButton


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
         root = inflater.inflate(R.layout.fragment_home, container, false)

        mFloat = root.findViewById(R.id.floating)
        mFloat.setOnClickListener { view ->
            Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()
        }

        return root
    }
}