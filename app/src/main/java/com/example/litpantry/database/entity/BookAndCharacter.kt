package com.example.litpantry.database.entity

import androidx.room.Embedded
import androidx.room.Relation
import java.io.Serializable

data class BookAndCharacter(
    @Embedded val book: Book,

    @Relation(
        parentColumn = "book_id",
        entityColumn = "parent_book_id_to_character")
    val characterList: List<BookCharacter>

)
