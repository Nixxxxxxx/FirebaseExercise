package german.teach.learn.zero.learnandteachgerman.models;

import java.util.UUID;

/**
 * Created by zero on 02.02.17.
 */

public class Exercise1 {
    // Should match firebase storage name reference used there
    private String image, question, answer;
    private UUID mId;

    public Exercise1() {
        mId = UUID.randomUUID();

    }

    public Exercise1(String answer, String message, String image) {
        this.answer = answer;
        this.question = question;
        this.image = image;
    }

    public UUID getId() {
        return mId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
