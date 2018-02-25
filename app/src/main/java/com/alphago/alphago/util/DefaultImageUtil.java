package com.alphago.alphago.util;

import com.alphago.alphago.R;

/**
 * Created by su_me on 2018-02-25.
 */

public class DefaultImageUtil {
    public static int getCategoryImage(long category) {
        switch ((int) category) {
            case 1:
                return R.drawable.tmp_dog;
            case 2:
                return R.drawable.tmp_apple;
            case 3:
                return R.drawable.tmp_bed;
            case 4:
                return R.drawable.tmp_tomato;
        }
        return -1;
    }
}
