package com.example.firebasedemo2.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasedemo2.data.Fruit
import com.example.firebasedemo2.databinding.ItemFruitBinding
import java.text.DecimalFormat

class FruitAdapter (
    val fn: (ViewHolder, Fruit) -> Unit = { _, _ -> }
) : ListAdapter<Fruit, FruitAdapter.ViewHolder>(DiffCallback) {

    private val formatter = DecimalFormat("0.00")

    companion object DiffCallback : DiffUtil.ItemCallback<Fruit>() {
        override fun areItemsTheSame(a: Fruit, b: Fruit)    = a.id == b.id
        override fun areContentsTheSame(a: Fruit, b: Fruit) = a == b
    }

    class ViewHolder(val binding: ItemFruitBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFruitBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = getItem(position)

        holder.binding.txtId.text    = fruit.id
        holder.binding.txtName.text  = fruit.name
        holder.binding.txtCategory.text = fruit.category.name

        fn(holder, fruit)
    }

}