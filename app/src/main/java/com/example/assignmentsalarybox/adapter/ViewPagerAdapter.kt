package com.example.assignmentsalarybox.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.example.assignmentsalarybox.R
import com.example.assignmentsalarybox.data.ViewPagerData
import com.google.android.material.imageview.ShapeableImageView

class ViewPagerAdapter(
    val pageIndImg : ImageView,
    val hr_Img : ShapeableImageView,
    val cur_pos : TextView,
    val li : List<ViewPagerData>
    ): LoopingPagerAdapter<ViewPagerData>(li,true) {

    override fun inflateView(viewType: Int, container: ViewGroup, listPosition: Int): View {
        return LayoutInflater.from(container.context).inflate(R.layout.horizontal_recycler_item, container,false)
    }

    override fun bindView(convertView: View, listPosition: Int, viewType: Int) {
        convertView.findViewById<TextView>(R.id.hr_title).text = li[listPosition].title
        convertView.findViewById<TextView>(R.id.hr_subtitle).text = li[listPosition].subtitle
        Log.d("checkingthis", "bindView: $listPosition")
        if (listPosition == 1){
            li[listPosition-1].img?.let { hr_Img.setImageResource(it) }
            pageIndImg.setImageResource(R.drawable.first_img)
            cur_pos.text = "1/4"

        } else if (listPosition == 0) {
            li[3].img?.let { hr_Img.setImageResource(it) }
            pageIndImg.setImageResource(R.drawable.last_img)
            cur_pos.text = "4/4"
        } else {
            li[listPosition-1].img?.let { hr_Img.setImageResource(it) }
            pageIndImg.setImageResource(R.drawable.middle_img)
            cur_pos.text = "$listPosition/4"
        }
    }

}