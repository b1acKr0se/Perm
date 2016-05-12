package com.framgia.haint.perm;

import android.Manifest;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PermissionCallback {
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.container);
        findViewById(R.id.btn_request_camera).setOnClickListener(this);
        findViewById(R.id.btn_request_contact).setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Perm.onRequestPermissionResult(requestCode, grantResults);
    }

    @Override
    public void onPermissionGranted() {
        Toast.makeText(this, "All permissions granted", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onShowingRationale() {

    }

    @Override
    public void onPermissionDenied() {
        Toast.makeText(this, "One or more permissions have been denied", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_request_contact:
                if (Perm.isPermissionGranted(this, Manifest.permission.READ_CONTACTS)
                        && Perm.isPermissionGranted(this, Manifest.permission.WRITE_CONTACTS))
                    Toast.makeText(this, "Contact permissions have already been granted.", Toast.LENGTH_LONG).show();
                else
                    requestContactPermission();
                break;
            case R.id.btn_request_camera:
                if (Perm.isPermissionGranted(this, Manifest.permission.CAMERA))
                    Toast.makeText(this, "Camera permission has already been granted.", Toast.LENGTH_LONG).show();
                else
                    requestCameraPermission();
                break;
        }
    }

    private void requestContactPermission() {
        if (Perm.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS) || Perm.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_CONTACTS))
            Snackbar.make(findViewById(R.id.container), R.string.permission_contacts_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Perm.requestPermission(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, MainActivity.this);
                        }
                    })
                    .show();
        else
            Perm.requestPermission(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, MainActivity.this);
    }

    private void requestCameraPermission() {
        if (Perm.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))
            Snackbar.make(findViewById(R.id.container), R.string.permission_camera_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Perm.requestPermission(MainActivity.this, new String[]{Manifest.permission.CAMERA}, MainActivity.this);
                        }
                    })
                    .show();
        else
            Perm.requestPermission(MainActivity.this, new String[]{Manifest.permission.CAMERA}, MainActivity.this);

    }
}
