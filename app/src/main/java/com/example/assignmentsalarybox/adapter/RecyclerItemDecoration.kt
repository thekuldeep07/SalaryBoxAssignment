package com.example.assignmentsalarybox.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.assignmentsalarybox.R

class RecyclerItemDecoration(
    var context: Context,
    var headerOffset: Int,
    var sticky: Boolean,
    var sectionCallback: SectionCallback

) : ItemDecoration() {
    var headerView: View? = null
    var tvTitle: TextView? = null

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val pos = parent.getChildAdapterPosition(view)
        if (sectionCallback.isSection(pos)) {
            outRect.top = headerOffset
        }
    }


    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (headerView == null) {
            headerView = inflateHeader(parent)
            tvTitle = headerView!!.findViewById(R.id.text_header_name)
            fixLayoutSize(headerView, parent)
        }
        var prevTitle = ""
        for (i in 0 until parent.childCount) {

            val child: View = parent.getChildAt(i)
            val childPos = parent.getChildAdapterPosition(child)
            val title = sectionCallback.getSectionHeaderName(childPos)

            tvTitle!!.text = title
            if(title!="footer"){
                if (!prevTitle.equals(
                        title,
                        ignoreCase = true
                    ) || sectionCallback.isSection(childPos)
                ) {
                    drawHeader(c, child, headerView)
                    prevTitle = title
                }

            }

        }


    }

    private fun drawHeader(c: Canvas, child: View, headerView: View?) {
        c.save()
        if (sticky) {
            c.translate(2f, Math.max(0, child.getTop() - headerView!!.getHeight()).toFloat())

        } else {
            c.translate(0f, child.getTop() - headerView!!.getHeight().toFloat())
        }
        headerView.draw(c)
        c.restore()
    }

    fun fixLayoutSize(view: View?, viewGroup: ViewGroup) {
        val widthSpec: Int =
            View.MeasureSpec.makeMeasureSpec(viewGroup.width, View.MeasureSpec.EXACTLY)
        val heightSpec: Int =
            View.MeasureSpec.makeMeasureSpec(viewGroup.height, View.MeasureSpec.UNSPECIFIED)
        val childWidth = ViewGroup.getChildMeasureSpec(
            widthSpec,
            viewGroup.paddingLeft + viewGroup.paddingRight,
            view!!.getLayoutParams().width
        )
        val childHeight = ViewGroup.getChildMeasureSpec(
            heightSpec,
            viewGroup.paddingTop + viewGroup.paddingBottom,
            view.getLayoutParams().height
        )
        view.measure(childWidth, childHeight)
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight())
    }

    fun inflateHeader(recyclerView: RecyclerView): View {
        return LayoutInflater.from(context)
            .inflate(R.layout.row_section_header, recyclerView, false)
    }

    interface SectionCallback {
        fun isSection(pos: Int): Boolean
        fun getSectionHeaderName(pos: Int): String
    }
}