package com.alphago.alphago.util;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by su_me on 2018-02-04.
 */

public class PermissionUtils {
    public static void checkPermissions(AppCompatActivity activity, int requestCode, String... permissions) {
        ArrayList<String> needPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED) {
                needPermissions.add(permission);
            }
        }

        if (!needPermissions.isEmpty()) {
            String[] array = new String[needPermissions.size()];
            needPermissions.toArray(array);
            ActivityCompat.requestPermissions(activity, array, requestCode);
        }
    }
}
