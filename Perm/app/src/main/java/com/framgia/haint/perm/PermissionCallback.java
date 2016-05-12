package com.framgia.haint.perm;

public interface PermissionCallback {
    /*
     * Perform any operation as the permission has been granted
     */
    void onPermissionGranted();

    /*
     * Show the rationale (if any) for requesting said permission
     */
    void onShowingRationale();

    /*
     * Perform any operation as the permission has been denied by the user
     */
    void onPermissionDenied();
}
