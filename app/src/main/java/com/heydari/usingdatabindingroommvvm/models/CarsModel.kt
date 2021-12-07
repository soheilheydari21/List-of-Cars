package com.heydari.usingdatabindingroommvvm.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "cars_table")
data class CarsModel(
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "cars_id")
    val id:Int,
    @ColumnInfo (name = "cars_name")
    var name:String,
    @ColumnInfo (name = "cars_price")
    var price:String,
    @ColumnInfo (name = "cars_engine")
    var engine:String,
    @ColumnInfo (name = "cars_picture")
    var picture:ByteArray
)
