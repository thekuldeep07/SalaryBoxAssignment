package com.example.assignmentsalarybox.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmentsalarybox.data.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {
    val allData: MutableLiveData<Store> = MutableLiveData()

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            allData.postValue(repository.getData())
        }
    }

    fun getFoodsByType(type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            allData.postValue(repository.getDataByType(type))
        }
    }
}