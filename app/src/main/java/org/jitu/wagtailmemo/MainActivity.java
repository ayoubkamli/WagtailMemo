package org.jitu.wagtailmemo;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Idle idle = new Idle(this);
    private Kameko kameko = new Kameko(this);

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
        idle.load(edit);
    }

    private void saveEditText() {
        EditText edit = findViewById(R.id.editText);
        idle.save(edit);
    }

    public void onClickButtonOk(View view) {
        View button = findViewById(R.id.button);
        view.setVisibility(View.INVISIBLE);
        saveImage();
        view.setVisibility(View.VISIBLE);
    }

    private void saveImage() {
        EditText edit = findViewById(R.id.editText);
        kameko.save(edit);
    }
}

abstract class FileSaver {
    Activity activity;

    FileSaver(Activity arg) {
        this.activity = arg;
    }

    abstract void save(EditText edit);

    void toastLong(String msg) {
        toast(msg, Toast.LENGTH_LONG);
    }

    void toastShort(String msg) {
        toast(msg, Toast.LENGTH_SHORT);
    }

    private void toast(String msg, int duration) {
        Toast.makeText(activity, msg, duration).show();
    }

    void toast(Throwable e) {
        toastLong(e.getMessage());
    }
}

class Kameko extends FileSaver {
    Kameko(Activity arg) {
        super(arg);
    }

    @Override
    void save(EditText edit) {
        edit.setCursorVisible(false);
        View view = activity.getWindow().getDecorView().getRootView();
        view.setDrawingCacheEnabled(true);
        view.destroyDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        setWallpaper(bitmap);
        view.setDrawingCacheEnabled(false);
        edit.setCursorVisible(true);
    }

    private void setWallpaper(Bitmap bitmap) {
        WallpaperManager mgr = (WallpaperManager) activity.getSystemService(Context.WALLPAPER_SERVICE);
        try {
            mgr.setBitmap(bitmap);
            toastShort("Set Wallpaper");
        } catch (IOException e) {
            toast(e);
        }
    }
}

class Idle extends FileSaver {
    Idle(Activity arg) {
        super(arg);
    }

     @NonNull
    protected File getFile() {
        return new File(activity.getFilesDir(), "WagtailMemo.txt");
    }

    @Override
    void save(EditText edit) {
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

    void load(EditText edit) {
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
