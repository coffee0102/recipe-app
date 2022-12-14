package com.example.firebasedemo2.data

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await

class CategoryViewModel : ViewModel() {

    // Dummy function
    fun init() = Unit

    suspend fun getAll(): List<Category> {
        val categories = CATEGORIES.get().await().toObjects<Category>()

        for (c in categories){
            c.count = FRUITS.whereEqualTo("categoryId", c.id).get().await().size()
        }
        return categories
    }

    suspend fun get(id: String): Category? {
        return CATEGORIES.document(id).get().await().toObject()
    }

    suspend fun getFruits(id: String): List<Fruit> {
        val fruits = FRUITS.whereEqualTo("categoryId", id).get().await().toObjects<Fruit>()
        val category = get(id)

        for (f in fruits){
            f.category = category!!
        }


        return fruits
    }

}