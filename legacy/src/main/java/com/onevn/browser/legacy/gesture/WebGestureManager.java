package com.onevn.browser.legacy.gesture;

import android.content.Context;
import android.gesture.GestureOverlayView;

import com.onevn.browser.legacy.settings.data.AppData;

public class WebGestureManager extends GestureManager {
    public WebGestureManager(Context context) {
        super(context, GESTURE_TYPE_WEB);
    }

    @Override
    public int getGestureStrokeType() {
        return GestureOverlayView.GESTURE_STROKE_TYPE_SINGLE;
    }

    @Override
    public double getGestureScore() {
        return AppData.gesture_score_web.get();
    }
}
