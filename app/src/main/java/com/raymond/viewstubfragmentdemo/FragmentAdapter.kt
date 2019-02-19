package com.raymond.viewstubfragmentdemo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class FragmentAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val titles = arrayOf("Title 1", "Title 2", "Title 3")

    override fun getItem(position: Int): Fragment {
        val f = HeavyFragment()
        val bundle = Bundle()
        bundle.putString("title", titles[position])
        f.arguments = bundle
        return f
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}