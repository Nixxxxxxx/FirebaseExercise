package german.teach.learn.zero.learnandteachgerman.exercises.exe1;

import java.util.UUID;

/**
 * Created by zero on 02.02.17.
 */

public class Exercise1 {
    // Should match firebase storage name reference used there
    private String image, word, article;
    private UUID mId;

    public Exercise1() {
        mId = UUID.randomUUID();

    }

    public Exercise1(String article, String word, String image) {
        this.article = article;
        this.word = word;
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

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
