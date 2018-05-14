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
                return R.drawable.tmp_bed;
            case 3:
                return R.drawable.tmp_apple;
        }
        return R.mipmap.ic_launcher;
    }

    public static int getCollectionImage(long category) {
        switch ((int) category) {
            case 1:
                return R.drawable.a_img_collect_anm;
            case 2:
                return R.drawable.b_img_collect_fur;
            case 3:
                return R.drawable.c_img_collect_food;
            case 4:
                return R.drawable.d_img_collect_sch;
            case 5:
                return R.drawable.e_img_collect_kch;
            case 6:
                return R.drawable.g_img_collect_elec;
            case 7:
                return R.drawable.f_img_collect_bth;
            case 8:
                return R.drawable.h_img_collect_room;
            default:
                return R.mipmap.ic_launcher;
        }
    }
}
