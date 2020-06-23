package com.kmgrv.pickerlib.bean;

import android.graphics.Bitmap;

public class ImageHolderBean  {

    private Bitmap imageBitmap;
    private String base64Image;

    public ImageHolderBean(Bitmap imageBitmap, String base64Image) {
        this.imageBitmap = imageBitmap;
        this.base64Image = base64Image;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }
}
