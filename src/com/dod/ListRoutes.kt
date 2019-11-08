package com.dod

import com.google.gson.JsonParser
import com.dod.lists.ItemList
import io.ktor.application.call
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.routing.*

fun Route.listRoutes(list : ItemList) {

    route("/lists") {
        post("/{list}") {
            val done = list.create(call.parameters["list"]!!)
            call.respond(if (done) OK else BadRequest)
        }

        get("/{list}/{item}") {
            val found = list.find(call.parameters["list"]!!, call.parameters["item"]!!)
            call.respond(if (found) OK else NotFound)
        }

        post("/{list}/bulk") {
            val json = JsonParser().parse(call.receiveText())
            val done = list.addBulk(call.parameters["list"]!!, json.asJsonObject["items"])
            call.respond(if (done) OK else BadRequest)
        }

        post("/{list}/{item}") {
            val done = list.add(call.parameters["list"]!!, call.parameters["item"]!!)
            call.respond(if (done) Created else BadRequest)
        }

        delete("/{list}/{item}") {
            val done = list.remove(call.parameters["list"]!!, call.parameters["item"]!!)
            call.respond(if (done) OK else NotFound)
        }
    }
}