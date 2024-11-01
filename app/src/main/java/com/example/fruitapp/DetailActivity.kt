package com.example.fruitapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fruitapp.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val price = intent.getDoubleExtra("price",0.0)
        val qnt = intent.getIntExtra("quantity",0)
        val img = intent.getIntExtra("image",0)
        val desc = intent.getStringExtra("desc")

        binding.apply {
            fruitName.text = name
            fruitPrice.text = "Price: $"+price.toString()
            fruitQnt.text = "Quantity: "+qnt.toString()
            fruitImg.setImageResource(img)
            fruitDesc.text = desc
        }

    }
}