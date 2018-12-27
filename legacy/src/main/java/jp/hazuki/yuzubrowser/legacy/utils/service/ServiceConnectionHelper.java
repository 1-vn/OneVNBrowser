package jp.hazuki.yuzubrowser.legacy.utils.service;

import android.os.IBinder;
import android.support.annotation.Nullable;

public interface ServiceConnectionHelper<T> {
    T onBind(IBinder service);

    void onUnbind(@Nullable T service);
}
