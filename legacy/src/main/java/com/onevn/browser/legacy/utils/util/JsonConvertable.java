package com.onevn.browser.legacy.utils.util;

public interface JsonConvertable {
    String toJsonString();

    boolean fromJsonString(String str);
}
