package com.example.litpantry.screen.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.litpantry.adapter.REPOSITORY
import com.example.litpantry.database.entity.Book
import com.example.litpantry.database.entity.ReadingBook
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalendarViewModel : ViewModel() {

    fun deleteReadingBook(readingBook: ReadingBook)  = viewModelScope.launch(Dispatchers.IO) {
        REPOSITORY.deleteReadingBook(readingBook)
    }

    fun getAllBooks(): LiveData<List<Book>> {
        return REPOSITORY.allBooks
    }

    fun getBook(bookId: Int) : Book {
        return REPOSITORY.getBook(bookId)
    }

    fun getReadingBook(): LiveData<List<ReadingBook>> {
        return REPOSITORY.getReadingBook()
    }

    fun getIsReadingBook(ok: Boolean): LiveData<List<ReadingBook>> {
        return REPOSITORY.getIsReadingBook(ok)
    }


    fun updateReadingBook(readingBook: ReadingBook) = viewModelScope.launch(Dispatchers.IO)  {
        REPOSITORY.updateReadingBook(readingBook)
    }


}

