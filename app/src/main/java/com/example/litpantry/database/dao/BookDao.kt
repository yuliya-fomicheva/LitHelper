package com.example.litpantry.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.litpantry.database.entity.*
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    //получить все книги
    @Query("SELECT * FROM Book")
    fun getAllBooks(): LiveData<List<Book>>
    //получить книгу по id
    @Query("SELECT * FROM Book WHERE book_id=:bookId")
    fun getBook(bookId: Int): Book
    //добавить книгу
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(book: Book)
    //получить список книг с датой прочтения
    @Query("SELECT * FROM ReadingBook")
    fun getReadingBook(): LiveData<List<ReadingBook>>
    //получить прочитанные книги
    @Query("SELECT * FROM ReadingBook where isRead=:ok")
    fun getIsReadingBook(ok: Boolean): LiveData<List<ReadingBook>>
    //добавить книгу с датой прочтения
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertReadingBook(readingBook: ReadingBook)
    //обновить основную информацию о книге
    @Update
    fun updateBook(book: Book)
    //удалить книгу
    @Delete
    fun delete(book: Book)
    //получить список доп. информации о книге
    @Transaction
    @Query("SELECT * FROM Book where book_id =:bookId")
    fun getBookOtherInformation (bookId: Int): LiveData<BookAndInf>
    //добавить доп.информацию о книге
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOtherInfo(otherInfBook: OtherInfBook)
    //обновить доп.информацию о книге
    @Update
    fun updateOtherInfo(otherInfBook: OtherInfBook)
    //добавить персонажа
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCharacter(character: BookCharacter)
    //получить список персонажей
    @Transaction
    @Query("SELECT * FROM Book where book_id =:bookId")
    fun getCharacters (bookId: Int): LiveData<BookAndCharacter>
    //добавить отношения между персонажами
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBookCharacterCrossRef(characterCrossRef: BookCharacterCrossRef)
    //получить список взаимоотношений опредленного персонажа
    @Transaction
    @Query("SELECT * FROM BookCharacter where character_id =:characterId")
    fun getCharacterRelations (characterId: Int): LiveData<CharacterWithOther>
    //обновить персонажа
    @Update
    fun updateCharacter(bookCharacter: BookCharacter)
    //получить список доп. информации о персонаже
    @Transaction
    @Query("SELECT * FROM BookCharacter where character_id =:characterId")
    fun getCharacterOtherInf (characterId: Int): LiveData<CharacterAndOtherInf>
    //получить список цитат персонажа
    @Transaction
    @Query("SELECT * FROM BookCharacter where character_id =:characterId")
    fun getCharacterQuotes (characterId: Int): LiveData<CharacterAndQuote>
    //добавить цитату персонажу
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCharacterQuote(quote: Quote)
    //обновить цитату
    @Update
    fun updateQuote(quote: Quote)
    //обновить доп. инф-цию о персонаже
    @Update
    fun updateCharacterOtherInf(characterOtherInf: OtherInfCharacter)
    //добавить доп. инф-цию о персонаже
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCharacterOtherInf(characterOtherInf: OtherInfCharacter)
    //получить список всех взаимоотношений по книге
    @Transaction
    @Query("SELECT CR.* FROM BookCharacter BC JOIN BookCharacterCrossRef CR ON " +
            "BC.character_id = CR.character_id where BC.parent_book_id_to_character =:bookId")
    fun getCharacterRelationFromBook(bookId: Int): LiveData<List<BookCharacterCrossRef>>
    //удалить доп. информацию о книге
    @Delete
    fun deleteOtherInfBook(otherInfBook: OtherInfBook)
    //удалить доп. информацию о персонаже
    @Delete
    fun deleteOtherInfChar(otherInfCharacter: OtherInfCharacter)
    //удалить цитату
    @Delete
    fun deleteOtherInfQuote(quote: Quote)
    //удалить персонажа
    @Delete
    fun deleteBookCharacter(character: BookCharacter)
    //удалить взаимоотношения персонажей
    @Delete
    fun deleteRelation(bookCharacterCrossRef: BookCharacterCrossRef)
    //удалить книгу из календаря
    @Delete
    fun deleteReadingBook(readingBook: ReadingBook)
    //обновить книгу из календаря
    @Update
    fun updateReadingBook(readingBook: ReadingBook)
    //поиск книге по названию и автору
    @Query("SELECT * FROM Book WHERE book_name LIKE:searchQuery OR book_author LIKE :searchQuery ")
    fun searchBook(searchQuery:String): Flow<List<Book>>

}