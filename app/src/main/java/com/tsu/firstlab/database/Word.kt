package com.tsu.firstlab.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Word(
    @PrimaryKey(autoGenerate = false) val word: String,
    val phonetic: String,
    val partOfSpeech: String,
    val soundURL: String
)
