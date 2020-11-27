package com.staydev.seani.helper

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleySingleton private constructor(context: Context){
    private var mRequestQueue: RequestQueue

    val requestQueue: RequestQueue
    get() {
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mCtx?.applicationContext)
        }
        return mRequestQueue
    }
    init {
        mCtx = context
        mRequestQueue = requestQueue
    }
    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
    companion object {
        private var mInstance: VolleySingleton? = null
        private var mCtx: Context? = null

        @Synchronized
        fun getInstance(context: Context): VolleySingleton {
            if (mInstance == null) {
                mInstance = context?.let { VolleySingleton(it) }
            }
            return mInstance as VolleySingleton
        }
    }
}