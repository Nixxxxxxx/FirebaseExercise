package german.teach.learn.zero.learnandteachgerman.exercises.exe2;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import german.teach.learn.zero.learnandteachgerman.R;

/**
 * Created by zero on 03.02.17.
 */

public class MyCustomadapter extends BaseAdapter {
    private Context mContext;
    private String mWord;
    // Keep all Images in array
    public Character[] alphabets;

    // Constructor
    public MyCustomadapter(Context c, String word) {
        mContext = c;
        mWord = word;
        alphabets = toCharacterArray(mWord);
    }

    public int getCount() {
        return alphabets.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;

        if (convertView == null) {
            textView = new TextView(mContext);
            textView.setLayoutParams(new GridView.LayoutParams(80, 100));
            textView.setPadding(3, 3, 3, 3);
            textView.setTextSize(25);
            textView.setTypeface(null, Typeface.BOLD);
        } else {
            textView = (TextView) convertView;
        }
        textView.setText(alphabets[position].toString());

        return textView;
    }

    public static Character[] toCharacterArray(String sourceString) {
        char[] charArrays = new char[sourceString.length()];
        charArrays = sourceString.toCharArray();
        Character[] characterArray = new Character[charArrays.length];
        for (int i = 0; i < charArrays.length; i++) {
            characterArray[i] = charArrays[i];
        }
        shuffleArray(characterArray);
        return characterArray;
    }

    static void shuffleArray(Character[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Character a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}