package com.tsu.firstlab.database
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Meaning(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val definition: String,
    val example: String,
    val word: String
)
