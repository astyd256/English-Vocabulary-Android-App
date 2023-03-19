package com.tsu.firstlab.database

import androidx.room.*

@Dao
interface WordDao  {
    @Query("SELECT * FROM word WHERE word LIKE :word")
    fun findByWord(word: String): Word

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(word: Word)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeaning(meaning: Meaning)

    @Transaction
    @Query("SELECT * FROM word WHERE word = :word")
    fun findWordWithMeanings(word: String): List<WordWithMeanings>

}