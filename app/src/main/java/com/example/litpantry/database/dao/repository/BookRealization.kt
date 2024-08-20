package com.example.litpantry.database.dao.repository

import androidx.lifecycle.LiveData
import com.example.litpantry.database.dao.BookDao
import com.example.litpantry.database.entity.*
import kotlinx.coroutines.flow.Flow

class BookRealization(private val bookDao: BookDao): IBookRepository {

    override val allBooks: LiveData<List<Book>>
        get() = bookDao.getAllBooks()


    override suspend fun insertBook(book: Book/*, onSuccess: () -> Unit*/) {
        bookDao.insert(book)
        //onSuccess()
    }

    override suspend fun insertReadingBook(readingBook: ReadingBook) {
        bookDao.insertReadingBook(readingBook)
    }

    override suspend fun deleteBook(book: Book/*, onSuccess: () -> Unit*/) {
        bookDao.delete(book)
        //onSuccess()
    }

    override suspend fun insertOtherInf(otherInfBook: OtherInfBook) {
        bookDao.insertOtherInfo(otherInfBook)
    }

    override  /*suspend*/ fun getBookOtherInformation(bookId: Int): LiveData<BookAndInf> {
        return bookDao.getBookOtherInformation(bookId)
    }

    override suspend fun insertCharacter(character: BookCharacter) {
        return bookDao.insertCharacter(character)
    }

    override fun getCharacters(bookId: Int): LiveData<BookAndCharacter> {
        return bookDao.getCharacters(bookId)
    }



    override suspend fun insertCharacterCrossRef(characterCrossRef: BookCharacterCrossRef) {
        bookDao.insertBookCharacterCrossRef(characterCrossRef)
    }

    override fun getCharacterRelations (characterId: Int): LiveData<CharacterWithOther> {
        return bookDao.getCharacterRelations(characterId)
    }

    override suspend fun updateCharacter(bookCharacter: BookCharacter) {
        bookDao.updateCharacter(bookCharacter)
    }

    override fun getCharacterOtherInf(characterId: Int): LiveData<CharacterAndOtherInf> {
        return bookDao.getCharacterOtherInf(characterId)
    }

    override fun getCharacterQuotes(characterId: Int): LiveData<CharacterAndQuote> {
        return bookDao.getCharacterQuotes(characterId)
    }

    override suspend fun insertCharacterQuote(quote: Quote) {
        bookDao.insertCharacterQuote(quote)
    }

    override suspend fun insertCharacterOtherInf(characterOtherInf: OtherInfCharacter) {
        bookDao.insertCharacterOtherInf(characterOtherInf)
    }

    override fun getCharacterRelationFromBook(bookId: Int): LiveData<List<BookCharacterCrossRef>> {
        return bookDao.getCharacterRelationFromBook(bookId)
    }

    override suspend fun deleteOtherInfBook(otherInfBook: OtherInfBook) {
        bookDao.deleteOtherInfBook(otherInfBook)
    }

    override suspend fun deleteOtherInfChar(otherInfCharacter: OtherInfCharacter) {
        bookDao.deleteOtherInfChar(otherInfCharacter)
    }

    override suspend fun deleteOtherInfQuote(quote: Quote) {
        bookDao.deleteOtherInfQuote(quote)
    }

    override suspend fun deleteBookCharacter(character: BookCharacter) {
        bookDao.deleteBookCharacter(character)
    }

    override suspend fun deleteRelation(bookCharacterCrossRef: BookCharacterCrossRef) {
        bookDao.deleteRelation(bookCharacterCrossRef)
    }

    override suspend fun deleteReadingBook(readingBook: ReadingBook) {
        bookDao.deleteReadingBook(readingBook)
    }

    override fun getBook(idBook: Int): Book {
        return bookDao.getBook(idBook)
    }

    override fun getReadingBook(): LiveData<List<ReadingBook>> {
        return bookDao.getReadingBook()
    }

    override suspend fun updateReadingBook(readingBook: ReadingBook) {
        bookDao.updateReadingBook(readingBook)
    }

    override fun getIsReadingBook(ok: Boolean): LiveData<List<ReadingBook>> {
        return bookDao.getIsReadingBook(ok)
    }

    override fun searchBook(searchQuery:String): Flow<List<Book>> {
        return bookDao.searchBook(searchQuery)
    }

}