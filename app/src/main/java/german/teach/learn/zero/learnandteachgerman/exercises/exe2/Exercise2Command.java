package german.teach.learn.zero.learnandteachgerman.exercises.exe2;

import android.view.View;
import android.widget.TextView;

import german.teach.learn.zero.learnandteachgerman.models.Command;

/**
 * Created by zero on 04.02.17.
 */

public class Exercise2Command implements Command {
    private View charPressed;
    private TextView mTextView;
    private String oldWord;
    private String newWord;

    public Exercise2Command(View cp, TextView tv){
        this.charPressed = cp;
        this.mTextView = tv;
        this.oldWord = mTextView.getText().toString();
        this.newWord = this.oldWord + ((TextView) cp).getText().toString();
    }

    @Override
    public void execute() {
        this.charPressed.setVisibility(View.INVISIBLE);
        this.mTextView.setText(newWord);
    }

    @Override
    public void undo() {
        this.charPressed.setVisibility(View.VISIBLE);
        this.mTextView.setText(oldWord);
    }
}
