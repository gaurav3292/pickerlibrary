package com.kmgrv.pickerlib.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;


public class RotateImage {


    Bitmap bitmap;
    private String filePath;
    private Activity activity;
    private Uri uri;

    public RotateImage(Bitmap bitmap, String filePath, Activity activity, Uri fileUri) {
        this.bitmap = bitmap;
        this.filePath = filePath;
        this.activity = activity;
        this.uri = fileUri;
    }

    public Bitmap rotateImage(){
        ExifInterface ei = null;
        try {
            activity.getContentResolver().notifyChange(uri,null);
            File imageFileUrl = new File(filePath);
            ei = new ExifInterface(imageFileUrl.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        switch(orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
               return newRotatedImage(bitmap, 90);


            case ExifInterface.ORIENTATION_ROTATE_180:
                return newRotatedImage(bitmap, 180);


            case ExifInterface.ORIENTATION_ROTATE_270:
                return  newRotatedImage(bitmap, 270);


            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
        }

        return bitmap;
    }

    public Bitmap newRotatedImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public Bitmap decodeFile() {

        int orientation;

        try {

            if(filePath==null){

                return null;
            }
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 4;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale++;
            }
            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            Bitmap bm = BitmapFactory.decodeFile(filePath,o2);


            Bitmap bitmap = bm;

            ExifInterface exif = new ExifInterface(filePath);
            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            Log.e("orientation",""+orientation);
            Matrix m=new Matrix();

            if((orientation==3)){

                m.postRotate(180);
                m.postScale((float)bm.getWidth(), (float)bm.getHeight());

//               if(m.preRotate(90)){
                Log.e("in orientation",""+orientation);

                bitmap = Bitmap.createBitmap(bm, 0, 0,bm.getWidth(),bm.getHeight(), m, true);
                return  bitmap;
            }
            else if(orientation==6){

                m.postRotate(90);

                Log.e("in orientation",""+orientation);

                bitmap = Bitmap.createBitmap(bm, 0, 0,bm.getWidth(),bm.getHeight(), m, true);
                return  bitmap;
            }

            else if(orientation==8){

                m.postRotate(270);

                Log.e("in orientation",""+orientation);

                bitmap = Bitmap.createBitmap(bm, 0, 0,bm.getWidth(),bm.getHeight(), m, true);
                return  bitmap;
            }
            return bitmap;
        }
        catch (Exception e) {
        }
        return null;
    }
}
