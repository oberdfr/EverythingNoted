package com.example.shoppinglist.utils

fun hasDecimalNumber(input: String): Boolean {
    val regex = Regex("[.,]\\d+")
    return regex.find(input) != null
}