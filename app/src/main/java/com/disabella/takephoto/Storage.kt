package com.disabella.takephoto

interface Storage {

    fun loadItems(): List<MyItem>

    fun saveItem(item: MyItem)
}