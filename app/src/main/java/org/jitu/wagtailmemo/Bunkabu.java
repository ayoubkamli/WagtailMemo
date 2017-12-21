package org.jitu.wagtailmemo;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Toast;

abstract class Bunkabu {
    Activity activity;

    Bunkabu(Activity arg) {
        this.activity = arg;
    }

    abstract void publish(EditText edit);

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
