package com.example.firebasedemo2.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FruitViewModel : ViewModel() {

    private var fruits = listOf<Fruit>() // Original data
    private val result = MutableLiveData<List<Fruit>>() // Search + filter + sort result
    val FRUITS = Firebase.firestore.collection("fruits")

    private var name = ""       // Search
    private var categoryId = "" // Filter
    private var field = ""      // Sort
    private var reverse = false // Sort

    init {
        viewModelScope.launch {
            val categories = CATEGORIES.get().await().toObjects<Category>()

            FRUITS.addSnapshotListener { value, _ ->
                if (value == null) return@addSnapshotListener

                fruits = value.toObjects<Fruit>()
                for (f in fruits){
                    val category = categories.find { c -> c.id == f.categoryId }!!
                    f.category = category
                }

                updateResult()
            }
        }

    }

    private fun updateResult() {
        var list = fruits

        list = list.filter { f ->
            f.name.contains(name, true)&&
            (categoryId == "" || categoryId == f.categoryId)
        }
        list = when (field){
            "id" -> list.sortedBy { f -> f.id }
            "name" -> list.sortedBy { f -> f.name }
            else -> list
        }

        if (reverse) list = list.reversed()
        result.value = list
    }

    // ---------------------------------------------------------------------------------------------

    // Dummy function
    fun init() = Unit

    fun getResult() = result // Live data

    suspend fun getCategories(): List<Category> {
        return CATEGORIES.get().await().toObjects<Category>()
    }

    fun search(name: String) {
        this.name = name
        updateResult()
    }

    fun filter(categoryId: String) {
        this.categoryId = categoryId
        updateResult()
    }

    fun sort(field: String): Boolean {
        reverse = if (this.field == field)!reverse else false
        this.field = field
        updateResult()
        return reverse
    }

    // ---------------------------------------------------------------------------------------------

    fun get(id: String): Fruit? {
        return fruits.find { it.id == id }
    }
    fun set(f: Fruit) {
        // TODO
        FRUITS.document(f.id).set(f)
    }

    //----------------------------------------------------------------------------------------------

    private fun idExists(id: String)  // TODO
            =  result.value?.any{it.id == id} ?: false


    fun validate(f: Fruit, insert: Boolean = true): String {
        val regexId = Regex("""^[A-Z]\d{2}$""")
        var e = ""

        if (insert) {
            e += if (f.id == "") "- Id is required.\n"
            else if (!f.id.matches(regexId)) "- Id format is invalid (format: X999).\n"
            else if (idExists(f.id)) "- Id is duplicated.\n"
            else ""
        }

        e += if (f.name == "") "- Name is required.\n"
        else if (f.name.length < 3) "- Name is too short (at least 3 letters).\n"
        else ""

        e += if (f.categoryId == "") "- The categoryId is required.\n"
        else if (f.categoryId != "F" && f.categoryId != "D") "- The categoryId is 'F' for Food, 'D' for Drink.\n"
        else ""

        e += if (f.photo.toBytes().isEmpty()) "- Photo is required.\n"
        else ""

        return e
    }
    // ---------------------------------------------------------------------------------------------

}