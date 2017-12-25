package org.jitu.wagtailmemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class SekireiGakuen extends AppCompatActivity {
    private Sinbunbu sinbunbu = new Sinbunbu(this);
    private Shasinbu shasinbu = new Shasinbu(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restoreEditText();
    }

    @Override
    public void onResume() {
        super.onResume();
        restoreEditText();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveEditText();
    }

    private void restoreEditText() {
        EditText edit = findViewById(R.id.editText);
        sinbunbu.interview(edit);
    }

    private void saveEditText() {
        EditText edit = findViewById(R.id.editText);
        sinbunbu.publish(edit);
    }

    public void onClickButtonOk(View view) {
        View button = findViewById(R.id.button);
        view.setVisibility(View.INVISIBLE);
        saveImage();
        view.setVisibility(View.VISIBLE);
    }

    private void saveImage() {
        EditText edit = findViewById(R.id.editText);
        shasinbu.publish(edit);
    }
}
