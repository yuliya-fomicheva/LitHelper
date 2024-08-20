package com.example.litpantry.database.entity

import androidx.room.*
import java.io.Serializable

@Entity
data class Book(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "book_id")
    val bookId: Int = 0,

    @ColumnInfo(name = "book_name")
    val bookName:String?,

    @ColumnInfo(name = "book_author")
    val bookAuthor:String?,

    @ColumnInfo(name = "year")
    val year: String?,

    @ColumnInfo(name = "calendar_date")
    val calendarDate: String?,

) : Serializable //what is it?


