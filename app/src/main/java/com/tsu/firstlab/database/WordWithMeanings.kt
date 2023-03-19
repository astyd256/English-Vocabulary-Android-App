package com.tsu.firstlab.database

import androidx.room.Embedded
import androidx.room.Relation

data class WordWithMeanings(
    @Embedded val word: Word,
    @Relation(
        parentColumn = "word",
        entityColumn = "word"
    )
    val meanings: List<Meaning>
)