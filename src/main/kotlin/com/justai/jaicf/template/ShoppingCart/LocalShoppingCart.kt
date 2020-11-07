package com.justai.jaicf.template.ShoppingCart

import kotlin.collections.HashMap

object LocalShoppingCart : ShoppingCart {
    private val shoppingCart: MutableMap<String, MutableList<String>> = HashMap<String, MutableList<String>>()

    override fun add(unit: String, item: String) {
        shoppingCart.putIfAbsent(unit, mutableListOf())
        shoppingCart[unit]?.add(item)
    }

    override fun remove(unit: String, item: String) {
        if(shoppingCart.containsKey(unit)){
            shoppingCart[unit]?.remove(item)
        }
    }

    override fun getAll(unit: String): List<String>? {
        return shoppingCart[unit]?.toList()
    }
}