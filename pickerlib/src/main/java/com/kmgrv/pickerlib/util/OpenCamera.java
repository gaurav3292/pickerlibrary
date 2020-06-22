package com.kmgrv.pickerlib.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class OpenCamera {

    private Activity activity;
    private static final int MY_PERMISSIONS_REQUEST_READ_CAMERA = 1;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private Uri fileUri;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 2;
    private static final String IMAGE_DIRECTORY_NAME = "Cato's Camera";
    private String base64Image;

    public OpenCamera(Activity activity) {

        this.activity = activity;
        getPhoto();
    }

    public void getPhoto() {

        int cameraCheck = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.CAMERA);

        int storageCheck = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (cameraCheck == PackageManager.PERMISSION_GRANTED && storageCheck == PackageManager.PERMISSION_GRANTED) {

            getCamera();
        } else {
            Log.d("permission", "not granted");
            if (cameraCheck != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_READ_CAMERA);

            } else if (storageCheck != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, "camera"},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }

        }
    }

    public void getCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            fileUri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", getOutputMediaFileUri());
        }else{
            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        // start the image capture Intent
        activity.startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

    }

    private File getOutputMediaFileUri() {
        return getOutputMediaFile(MEDIA_TYPE_IMAGE);
    }

    private Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private File getOutputMediaFile(int type) {
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg ");
            Log.d("image path...", mediaFile.getAbsolutePath().toString());
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public Bitmap previewCapturedImage() {
        Bitmap bitmap = null;
        try {

            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inSampleSize = 8;

            bitmap = AppHelper.getBitmapFromUri(fileUri, activity);
            bitmap = new RotateImage(bitmap, fileUri.getPath(),activity,fileUri).rotateImage();

           // Bitmap bm = BitmapFactory.decodeFile(fileUri.getPath());
          bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, false);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

            byte[] b = baos.toByteArray();

            base64Image = Base64.encodeToString(b, Base64.DEFAULT);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


}
