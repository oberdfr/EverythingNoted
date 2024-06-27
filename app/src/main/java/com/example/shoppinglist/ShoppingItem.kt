package com.example.shoppinglist

data class ShoppingItem(val id: Int,
                        val name: String,
                        val quantity: Int,
                        val description: String = "",
                        val isEditing: Boolean = false
) {

}