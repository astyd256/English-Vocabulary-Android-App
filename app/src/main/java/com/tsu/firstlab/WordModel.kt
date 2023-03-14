package com.tsu.firstlab

data class WordModel(
    val word: String,
    val phonetic: String,
    val partOfSpeech: String,
    val defenitions: List<String>,
    val examples: List<String>,
    val soundURL: String
)
