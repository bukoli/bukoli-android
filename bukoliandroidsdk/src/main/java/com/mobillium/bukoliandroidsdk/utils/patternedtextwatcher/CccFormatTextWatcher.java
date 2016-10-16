package com.mobillium.bukoliandroidsdk.utils.patternedtextwatcher;

import android.text.Editable;
import android.text.TextWatcher;

@Deprecated
public class CccFormatTextWatcher implements TextWatcher {

  private static final char SPACE = ' ';

  // The maximum length of the CCC.
  private static final int MAX_LENGTH = 20 + 3;

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
  }

  @Override
  public void afterTextChanged(Editable s) {
    StringBuilder sb = new StringBuilder(s);

    for (int i = sb.length() - 1; i >= 0; i--) {
      char c = sb.charAt(i);
      if (SPACE == c) {
        sb.delete(i, i + 1);
      }
    }

    if (sb.length() > 4) {
      int i = 4;
      int j;

      while (i < sb.length()) {
        j = i + 1;

        // Don't let the user insert more symbols than the maximum allowed.
        if (i >= MAX_LENGTH) {
          sb.delete(i, j);
        }

        // Space must be inserted at these places.
        if (j == 5 | j == 10 | j == 13) {
          sb.insert(i, String.valueOf(SPACE));
        }
        i++;
      }
    }

    if (!s.toString().equals(sb.toString())) {
      s.replace(0, s.length(), sb);
    }
  }
}
