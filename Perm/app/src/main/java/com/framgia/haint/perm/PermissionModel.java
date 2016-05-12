package com.framgia.haint.perm;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PermissionModel {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int requestCode;
    private List<String> permissionList;
    private PermissionCallback callback;

    public PermissionModel(List<String> permissionList, PermissionCallback callback) {
        this.requestCode = count.incrementAndGet();
        this.callback = callback;
        this.permissionList = permissionList;
    }

    public int getRequestCode() {
        return this.requestCode;
    }

    public List<String> getPermissionList() {
        return this.permissionList;
    }

    public PermissionCallback getCallback() {
        return this.callback;
    }
}
