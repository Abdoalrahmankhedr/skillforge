package models;

import java.util.ArrayList;
import java.util.List;

// Andrew :)

public class Question {
    private String header;
    private List<Choice> choices = new ArrayList<>();

    public static class Choice {
        private String text;
        private boolean correct;

        /* This constructor is necessary for JSON parsing */
        public Choice() {}

        /* Getters & Setters */
        public String getText() {return text;}
        public void setText(String text) {this.text = text;}
        public boolean isCorrect() {return correct;}
        public void setCorrect(boolean correct) {this.correct = correct;}
    }

    /* This constructor is necessary for JSON parsing */
    public Question() {}

    public void addChoice(String text, boolean correct) {
        Choice choice = new Choice();
        choice.setCorrect(correct);
        choice.setText(text);
        this.choices.add(choice);
    }

    /* Getters & Setters */
    public String getHeader() {return header;}
    public void setHeader(String header) {this.header = header;}
    public List<Choice> getChoices() {return choices;}
    public void setChoices(List<Choice> choices) {this.choices = choices;}
}
