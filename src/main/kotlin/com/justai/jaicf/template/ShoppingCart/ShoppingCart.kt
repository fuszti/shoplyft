package com.justai.jaicf.template.ShoppingCart

interface ShoppingCart {
    fun add(unit: String, item: String)
    fun remove(unit: String, item: String)
    fun getAll(unit: String): List<String>?
}