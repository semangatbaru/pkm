package com.staydev.seani.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.staydev.seani.JadwalFragment
import com.staydev.seani.ProsesFragment
import com.staydev.seani.SetujuFragment

class AdapterPager(fm :FragmentManager) : FragmentPagerAdapter(fm) {
    private val pages = listOf(
        ProsesFragment(),
        JadwalFragment(),
        SetujuFragment(),
    )
    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    // judul untuk tabs
    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Proses"
            1 -> "re-Jadwal"
            else -> "Setuju"
        }
    }
}