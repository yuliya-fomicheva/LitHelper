package com.example.litpantry.database.entity

import androidx.room.*
import java.io.Serializable

@Entity
data class BookCharacter(
@PrimaryKey(autoGenerate = true)
@ColumnInfo(name = "character_id")
val characterId: Int = 0,

@ColumnInfo(name = "character_name")
var characterName:String?,

@ColumnInfo(name = "parent_book_id_to_character")
val parentBookIdChar: Int,

@ColumnInfo(name = "biography")
var biography: String? = "",

@ColumnInfo(name = "portrait")
var portrait: String? = ""

) : Serializable //what is it?

@Entity
data class BookCharacterCrossRef(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "relation_id")
    val relationId: Int = 0,

    @ColumnInfo(name = "character_id")
    var characterId1: Int,

    @ColumnInfo(name = "character_name1")
    var characterName1: String?,

    @ColumnInfo(name = "character_id2")
    var characterId2: Int,

    @ColumnInfo(name = "character_name2")
    var characterName2: String?,

    var descriptionRelationship:String?
)

data class CharacterWithOther(
    @Embedded val bookCharacter: BookCharacter,
    @Relation(
        parentColumn = "character_id",
        entityColumn = "character_id",
    )
    val characters: List<BookCharacterCrossRef>
)