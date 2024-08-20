package com.example.litpantry.screen.start

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.litpantry.adapter.REPOSITORY
import com.example.litpantry.database.entity.Book
import com.example.litpantry.database.LitDatabase
import com.example.litpantry.database.dao.repository.BookRealization
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class StartViewModel  (application: Application): AndroidViewModel(application) {

    private val allBooks : LiveData<List<Book>>

    init {
        val dao = LitDatabase.getDatabase(application).getBookDao()
        REPOSITORY = BookRealization(dao)
        allBooks = REPOSITORY.allBooks
    }

    fun deleteBook (book: Book) = viewModelScope.launch(Dispatchers.IO) {
        REPOSITORY.deleteBook(book)

    }

    fun insertBook (book: Book) = viewModelScope.launch(Dispatchers.IO) {
        REPOSITORY.insertBook(book)

    }

   fun getAllBooks(): LiveData<List<Book>> {
        return REPOSITORY.allBooks
    }

    fun searchBook(searchQuery:String): LiveData<List<Book>> {
        return REPOSITORY.searchBook(searchQuery).asLiveData()
    }
 }