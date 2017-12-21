package org.jitu.wagtailmemo;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class Sinbunbu extends Saver {
    Sinbunbu(Activity arg) {
        super(arg);
    }

    @NonNull
    protected File getFile() {
        return new File(activity.getFilesDir(), "WagtailMemo.txt");
    }

    @Override
    void publish(EditText edit) {
        saveText(edit.getText().toString(), getFile());
    }

    private void saveText(String text, File file) {
        FileWriter out = null;
        try {
            out = new FileWriter(file);
            out.write(text);
            out.flush();
        } catch (IOException e) {
            toast(e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void interview(EditText edit) {
        String text = loadText(getFile());
        edit.setText(text);
    }

    @NonNull
    private String loadText(File file) {
        BufferedReader br = null;
        try {
            int c;
            StringBuilder buf = new StringBuilder();
            FileReader in = new FileReader(file);
            br = new BufferedReader(in);
            while ((c = br.read()) != -1) {
                buf.append((char)c);
            }
            return buf.toString();
        } catch (IOException e) {
            return "";
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
