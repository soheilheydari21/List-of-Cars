package com.heydari.usingdatabindingroommvvm.viewModel

import android.annotation.SuppressLint
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heydari.usingdatabindingroommvvm.view.MainActivity
import com.heydari.usingdatabindingroommvvm.models.CarsModel
import com.heydari.usingdatabindingroommvvm.models.CarsRepository
import kotlinx.coroutines.launch


class CarViewModel @SuppressLint("StaticFieldLeak") constructor(
    private val repository: CarsRepository,
    private val main: MainActivity
    ) : ViewModel(), Observable {

    val cars = repository.cars
    private var isUpdateOrDelete = false
    private lateinit var carToUpdateOrDelete: CarsModel

    @Bindable
    var inputName = MutableLiveData<String?>()
    @Bindable
    var inputPrice = MutableLiveData<String?>()
    @Bindable
    var inputEngine = MutableLiveData<String?>()

    @Bindable
    var buttonSave = MutableLiveData<String>()
    @Bindable
    var buttonDelete = MutableLiveData<String>()

    init {
        buttonSave.value = "Save"
        buttonDelete.value = "Delete all cars"
    }

    fun initUpdateAndDelete(model: CarsModel){
        inputName.value = model.name
        inputPrice.value = model.price
        inputEngine.value = model.engine
        main.setImageAdd(model.picture)

        buttonSave.value = "Update"
        buttonDelete.value = "Delete"
        isUpdateOrDelete = true
        carToUpdateOrDelete = model
    }

    fun saveOrUpdate(){
        if (isUpdateOrDelete) {
            carToUpdateOrDelete.name = inputName.value!!
            carToUpdateOrDelete.price = inputPrice.value!!
            carToUpdateOrDelete.engine = inputEngine.value!!
            carToUpdateOrDelete.picture =  main.imageViewToByte()
            update(carToUpdateOrDelete)
            resetFields()
        }
        else {
            val name:String = inputName.value!!
            val price:String = inputPrice.value!!
            val engine:String = inputEngine.value!!
            insert(CarsModel(0, name, price, engine, main.imageViewToByte()))
            inputName.value = null
            inputPrice.value = null
            inputEngine.value = null
            main.defaultImage()
        }
    }

    fun choseImage(){
        main.chose()
    }

    private fun update(model: CarsModel){
        viewModelScope.launch {
            repository.update(model)
        }
    }

    fun deleteOrAllDelete(){
        if (isUpdateOrDelete)
            delete(carToUpdateOrDelete)
        else
            deleteAll()
    }

    private fun delete(model: CarsModel){
        viewModelScope.launch {
            repository.delete(model)
            resetFields()
        }
    }

    private fun resetFields() {
        inputName.value = null
        inputPrice.value = null
        inputEngine.value = null
        main.defaultImage()
        isUpdateOrDelete = false
        buttonSave.value = "Save"
        buttonDelete.value = "Delete all cars"
    }

    private fun insert(model: CarsModel){
        viewModelScope.launch {
            repository.insert(model)
        }
    }

    private fun deleteAll(){
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

}