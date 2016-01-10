package models;

import java.util.ArrayList;

public class Category {
    private int id;
    private String name;
    private ArrayList<String> words;

    public Category() {
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public void addWord(String word) {
        this.words.add(word);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
