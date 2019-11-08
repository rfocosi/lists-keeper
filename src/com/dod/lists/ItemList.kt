package com.dod.lists

import com.google.gson.JsonElement

interface ItemList {
    fun add(listName: String, item: String): Boolean
    fun addBulk(listName: String, itemList: JsonElement): Boolean
    fun create(listName: String): Boolean
    fun find(listName: String, item: String): Boolean
    fun remove(listName: String, item: String): Boolean
}