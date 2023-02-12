package com.example.assignmentsalarybox.data

data class Store(
    val id: String = "",
    val name: String = "",
    val subtitle: String = "",
    val rating: Double = 0.0,
    val distance: Int = 0,
    val reviewCount: Int = 0,
    val address: String = "",
    val deliveryTime: Int = 0,
    var foods: ArrayList<Food> = arrayListOf()
)
