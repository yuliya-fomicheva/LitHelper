package com.example.litpantry.screen.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.litpantry.adapter.REPOSITORY
import com.example.litpantry.database.entity.Book
import com.example.litpantry.database.entity.ReadingBook
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class AddViewModel: ViewModel() {

    fun insertBook(book: Book)  = viewModelScope.launch(Dispatchers.IO) {
        REPOSITORY.insertBook(book)
    }

        fun insertReadingBook(readingBook: ReadingBook)  = viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.insertReadingBook(readingBook)
        }



}