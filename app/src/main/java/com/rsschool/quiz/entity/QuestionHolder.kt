package com.rsschool.quiz.entity

var questions = listOf(
    Question("How many time zones are there in Russia", "7", "8", "9", "10", "11", "11"),
    Question(
        "What’s the smallest country in the world?",
        "Belarus",
        "Vatican",
        "Monaco",
        "Malta",
        "Barbados",
        "Vatican"
    ),
    Question(
        "What’s the capital of Canada? ",
        "Toronto",
        "Montreal",
        "Calgary",
        "Ottawa",
        "Vancouver",
        "Ottawa"
    ),
    Question(
        "What is the longest river in the world?",
        "Volga",
        "Nile",
        "Dniepr",
        "Neman",
        "Amazon",
        "Nile"
    ),
    Question(
        "Biggest country in the world?",
        "Russia",
        "Canada",
        "China",
        "USA",
        "Brazil",
        "Russia"
    )
)

fun getQuestion(pointer: Int): Question {
    return questions[pointer]
}
