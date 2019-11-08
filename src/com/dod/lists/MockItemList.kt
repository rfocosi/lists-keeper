package com.dod.lists

import com.google.gson.JsonElement
import java.util.logging.Logger

class MockItemList : ItemList {

    private val log = Logger.getLogger(MockItemList::javaClass.name)!!
    private val failListName = "fail"

    override fun add(listName: String, item: String): Boolean {
        log.info("($listName) ADD: $item")
        return shouldFail(listName)
    }

    override fun addBulk(listName: String, itemList: JsonElement): Boolean {
        log.info("($listName) ADD_BULK: $itemList")
        return shouldFail(listName)
    }

    override fun create(listName: String): Boolean {
        log.info("($listName) CREATED")
        return shouldFail(listName)
    }

    override fun find(listName: String, item: String): Boolean {
        log.info("($listName) FIND: $item")
        return shouldFail(listName)
    }

    override fun remove(listName: String, item: String): Boolean {
        log.info("($listName) REMOVE: $item")
        return shouldFail(listName)
    }

    private fun shouldFail(listName: String): Boolean {
        return listName != failListName
    }
}
