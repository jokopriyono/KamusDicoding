package com.jokopriyono.kamusdicoding.data.database.model;

public class KamusModel {
    private int id;
    private String word;
    private String translate;

    public KamusModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }
}
