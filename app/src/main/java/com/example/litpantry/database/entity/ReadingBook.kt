package com.example.litpantry.database.entity

import androidx.room.*
import java.io.Serializable

@Entity
data class ReadingBook(
    @PrimaryKey(autoGenerate = true)
    val bookId: Int = 0,

    val bookName:String?,

    val calendarDate: String?,

    var isRead: Boolean
)
