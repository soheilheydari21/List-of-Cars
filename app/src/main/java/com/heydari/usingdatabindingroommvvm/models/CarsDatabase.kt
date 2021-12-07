package com.heydari.usingdatabindingroommvvm.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [CarsModel::class], version = 1)
abstract class CarsDatabase : RoomDatabase() {
    abstract val carsDao:CarsDao

    companion object{
        private var INSTANCE: CarsDatabase? = null

        fun getInstance(context: Context) : CarsDatabase {
            synchronized(this){
                var instance:CarsDatabase? = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CarsDatabase::class.java,
                        "cars_database"
                    ).allowMainThreadQueries().build()
                }
                return instance
            }
        }

    }
}