package com.kmgrv.pickerlib.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.kmgrv.pickerlib.R;
import com.kmgrv.pickerlib.bean.ImageHolderBean;
import com.kmgrv.pickerlib.databinding.DialogUploadPhotoBinding;
import com.kmgrv.pickerlib.handlers.MediaHandler;
import com.kmgrv.pickerlib.util.ImageGalleryHelper;
import com.kmgrv.pickerlib.util.OpenCamera;
import com.kmgrv.pickerlib.util.OpenGallery;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class DialogUploadPhoto extends BottomSheetDialogFragment implements View.OnClickListener {

    private DialogUploadPhotoBinding binding;


    private static String GALLERY = "GALLERY";
    private static String CAMERA = "CAMERA";

    private static String ACCESS = null;


    private String base64Image;

    private Activity activity = null;
    private String heading = null;
    private MediaHandler mediaHandler;

    private OpenGallery openGallery;
    private OpenCamera openCamera;

    private Bitmap bitmap;

    private DialogUploadPhoto(DialogBuilder builder) {
        this.activity = builder.activity;
        this.heading = builder.heading;
        this.mediaHandler = builder.mediaHandler;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.activity = getActivity();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        LayoutInflater layoutInflater =
                LayoutInflater.from(dialog.getContext());
        binding = DialogUploadPhotoBinding.inflate(layoutInflater);
        dialog.setContentView(binding.getRoot());

        binding.openCamera.setOnClickListener(this);
        binding.openGallery.setOnClickListener(this);

        binding.heading.setText(heading);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.open_camera) {
            ACCESS = CAMERA;
            openCamera = new OpenCamera(this);

        } else if (id == R.id.open_gallery) {
            ACCESS = GALLERY;


            openGallery = new OpenGallery(this);

        }

    }


    public static class DialogBuilder {
        private Activity activity = null;
        private String heading = null;
        private MediaHandler mediaHandler;

        public DialogBuilder(Activity activity, String heading) {
            this.activity = activity;
            this.heading = heading;
        }

        public DialogBuilder setMediaHandler(MediaHandler mediaHandler) {
            this.mediaHandler = mediaHandler;
            return this;
        }

        public DialogUploadPhoto build() {
            DialogUploadPhoto dialogUploadPhoto = new DialogUploadPhoto(this);
            return dialogUploadPhoto;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (ACCESS != null) {
            if (ACCESS == CAMERA) {
                if (resultCode == RESULT_OK) {
                    // successfully captured the image
                    // display it in image view
                    bitmap = openCamera.previewCapturedImage();
                    base64Image = openCamera.getBase64Image();
                    mediaHandler.getImageHolderBean(new ImageHolderBean(bitmap, base64Image));

                    dismissPicker();


                } else if (resultCode == RESULT_CANCELED) {
                    // user cancelled Image capture

                    Toast.makeText(activity,
                            "User cancelled image capture", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    // failed to capture image
                    Toast.makeText(activity,
                            "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                            .show();
                }


            } else if (data != null) {

                ImageGalleryHelper imageGalleryHelper = ImageGalleryHelper.getInstance().setContext(this.getActivity());
                bitmap = imageGalleryHelper.getPhotoBitmap(data);
                base64Image = ImageGalleryHelper.getInstance().getBase64Image();
                mediaHandler.getImageHolderBean(new ImageHolderBean(bitmap, base64Image));

                dismissPicker();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (ACCESS != null) {
            if (ACCESS == CAMERA) {
                openCamera.getPhoto();
            } else {
                openGallery.getPhotoGalleryPermission();
            }
        }


    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commitAllowingStateLoss();
        } catch (IllegalStateException e) {
            Log.d("ABSDIALOGFRAG", "Exception", e);
        }
    }


    public void dismissPicker() {
        ACCESS = null;
        dismissAllowingStateLoss();
    }

}
