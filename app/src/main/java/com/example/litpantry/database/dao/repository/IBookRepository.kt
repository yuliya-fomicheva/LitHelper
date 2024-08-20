package com.example.litpantry.database.dao.repository

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.litpantry.database.entity.*
import kotlinx.coroutines.flow.Flow

interface IBookRepository {

    val allBooks: LiveData<List<Book>>

    suspend fun insertBook(book: Book/*, onSuccess:() -> Unit*/)

    suspend fun insertReadingBook(readingBook: ReadingBook/*, onSuccess:() -> Unit*/)

    suspend fun deleteBook(book: Book/*, onSuccess:() -> Unit*/)

    suspend fun insertOtherInf(otherInfBook: OtherInfBook)

  /*  suspend*/ fun getBookOtherInformation (bookId: Int): LiveData<BookAndInf>

    suspend fun insertCharacter(character: BookCharacter)

    /*  suspend*/ fun getCharacters (bookId: Int): LiveData<BookAndCharacter>
    


    suspend fun insertCharacterCrossRef(characterCrossRef: BookCharacterCrossRef)

    fun getCharacterRelations (characterId: Int): LiveData<CharacterWithOther>

    suspend fun updateCharacter(bookCharacter: BookCharacter/*, onSuccess:() -> Unit*/)

    fun getCharacterOtherInf (characterId: Int): LiveData<CharacterAndOtherInf>

    fun getCharacterQuotes (characterId: Int): LiveData<CharacterAndQuote>

    suspend fun insertCharacterQuote(quote: Quote)

    suspend fun insertCharacterOtherInf(characterOtherInf: OtherInfCharacter)

    fun getCharacterRelationFromBook(bookId: Int): LiveData<List<BookCharacterCrossRef>>

    suspend fun deleteOtherInfBook(otherInfBook: OtherInfBook/*, onSuccess:() -> Unit*/)

    suspend fun deleteOtherInfChar(otherInfCharacter: OtherInfCharacter)

    suspend fun deleteOtherInfQuote(quote: Quote)

    suspend fun deleteBookCharacter(character: BookCharacter)

    suspend fun deleteRelation(bookCharacterCrossRef: BookCharacterCrossRef)

  suspend fun deleteReadingBook(readingBook: ReadingBook)

    fun getBook(idBook: Int) : Book

  fun getReadingBook(): LiveData<List<ReadingBook>>

  suspend fun updateReadingBook(readingBook: ReadingBook)

  fun getIsReadingBook(ok: Boolean): LiveData<List<ReadingBook>>

  fun searchBook(searchQuery:String): Flow<List<Book>>



}