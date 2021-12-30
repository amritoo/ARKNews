package com.example.arknews.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "faq")
public class FAQ {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "question")
    private String question;
    @ColumnInfo(name = "answer")
    private String answer;

    public FAQ(int id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    @Ignore
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
