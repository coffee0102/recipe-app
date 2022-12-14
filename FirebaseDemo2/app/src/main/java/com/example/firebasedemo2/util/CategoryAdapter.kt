package com.example.firebasedemo2.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasedemo2.data.Category
import com.example.firebasedemo2.databinding.ItemCategoryBinding

class CategoryAdapter (
    val fn: (ViewHolder, Category) -> Unit = { _, _ -> }
) : ListAdapter<Category, CategoryAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(a: Category, b: Category)    = a.id == b.id
        override fun areContentsTheSame(a: Category, b: Category) = a == b
    }

    class ViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = getItem(position)

        holder.binding.txtId.text    = category.id
        holder.binding.txtName.text  = category.name

        holder.binding.txtCount.text = "${category.count} Fruit(s)"

        fn(holder, category)
    }

}