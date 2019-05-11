package com.onevn.browser.legacy.pattern;

import com.onevn.browser.legacy.utils.matcher.AbstractPatternChecker;

public abstract class PatternChecker extends AbstractPatternChecker<PatternAction> {
    protected PatternChecker(PatternAction pattern_action) {
        super(pattern_action);
    }
}
