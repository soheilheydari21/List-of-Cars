package com.heydari.usingdatabindingroommvvm.view

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.heydari.usingdatabindingroommvvm.R
import com.heydari.usingdatabindingroommvvm.databinding.ActivityMainBinding
import com.heydari.usingdatabindingroommvvm.helper.CarAdapter
import com.heydari.usingdatabindingroommvvm.models.CarsDatabase
import com.heydari.usingdatabindingroommvvm.models.CarsModel
import com.heydari.usingdatabindingroommvvm.models.CarsRepository
import com.heydari.usingdatabindingroommvvm.viewModel.CarViewModel
import com.heydari.usingdatabindingroommvvm.viewModel.CarViewModelFactory
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var carViewModel: CarViewModel
    companion object {
        const val IMAGE_PIC = 1000
        const val PERMISSION_CODE = 2000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = CarsDatabase.getInstance(application).carsDao
        val repository = CarsRepository(dao)
        val factory = CarViewModelFactory(repository, this)

        title = "List Price Car"

        carViewModel = ViewModelProvider(this, factory)[CarViewModel::class.java]
        binding.carsViewModel = carViewModel
        binding.lifecycleOwner = this

        initRecyclerView()
    }

    private fun initRecyclerView(){
        binding.recyclerViewCars.layoutManager = LinearLayoutManager(this)
        loadData()
    }

    private fun loadData() {
        carViewModel.cars.observe(this) {
            binding.recyclerViewCars.adapter = CarAdapter(it) { carItem: CarsModel ->
                carItemClick(carItem)
            }
        }
    }

    private fun carItemClick(model: CarsModel){
        carViewModel.initUpdateAndDelete(model)
    }

    fun chose(){
        if ((checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)) {
            val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permission, PERMISSION_CODE)
        }
        else
            picImageFromGallery()
    }

    private fun picImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PIC)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    picImageFromGallery()
                else
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PIC){
            val imageAdd = findViewById<ImageView>(R.id.imageViewAdd)
            imageAdd.setImageURI(data?.data)
        }
    }

    fun imageViewToByte() : ByteArray {
        val image = findViewById<ImageView>(R.id.imageViewAdd)
        val bitmap = (image.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    fun defaultImage(){
        val image = findViewById<ImageView>(R.id.imageViewAdd)
        image.setImageResource(R.drawable.ice_image)
    }

    fun setImageAdd(model: ByteArray){
        val image = findViewById<ImageView>(R.id.imageViewAdd)
        Glide.with(this).load(model).into(image)
    }

}
