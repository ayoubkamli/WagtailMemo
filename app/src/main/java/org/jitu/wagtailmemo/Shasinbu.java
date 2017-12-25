package org.jitu.wagtailmemo;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

class Shasinbu extends Bunkabu {
    Shasinbu(Activity arg) {
        super(arg);
    }

    @Override
    void publish(EditText edit) {
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
        } catch (IOException e) {
            toast(e);
        }
    }
}
