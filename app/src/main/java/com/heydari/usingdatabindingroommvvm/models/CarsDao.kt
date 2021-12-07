package com.heydari.usingdatabindingroommvvm.models

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface CarsDao {
    @Insert
    fun insertCar(model: CarsModel)

    @Update
    fun updateCar(model: CarsModel)

    @Delete
    fun deleteCar(model: CarsModel)

    @Query("DELETE FROM cars_table")
    fun deleteAll()

    @Query("SELECT * FROM cars_table")
    fun getAllCars() : LiveData<List<CarsModel>>
}