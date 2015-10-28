package org.sssta.zeroind.util;

import android.widget.EditText;

/**
 * Created by 林韬 on 2015/10/9.
 */
public class TextUtil {

    public static boolean isEmpty(EditText editText) {
        String string = editText.getText().toString();
        if ( string == null || string.equals("")) {
            return true;
        }
        return false;
    }

    public static boolean checkPassLegal(String password) {
        if (password.length() <6 || password.length() >30) {
            return false;
        }
        return true;
    }
}
