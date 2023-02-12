package com.example.assignmentsalarybox.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.assignmentsalarybox.data.Food
import com.example.assignmentsalarybox.data.Store
import com.example.assignmentsalarybox.data.data
import javax.inject.Inject

class AppRepository @Inject constructor() {

    fun getData(): Store {
        return data
    }

    fun getDataByType(type: String): Store {
        val tempFood = arrayListOf<Food>()
        data.foods.forEach {
            if (it.type == type) {
                tempFood.add(it)
            }
        }
        val tempdata = data.copy()
        tempdata.foods = tempFood
        return tempdata
    }
}