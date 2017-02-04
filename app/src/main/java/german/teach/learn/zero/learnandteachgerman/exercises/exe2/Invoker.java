package german.teach.learn.zero.learnandteachgerman.exercises.exe2;

import java.util.ArrayList;

import german.teach.learn.zero.learnandteachgerman.models.Command;

/**
 * Created by zero on 04.02.17.
 */

public class Invoker {
    ArrayList<Command> commandQueue = new ArrayList<Command>();
    int head = 0;

    public void addCommand(Command cmd) {
        this.commandQueue.add(cmd);
    }

    public void executeSingleCommand() {
        if (head == this.commandQueue.size()) return;
        this.commandQueue.get(head++).execute();
    }

    public void undoSingleCommand() {
        if (head == 0) return;
        this.commandQueue.get(--head).undo();
        this.commandQueue.remove(this.commandQueue.size()-1);
    }
}
