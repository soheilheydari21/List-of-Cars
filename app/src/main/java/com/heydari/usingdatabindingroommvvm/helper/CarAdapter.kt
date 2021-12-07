package com.heydari.usingdatabindingroommvvm.helper

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.heydari.usingdatabindingroommvvm.R
import com.heydari.usingdatabindingroommvvm.databinding.CarItemBinding
import com.heydari.usingdatabindingroommvvm.models.CarsModel

class CarAdapter(private val cars: List<CarsModel>, private val clickListener: (CarsModel)-> Unit) : RecyclerView.Adapter<CarViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding:CarItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.car_item, parent, false)
        return  CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(cars[position], clickListener)
    }

    override fun getItemCount(): Int {
        return cars.size
    }
}

class CarViewHolder(private val binding: CarItemBinding) : RecyclerView.ViewHolder(binding.root){
    @SuppressLint("SetTextI18n")
    fun bind(model: CarsModel, clickListener: (CarsModel)-> Unit){
        binding.textViewName.text = "Name: ${model.name}"
        binding.textViewPrice.text = "Price: $ ${model.price}"
        binding.textViewEngine.text = "Engine: ${model.engine} HP"
        val carImage = model.picture
        val bitmap = BitmapFactory.decodeByteArray(carImage, 0, carImage.count())
        binding.imageViewCar.setImageBitmap(bitmap)
        binding.layoutItem.setOnClickListener {
            clickListener(model)
        }
    }
}