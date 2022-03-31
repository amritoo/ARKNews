package com.example.arknews.model;

public class FAQ {

    private int id;

    private String question;
    private String answer;

    public FAQ(int id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public FAQ(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
