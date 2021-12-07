package com.heydari.usingdatabindingroommvvm.models

class CarsRepository(private val dao: CarsDao) {
    val cars = dao.getAllCars()

    fun insert(model: CarsModel){
        dao.insertCar(model)
    }

    fun update(model: CarsModel){
        dao.updateCar(model)
    }

    fun delete(model: CarsModel){
        dao.deleteCar(model)
    }

    fun deleteAll(){
        dao.deleteAll()
    }
}