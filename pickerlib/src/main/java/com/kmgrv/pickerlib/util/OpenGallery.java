package com.kmgrv.pickerlib.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.kmgrv.pickerlib.dialog.DialogUploadPhoto;

import java.io.ByteArrayOutputStream;
import java.io.File;


public class OpenGallery {


    private Activity activity;
    private DialogUploadPhoto fragment;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 2;
    private static int RESULT_LOAD_IMAGE = 1;

    public OpenGallery(Activity activity) {

        this.activity = activity;
        getPhotoGalleryPermission();
    }

    public OpenGallery(DialogUploadPhoto fragment) {

        this.fragment = fragment;
        getPhotoGalleryPermission();
    }

    public void getPhotoGalleryPermission() {
        if (activity != null) {
            int storageCheck = ContextCompat.checkSelfPermission(fragment.getContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (storageCheck == PackageManager.PERMISSION_GRANTED) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                fragment.startActivityForResult(i, RESULT_LOAD_IMAGE);

            } else {
                fragment.requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, "photos"},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        } else {
            int storageCheck = ContextCompat.checkSelfPermission(fragment.getContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (storageCheck == PackageManager.PERMISSION_GRANTED) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                fragment.startActivityForResult(i, RESULT_LOAD_IMAGE);

            } else {
                fragment.requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, "photos"},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }


    }


}
