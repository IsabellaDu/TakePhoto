package com.disabella.takephoto

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder

object DataStorage {

    private data class Items(val items: List<MyItem>)

    private val gson = GsonBuilder().create()
    private val sharedPref: SharedPreferences by lazy {
        App.appContext.getSharedPreferences("main", Context.MODE_PRIVATE)
    }

    fun loadItems(): List<MyItem> {
        val data = sharedPref.getString("data", null)
        val items: Items? = gson.fromJson(data, Items::class.java)
        return items?.items ?: emptyList()
    }

    fun saveItem(item: MyItem) {
        val newItems = loadItems() + listOf(item)
        val json = gson.toJson(Items(newItems))
        sharedPref.edit().putString("data", json).apply()
    }

}