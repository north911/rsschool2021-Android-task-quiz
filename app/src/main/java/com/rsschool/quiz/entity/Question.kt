package com.rsschool.quiz.entity

data class Question(
    val question: String,
    val opt1: String,
    val opt2: String,
    val opt3: String,
    val opt4: String,
    val opt5: String,
    val correct: String
)
