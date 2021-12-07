package com.heydari.usingdatabindingroommvvm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.heydari.usingdatabindingroommvvm.view.MainActivity
import com.heydari.usingdatabindingroommvvm.models.CarsRepository
import java.lang.IllegalArgumentException


class CarViewModelFactory(private val repository: CarsRepository, private val main: MainActivity) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarViewModel::class.java)){
            return CarViewModel(repository, main) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}