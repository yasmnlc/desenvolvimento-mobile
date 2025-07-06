package com.example.cruditemapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cruditemapp.model.Item
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class ItemViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private var listenerRegistration: ListenerRegistration? = null

    var items = mutableStateOf<List<Item>>(listOf())
        private set

    init {
        listenerToItems()
    }

    private fun listenerToItems() {
        listenerRegistration = db.collection("items")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Log.e("ItemViewModel", "Error listening for items: ${exception.message}", exception)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val fetchedItems = snapshot.documents.mapNotNull { document ->
                        document.toObject(Item::class.java)?.copy(id = document.id)
                    }
                    items.value = fetchedItems
                }
            }
    }

    fun addItem(item: Item) {
        db.collection("items").add(item)
            .addOnSuccessListener { documentReference ->
                Log.d("ItemViewModel", "Item added successfully with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.e("ItemViewModel", "Error adding item: ${e.message}", e)
            }
    }

    fun deleteItem(itemId: String) {
        db.collection("items").document(itemId).delete()
            .addOnSuccessListener {
                Log.d("ItemViewModel", "Item deleted successfully: $itemId")
            }
            .addOnFailureListener { e ->
                Log.e("ItemViewModel", "Error deleting item: ${e.message}", e)
            }
    }

    fun updateItem(item: Item) {
        db.collection("items")
            .document(item.id)
            .set(item)
            .addOnSuccessListener {
                Log.d("ItemViewModel", "Item updated successfully: ${item.id}")
            }
            .addOnFailureListener { e ->
                Log.e("ItemViewModel", "Error updating item: ${e.message}", e)
            }
    }

    override fun onCleared() {
        super.onCleared()
        listenerRegistration?.remove()
    }
}