package com.kmgrv.pickerlib.util;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import com.kmgrv.pickerlib.dialog.DialogUploadPhoto;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class ImageGalleryHelper {

    public static ImageGalleryHelper INSTANCE = null;

    private Context context;


    public static ImageGalleryHelper getInstance(){
        if(INSTANCE==null){
            INSTANCE = new ImageGalleryHelper();
        }
        return INSTANCE;
    }

    private String base64Image;

    public ImageGalleryHelper setContext(Context context) {
        this.context = context;
        return INSTANCE;
    }

    public String getBase64Image() {
        return base64Image;
    }


    public Bitmap getPhotoBitmap(Intent data) {
        Bitmap bitmap = null;
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = null;

        cursor = context.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);


        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);

        cursor.close();

        File imgFile = new File(picturePath);

        bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

        Bitmap bm = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        bm = Bitmap.createScaledBitmap(bm, 300, 300, false);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        base64Image = Base64.encodeToString(b, Base64.DEFAULT);

        return bitmap;


    }
}
