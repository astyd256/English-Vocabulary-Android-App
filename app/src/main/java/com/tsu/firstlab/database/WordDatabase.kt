package com.tsu.firstlab.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Word :: class,
        Meaning :: class
                ], version = 1
)
abstract class WordDatabase : RoomDatabase(){

    abstract val wordDao : WordDao

    companion object{

        @Volatile
        private var INSTANCE : WordDatabase? = null

        fun getDatabase(context : Context): WordDatabase {

            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    WordDatabase::class.java,
                    "appDatabase"
                ).build().also {
                    INSTANCE = it
                }
            }
        }

    }
}