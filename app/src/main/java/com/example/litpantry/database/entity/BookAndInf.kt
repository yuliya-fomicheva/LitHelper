package com.example.litpantry.database.entity

import androidx.lifecycle.LiveData
import androidx.room.Embedded
import androidx.room.Relation


data class BookAndInf (
    @Embedded val book: Book,

    @Relation(
        parentColumn = "book_id",
        entityColumn = "parent_book_id")
    val otherInforms: List<OtherInfBook>

)