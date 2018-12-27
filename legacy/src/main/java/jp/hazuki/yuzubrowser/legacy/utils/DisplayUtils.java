package jp.hazuki.yuzubrowser.legacy.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import jp.hazuki.yuzubrowser.legacy.settings.data.AppData;
import jp.hazuki.yuzubrowser.legacy.theme.ThemeData;

public class DisplayUtils {
    private DisplayUtils() {
        throw new UnsupportedOperationException();
    }

    public static int getDisplayHeight(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point.y;
    }

    public static int getFullScreenVisibility() {
        switch (AppData.fullscreen_hide_mode.get()) {
            case 0:
            default:
                return View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | ThemeData.getSystemUiVisibilityFlag();
            case 1:
                return View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | ThemeData.getSystemUiVisibilityFlag();
            case 2:
                return View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | ThemeData.getSystemUiVisibilityFlag();
        }
    }

    public static boolean isNeedFullScreenFlag() {
        return AppData.fullscreen_hide_mode.get() != 1;
    }
}
