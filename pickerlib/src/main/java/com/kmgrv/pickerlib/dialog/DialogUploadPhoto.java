package com.kmgrv.pickerlib.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.kmgrv.pickerlib.R;
import com.kmgrv.pickerlib.databinding.DialogUploadPhotoBinding;
import com.kmgrv.pickerlib.handlers.MediaHandler;
import com.kmgrv.pickerlib.util.OpenCamera;
import com.kmgrv.pickerlib.util.OpenGallery;

public class DialogUploadPhoto extends BottomSheetDialogFragment implements View.OnClickListener {

    private DialogUploadPhotoBinding binding;

    private Activity activity = null;
    private String heading = null;
    private MediaHandler mediaHandler;

    private DialogUploadPhoto(DialogBuilder builder) {
        this.activity = builder.activity;
        this.heading = builder.heading;
        this.mediaHandler = builder.mediaHandler;
    }



    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style){
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
            OpenCamera openCamera = new OpenCamera(activity);
            mediaHandler.setOpenCamera(openCamera);
            dismiss();
        } else if (id == R.id.open_gallery) {
            OpenGallery openGallery;
               /* if(fragment!=null){
                    openGallery = new OpenGallery(fragment);
                }else{
                    openGallery = new OpenGallery(activity);
                }*/

            openGallery = new OpenGallery(activity);

            mediaHandler.setOpenGallery(openGallery);

            dismiss();
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

        public DialogBuilder setMediaHandler(MediaHandler mediaHandler){
            this.mediaHandler = mediaHandler;
            return this;
        }

        public DialogUploadPhoto build() {
            DialogUploadPhoto dialogUploadPhoto = new DialogUploadPhoto(this);
            return dialogUploadPhoto;
        }
    }
}
