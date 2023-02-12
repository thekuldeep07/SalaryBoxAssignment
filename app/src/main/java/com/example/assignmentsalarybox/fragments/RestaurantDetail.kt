package com.example.assignmentsalarybox.fragments

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentsalarybox.R
import com.example.assignmentsalarybox.adapter.RecyclerItemDecoration
import com.example.assignmentsalarybox.adapter.RecyclerViewAdapter
import com.example.assignmentsalarybox.data.Store
import com.example.assignmentsalarybox.mvvm.ViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantDetail : Fragment() {
    val viewModel: ViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_restaurant_detail,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar: Toolbar = view.findViewById(R.id.tool_bar)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        val collapsingToolbar: CollapsingToolbarLayout = view.findViewById(R.id.collapse_layout)
        val appBar: AppBarLayout = view.findViewById(R.id.appBarLayout)


        val restaurantName: TextView = view.findViewById(R.id.restaurant_name)
        val restaurantSubTitle: TextView = view.findViewById(R.id.restaurant_description)
        val restaurantAddress: TextView = view.findViewById(R.id.restaurant_address)
        val restaurantTime: TextView = view.findViewById(R.id.delivery_time)
        val restaurantDistance: TextView = view.findViewById(R.id.distance)
        val rating: TextView = view.findViewById(R.id.rating)
        val reviewCount: TextView = view.findViewById(R.id.review_count)
        val vegButton: LinearLayout = view.findViewById(R.id.veg_button)
        val nonVegButton: LinearLayout = view.findViewById(R.id.non_veg_button)
        val optionBtn : ImageView = view.findViewById(R.id.option_btn)
        val vegCross :ImageView = view.findViewById(R.id.veg_cross)
        val nonVegCross :ImageView = view.findViewById(R.id.non_veg_cross)
        var vegFlag = false
        var nonVegFlag = false

        val adapter = RecyclerViewAdapter(
            this.requireContext()
        )





        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerView.adapter = adapter

        toolbar.title = ""
        collapsingToolbar.isTitleEnabled = false
        var recyclerItemDecoration: RecyclerItemDecoration? = null
        var storeObj: Store? = null
        viewModel.getData()
        viewModel.allData.observe(viewLifecycleOwner) { store ->
            if (recyclerItemDecoration != null) {
                recyclerView.removeItemDecoration(recyclerItemDecoration!!)
            }
            recyclerItemDecoration = RecyclerItemDecoration(
                this.requireContext(),
                resources.getDimensionPixelSize(R.dimen.header_height),
                true,
                getSectionCallback(store)
            )

            storeObj = store
            restaurantName.text = store.name
            restaurantSubTitle.text = store.subtitle
            restaurantAddress.text = store.address
            restaurantTime.text = "${store.deliveryTime}-${(store.deliveryTime + 5)} min"
            restaurantDistance.text = "${store.distance} km away"
            rating.text = store.rating.toString()
            reviewCount.text = store.reviewCount.toString()
            adapter.changeData(store)
            recyclerView.addItemDecoration(
                recyclerItemDecoration!!
            )
        }

        optionBtn.setOnClickListener{
            showBottomSheetDialog(storeObj)
        }

        vegButton.setOnClickListener {
            vegFlag = !vegFlag
            if (vegFlag) {
                viewModel.getFoodsByType("veg")
                vegButton.setBackgroundResource(R.drawable.card_view_border)
                vegCross.visibility = View.VISIBLE

            }else {
                vegButton.setBackgroundResource(R.drawable.custom_button_background)
                viewModel.getData()
                vegCross.visibility = View.GONE
            }

        }

        nonVegButton.setOnClickListener {
            nonVegFlag = !nonVegFlag
            if (nonVegFlag) {
                nonVegButton.setBackgroundResource(R.drawable.card_view_border)
                viewModel.getFoodsByType("non-veg")
                nonVegCross.visibility = View.VISIBLE
            } else {
                nonVegButton.setBackgroundResource(R.drawable.custom_button_background)
                viewModel.getData()
                nonVegCross.visibility = View.GONE

            }
        }


        appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                Log.d("sa", verticalOffset.toString())
                if (verticalOffset <= -329)
                    view.findViewById<LinearLayout>(R.id.vegNonLinearlayout)
                        .setBackgroundResource(R.color.white)
                else {
                    view.findViewById<LinearLayout>(R.id.vegNonLinearlayout)
                        .setBackgroundResource(R.color.toolbarBackGround)
                }
                if (scrollRange == -1) {
                    toolbar.title = ""
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (verticalOffset <= -108) {
                    toolbar.title = storeObj?.name
                    toolbar.setBackgroundResource(R.color.white)

                    isShow = true
                } else if (isShow) {
                    toolbar.title = ""
                    toolbar.setBackgroundResource(R.color.toolbarBackGround)

                    isShow = false
                }
            }
        })


    }

    private fun showBottomSheetDialog(storeObj: Store?) {
        val bottomSheetDialog:BottomSheetDialog =  BottomSheetDialog(this.requireContext(),R.style.TransparentDialog)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog)
        bottomSheetDialog.findViewById<TextView>(R.id.bottom_sheet_text_view)?.text = storeObj!!.name
        bottomSheetDialog.show()
        val close_btn =bottomSheetDialog.findViewById<ImageView>(R.id.bottom_close_btn)
        close_btn?.setOnClickListener{
            bottomSheetDialog.dismiss()
        }

    }


    private fun getSectionCallback(dataList: Store): RecyclerItemDecoration.SectionCallback {
        return object : RecyclerItemDecoration.SectionCallback {
            override fun isSection(pos: Int): Boolean {
                return if (pos != dataList.foods.size)
                    pos == 0 || dataList.foods[pos].tag !== dataList.foods[pos - 1].tag
                else {
                    false
                }
            }

            override fun getSectionHeaderName(pos: Int): String {
                return if (pos != dataList.foods.size) {
                    val dataMap = dataList.foods[pos]
                    dataMap.tag
                } else
                    "footer"

            }

        }
    }

}