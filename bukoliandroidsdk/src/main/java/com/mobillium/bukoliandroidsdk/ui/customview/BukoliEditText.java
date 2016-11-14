
package com.mobillium.bukoliandroidsdk.ui.customview;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.mobillium.bukoliandroidsdk.utils.patternedtextwatcher.PatternedTextWatcher;


public class BukoliEditText extends AppCompatEditText {

    public BukoliEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public BukoliEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public BukoliEditText(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {

        this.addTextChangedListener(new PatternedTextWatcher.Builder("0 (###) ### ## ##")
                .fillExtraCharactersAutomatically(true)
                .deleteExtraCharactersAutomatically(true)
                .debug(true)
                .build());
    }


    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        //on selection move cursor to end of text
        setSelection(this.length());
    }

    public void setListener() {

    }


    public String getOnlyNumberText() {
        return removeFirst(getText().toString().replace("(", "").replace(")", "").replace(" ", ""));
    }

    public String removeFirst(String s) {
        if(TextUtils.isEmpty(s)){
            return "";
        }
        return s.substring(1);
    }
}
