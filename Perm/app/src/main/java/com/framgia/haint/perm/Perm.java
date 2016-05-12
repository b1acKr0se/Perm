package com.framgia.haint.perm;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Perm {
    private static List<PermissionModel> permissionModels = new ArrayList<>();

    private Perm() {
    }

    public static boolean isPermissionGranted(Activity activity, String permission) {
        return ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity,
                permission);
    }

    public static void requestPermission(Activity activity, String[] permission, PermissionCallback callback) {
        if (callback == null)
            return;
        PermissionModel model = new PermissionModel(Arrays.asList(permission), callback);
        permissionModels.add(model);
        ActivityCompat.requestPermissions(activity, permission, model.getRequestCode());

    }

    public static void onRequestPermissionResult(int requestCode, int[] grantResult) {
        for (PermissionModel model : permissionModels) {
            if (model.getRequestCode() == requestCode) {
                if (validatePermission(grantResult)) {
                    model.getCallback().onPermissionGranted();
                } else {
                    model.getCallback().onPermissionDenied();
                }
                permissionModels.remove(model);
                break;
            }
        }
    }

    private static boolean validatePermission(int[] grantResult) {
        for (int result : grantResult) {
            if (result != PackageManager.PERMISSION_GRANTED) return false;
        }
        return true;
    }
}
