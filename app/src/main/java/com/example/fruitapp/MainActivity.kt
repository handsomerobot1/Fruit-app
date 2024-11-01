package com.example.fruitapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitapp.adapter.FruitAdapter
import com.example.fruitapp.databinding.ActivityMainBinding
import com.example.fruitapp.model.Fruit


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fruitAdapter: FruitAdapter
    val fruit = ArrayList<Fruit>()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.fruitRv.layoutManager = LinearLayoutManager(this)


        val fruitNames = listOf("Apple", "Orange", "Banana", "Strawberry", "Mango", "Kiwi", "Dragon Fruit", "Berry", "Grape", "Jack Fruit")

        for (i in 1..100) {
            val fruitName = fruitNames[i % fruitNames.size]
            val quantity = (10..500).random()
            val price = (1..10).random().toDouble()
            val description = "$fruitName number $i"
            val imageRes = when (fruitName) {
                "Apple" -> R.drawable.apple
                "Orange" -> R.drawable.orange
                "Banana" -> R.drawable.banana
                "Strawberry" -> R.drawable.strawberry
                "Mango" -> R.drawable.mango
                "Kiwi" -> R.drawable.kiwi
                "Dragon Fruit" -> R.drawable.dragon
                "Berry" -> R.drawable.berry
                "Grape" -> R.drawable.grape
                "Jack Fruit" -> R.drawable.jackfruit
                else -> R.drawable.default_image // Fallback in case of a new fruit name
            }

            fruit.add(Fruit(fruitName, quantity, price, imageRes, description))
        }

        fruitAdapter = FruitAdapter(fruit)
        binding.fruitRv.adapter = fruitAdapter

        fruitAdapter.onClick={
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("name", it.fruitName)
            intent.putExtra("price", it.fruitPrice)
            intent.putExtra("quantity", it.fruitQnt)
            intent.putExtra("desc", it.fruitDesc)
            intent.putExtra("image", it.fruitImg)
            startActivity(intent)
        }

        binding.addBtn.setOnClickListener {
            showFruitAddDialog()
        }

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                fruit.removeAt(viewHolder.adapterPosition)
                fruitAdapter.notifyItemRemoved(viewHolder.adapterPosition)
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.fruitRv)



    }

    private fun showFruitAddDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.add_fruit_layout, null)
        val nameEt = dialogView.findViewById<EditText>(R.id.fruitNameEt)
        val priceEt = dialogView.findViewById<EditText>(R.id.fruitPriceEt)
        val qntEt = dialogView.findViewById<EditText>(R.id.fruitQntEt)
        val descEt = dialogView.findViewById<EditText>(R.id.fruitDescEt)

        AlertDialog.Builder(this)
            .setTitle("Add Fruit")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = nameEt.text.toString()
                val price = priceEt.text.toString().toDouble()
                val qnt = qntEt.text.toString().toInt()
                val desc = descEt.text.toString()
                val img = R.drawable.banana
                fruit.add(Fruit(name, qnt, price, img, desc))
                fruitAdapter.notifyItemInserted(fruit.size - 1)
            }
            .setNegativeButton("Cancel", null)
            .show()

    }


}