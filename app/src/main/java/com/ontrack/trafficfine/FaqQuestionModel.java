package com.ontrack.trafficfine;

public class FaqQuestionModel {
    private String faqQuestions;
    private String faqAnswers;
    private boolean faqExapand;

    public FaqQuestionModel(String faqQuestions, String faqAnswers) {
        this.faqQuestions = faqQuestions;
        this.faqAnswers = faqAnswers;
        this.faqExapand = true;
    }

    public String getFaqQuestions() {
        return faqQuestions;
    }

    public void setFaqQuestions(String faqQuestions) {
        this.faqQuestions = faqQuestions;
    }

    public String getFaqAnswers() {
        return faqAnswers;
    }

    public void setFaqAnswers(String faqAnswers) {
        this.faqAnswers = faqAnswers;
    }

    public boolean isFaqExapand() {
        return faqExapand;
    }

    public void setFaqExapand(boolean faqExapand) {
        this.faqExapand = faqExapand;
    }

    @Override
    public String toString() {
        return "FaqQuestionModel{" +
                "faqQuestions='" + faqQuestions + '\'' +
                ", faqAnswers='" + faqAnswers + '\'' +
                ", faqExapand=" + faqExapand +
                '}';
    }
}

