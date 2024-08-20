package com.example.litpantry.database.entity

import androidx.room.*
import java.io.Serializable

@Entity
data class OtherInfCharacter(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "other_inf_char_id")
    val otherInfCharId: Int = 0,


    @ColumnInfo(name = "text_other_inf_char")
    val text:String?,

    @ColumnInfo(name = "parent_character_id")
    val parentCharacter: Int

) : Serializable


data class CharacterAndOtherInf (
    @Embedded val bookCharacter: BookCharacter,

    @Relation(
        parentColumn = "character_id",
        entityColumn = "parent_character_id")
    val bookCharacterList: List<OtherInfCharacter>

)
