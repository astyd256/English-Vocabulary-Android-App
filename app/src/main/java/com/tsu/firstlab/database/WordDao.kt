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

    @Transaction
    @Query("SELECT COUNT(*) FROM word ")
    fun getSize(): Int
    @Transaction
    @Query("UPDATE word SET learningprogress = 0")
    fun resetLearningProgress()

    @Transaction
    @Query("SELECT * FROM word ORDER BY learningprogress ASC LIMIT 10")
    fun getTenWords(): List<WordWithMeanings>

    @Transaction
    @Query("SELECT word FROM word")
    fun getStringWords(): List<String>

    @Query("UPDATE word SET learningprogress = learningprogress + 1 WHERE word = :word")
    fun increaseLearningProgress(word: String)

    @Query("UPDATE word SET learningprogress = learningprogress - 1 WHERE word = :word")
    fun decreaseLearningProgress(word: String)
}