package com.tsu.firstlab.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    version = 2,
    entities = [
        Word :: class,
        Meaning :: class
                ],
    autoMigrations = [
        AutoMigration (from = 1, to = 2)
    ]
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