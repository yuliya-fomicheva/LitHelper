package com.example.litpantry.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class OtherInfBook(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "otherInf_id")
    val bookId: Int = 0,


    @ColumnInfo(name = "information")
    val information:String?,

    @ColumnInfo(name = "parent_book_id")
    val parentBookIdOther: Int

) : Serializable
