package com.example.litpantry.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.litpantry.database.dao.BookDao
import com.example.litpantry.database.entity.*

@Database(entities = [Book::class, OtherInfBook::class, BookCharacter::class, BookCharacterCrossRef::class,
    Quote::class, OtherInfCharacter::class, ReadingBook::class], version = 1)
abstract class LitDatabase: RoomDatabase() {

    abstract fun getBookDao(): BookDao

   companion object {
        // Singleton prevents multiple
        // instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: LitDatabase? = null

        fun getDatabase(context: Context): LitDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LitDatabase::class.java,
                    "lit_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
