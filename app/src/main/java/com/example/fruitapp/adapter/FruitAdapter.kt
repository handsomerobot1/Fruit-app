package com.example.fruitapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitapp.databinding.FruitItemBinding
import com.example.fruitapp.model.Fruit


class FruitAdapter(private val fruitList:ArrayList<Fruit>):RecyclerView.Adapter<FruitAdapter.MyViewHolder>() {

    var onClick : ((Fruit)->Unit) ?= null

    class MyViewHolder(val binding: FruitItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = FruitItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return fruitList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.binding.apply {
            fruitName.text = fruitList[position].fruitName
            fruitQnt.text = "Quantity: " + fruitList[position].fruitQnt.toString()
            fruitPrice.text = "Price: $" + fruitList[position].fruitPrice.toString()
            fruitImg.setImageResource(fruitList[position].fruitImg)
        }

        holder.itemView.setOnClickListener {
            onClick?.invoke(fruitList[position])
        }

        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Delete Fruit Item")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Yes") { _, _ ->
                    fruitList.removeAt(position)
                    notifyItemRemoved(position)
                }
                .setNegativeButton("No", null)
                .show()
            true
        }


    }


}