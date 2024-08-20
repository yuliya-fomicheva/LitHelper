package com.example.litpantry.database.entity

import androidx.room.*
import java.io.Serializable

@Entity
data class Quote(
@PrimaryKey(autoGenerate = true)
@ColumnInfo(name = "quote_id")
val quoteId: Int = 0,


@ColumnInfo(name = "text_quote")
val text:String?,

@ColumnInfo(name = "parent_character_id")
val parentCharacter: Int

) : Serializable


data class CharacterAndQuote (
    @Embedded val bookCharacter: BookCharacter,

    @Relation(
        parentColumn = "character_id",
        entityColumn = "parent_character_id")
    val quotes: List<Quote>

)