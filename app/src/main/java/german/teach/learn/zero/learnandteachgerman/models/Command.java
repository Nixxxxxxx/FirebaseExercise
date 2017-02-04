package german.teach.learn.zero.learnandteachgerman.models;

/**
 * Created by zero on 04.02.17.
 */

public interface Command {
    void execute();

    void undo();
}
