package com.dod.lists

import java.time.Instant

data class Item(
    val name: String,
    val createdAt: Instant = Instant.now()
)
