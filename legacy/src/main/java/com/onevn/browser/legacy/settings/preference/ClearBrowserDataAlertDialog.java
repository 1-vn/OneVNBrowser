/*
 * Copyright (C) 2017-2019 DiepDT
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.onevn.browser.legacy.settings.preference;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.WebViewDatabase;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import com.onevn.browser.favicon.FaviconManager;
import com.onevn.browser.legacy.R;
import com.onevn.browser.legacy.browser.BrowserManager;
import com.onevn.browser.legacy.history.BrowserHistoryManager;
import com.onevn.browser.legacy.settings.data.AppData;
import com.onevn.browser.ui.BrowserApplication;
import com.onevn.browser.ui.preference.CustomDialogPreference;

public class ClearBrowserDataAlertDialog extends CustomDialogPreference {


    public ClearBrowserDataAlertDialog(Context context) {
        this(context, null);
    }

    public ClearBrowserDataAlertDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @NonNull
    @Override
    protected CustomDialogFragment crateCustomDialog() {
        return new ClearDialog();
    }

    public static class ClearDialog extends CustomDialogFragment {
        private int mSelected = 0;
        private int mArrayMax;

        private int[] ids;

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            mSelected = AppData.clear_data_default.get();

            final Context context = getContext();

            String[] arrays = context.getResources().getStringArray(R.array.clear_browser_data);
            ids = context.getResources().getIntArray(R.array.clear_browser_data_id);

            if (arrays.length != ids.length) {
                throw new RuntimeException();
            }

            mArrayMax = arrays.length;

            final ListView listView = new ListView(context);
            listView.setAdapter(new ArrayAdapter<>(context, R.layout.select_dialog_multichoice, arrays));
            listView.setItemsCanFocus(false);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            for (int i = 0; i < mArrayMax; ++i) {
                int shifted = 1 << i;
                listView.setItemChecked(i, ((mSelected & shifted) == shifted));
            }
            listView.setOnItemClickListener((parent, view, position, id) -> {
                if (listView.isItemChecked(position))
                    mSelected |= 1 << position;
                else
                    mSelected &= ~(1 << position);
            });

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder
                    .setTitle(R.string.pref_clear_browser_data)
                    .setView(listView)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> onClickPositiveButton())
                    .setNegativeButton(android.R.string.cancel, null);

            return builder.create();
        }

        private void onClickPositiveButton() {
            for (int i = 0; i < mArrayMax; ++i) {
                int shifted = 1 << i;
                if ((mSelected & shifted) == shifted)
                    runAction(ids[i]);
            }

            AppData.clear_data_default.set(mSelected);
            AppData.commit(getContext().getApplicationContext(), AppData.clear_data_default);
        }

        private void runAction(int i) {
            Context con = getContext();
            if (con == null) throw new IllegalStateException();
            switch (i) {
                case 0:
                    BrowserManager.clearAppCacheFile(con.getApplicationContext());
                    BrowserManager.clearCache(con.getApplicationContext());
                    break;
                case 1:
                    CookieManager.getInstance().removeAllCookies(null);
                    break;
                case 2:
                    WebViewDatabase.getInstance(con.getApplicationContext()).clearHttpAuthUsernamePassword();
                    break;
                case 3:
                    //noinspection deprecation
                    WebViewDatabase.getInstance(con).clearFormData();
                    break;
                case 4:
                    BrowserManager.clearWebDatabase();
                    break;
                case 5:
                    BrowserManager.clearGeolocation();
                    break;
                case 6:
                    BrowserHistoryManager.getInstance(con.getApplicationContext()).deleteAll();
                    break;
                case 7:
                    getContext().getApplicationContext().getContentResolver().delete(
                            ((BrowserApplication)con.getApplicationContext()).getProviderManager().getSuggestProvider().getUriLocal()
                            , null, null);
                    break;
                case 8:
                    FaviconManager.Companion.getInstance(getContext()).clear();
                    break;
            }
        }
    }
}
