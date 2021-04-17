package com.murad.kareem_school.models.exams

class Question {

    private var question: String = ""
    private var answer: String = ""



    fun setQuestion(question: String) {
        this.question = question
    }

    fun getQuestion() = this.question

    fun setAnswer(answer: String) {
        this.answer = answer
    }

    fun getAnswer() = this.answer
}