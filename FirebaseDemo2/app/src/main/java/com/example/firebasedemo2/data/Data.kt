package com.example.firebasedemo2.data

import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Category(
    @DocumentId
    var id: String = "",
    var name: String = "",
) {

    var count: Int = 0
    override fun toString() = name
}

data class Fruit(
    @DocumentId
    var id: String = "",
    var name: String = "",
    var photo: Blob = Blob.fromBytes(ByteArray(0)),
    var categoryId: String = "",
    var description: String = "",
) {

    var category: Category = Category()
}

// -------------------------------------------------------------------------------------------------

val CATEGORIES = Firebase.firestore.collection("categories")
val FRUITS = Firebase.firestore.collection("fruits")

// -------------------------------------------------------------------------------------------------

//fun RESTORE_DATA() {
//    val categories = listOf(
//        Category("F", "Food"),
//        Category("D", "Drink"),
//    )
//
//    val fruits = listOf(
//        Fruit("F01", "Banana",  "F"),
//        Fruit("F02", "Papaya",  "F"),
//        Fruit("F03", "Coconut",  "F"),
//        Fruit("F04", "Apple",  "F"),
//        Fruit("F05", "Orange", "F"),
//        Fruit("D06", "Watermelon",  "D"),
//        Fruit("D07", "Strawberry",  "D"),
//        Fruit("D08", "Honeydew",  "D"),
//        Fruit("D09", "Durian", 0.00, "D"),
//        Fruit("D10", "Grape", 0.00, "D"),
//    )
//
//    for (c in categories) {
//        CATEGORIES.document(c.id).set(c)
//    }
//
//    for (f in fruits) {
//        FRUITS.document(f.id).set(f)
//    }
//}