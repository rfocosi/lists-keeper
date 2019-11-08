package com.dod.repository

import com.dod.lists.Item
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.*
import io.ktor.application.ApplicationEnvironment
import io.ktor.util.KtorExperimentalAPI
import org.bson.Document
import org.litote.kmongo.KMongo

@KtorExperimentalAPI
class MongoRepository(env: ApplicationEnvironment) {

    private val mongoHost= env.config.property("mongo.host").getString()
    private val mongoPort= env.config.property("mongo.port").getString().toInt()
    private val mongoDb= env.config.property("mongo.db").getString()
    private val mongoUser= env.config.property("mongo.user").getString()
    private val mongoPwd= env.config.property("mongo.pwd").getString()

    private val client = KMongo.createClient(server(), credentials())

    fun list(listName : String): MongoCollection<Item> {
        create(listName)
        return data().getCollection<Item>(listName, Item::class.java)
    }

    fun create(listName: String) {
        if (!data().listCollectionNames().contains(listName)) {
            data().createCollection(
                listName,
                CreateCollectionOptions()
                    .collation(
                        Collation.builder()
                            .locale("en")
                            .collationStrength(CollationStrength.PRIMARY)
                            .caseLevel(false)
                            .normalization(true)
                            .build()
                    )
                    .validationOptions(ValidationOptions().validationAction(ValidationAction.WARN))
            )
            data().getCollection(listName)
                .createIndex(
                    Document(Item::name.name, 1),
                    IndexOptions()
                        .unique(true)
                )
        }
    }

    private fun data(): MongoDatabase {
        return client.getDatabase(mongoDb)
    }

    private fun server(): ServerAddress {
        return ServerAddress(mongoHost, mongoPort)
    }

    private fun credentials(): List<MongoCredential> {
        return listOf<MongoCredential>(
            MongoCredential.createCredential(mongoUser, mongoDb, mongoPwd.toCharArray())
        )
    }
}
