package com.dod.lists

import com.google.gson.JsonElement
import com.dod.repository.MongoRepository
import com.mongodb.MongoBulkWriteException
import com.mongodb.MongoCommandException
import com.mongodb.MongoWriteException
import com.mongodb.client.model.InsertManyOptions
import io.ktor.application.ApplicationEnvironment
import io.ktor.util.KtorExperimentalAPI
import org.litote.kmongo.eq
import org.litote.kmongo.findOne

@KtorExperimentalAPI
class MongoItemList(env: ApplicationEnvironment) : ItemList {

    private val repo = MongoRepository(env)

    override fun create(listName: String): Boolean {
        try {
            repo.create(listName)
        } catch (e: MongoCommandException) {
            return false
        }
        return true
    }

    override fun add(listName: String, item: String): Boolean {
        try {
            repo.list(listName).insertOne(Item(item.toLowerCase()))
        } catch (e: MongoWriteException) {
            return false
        }
        return true
    }

    override fun addBulk(
        listName: String,
        itemList: JsonElement
    ): Boolean {
        try {
            val nItemList = itemList.asJsonArray.map { Item(it.asString.toLowerCase()) }
            repo.list(listName).insertMany(nItemList, InsertManyOptions().ordered(false))
        } catch (e: MongoBulkWriteException) {
            // Ignore dup errors
        }
        return true
    }

    override fun find(listName: String, item: String): Boolean {
        return repo.list(listName).findOne(Item::name eq item.toLowerCase()) != null
    }

    override fun remove(listName: String, item: String): Boolean {
        try {
            repo.list(listName).deleteOne(Item::name eq item.toLowerCase())
        } catch (e: MongoWriteException) {
            return false
        }
        return true
    }
}
