package com.example.assignmentsalarybox.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.assignmentsalarybox.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout


class RestaurantDetail : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_restaurant_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar:Toolbar = view.findViewById(R.id.tool_bar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        val collapsingToolbar:CollapsingToolbarLayout = view.findViewById(R.id.collapse_layout)

        val appBar : AppBarLayout = view.findViewById(R.id.appBarLayout)
        toolbar.title = ""
        collapsingToolbar.isTitleEnabled = false
        appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener{
            var isShow = false
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1){
                    scrollRange = appBarLayout.totalScrollRange
                }
                if(scrollRange + verticalOffset ==0 ){
                    toolbar.findViewById<TextView>(R.id.toolbar_title).text="kuldeep"
                    isShow = true
                }
                else if(isShow){
                    toolbar.findViewById<TextView>(R.id.toolbar_title).text=""
                    isShow = false
                }
            }
        })


    }
}