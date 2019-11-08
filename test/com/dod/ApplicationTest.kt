package com.dod

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import io.ktor.util.KtorExperimentalAPI
import kotlin.test.Test
import kotlin.test.assertEquals

@KtorExperimentalAPI
class ApplicationTest {
    @Test
    fun testFindSignature() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/test/42").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }

            handleRequest(HttpMethod.Get, "/fail/404").apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
            }
        }
    }
}
