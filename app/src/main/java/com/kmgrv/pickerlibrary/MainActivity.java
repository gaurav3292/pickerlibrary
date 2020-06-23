package com.kmgrv.pickerlibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import com.kmgrv.pickerlib.bean.ImageHolderBean;
import com.kmgrv.pickerlib.databinding.DialogUploadPhotoBinding;
import com.kmgrv.pickerlib.dialog.DialogUploadPhoto;
import com.kmgrv.pickerlib.handlers.MediaHandler;
import com.kmgrv.pickerlibrary.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MediaHandler {

    private ActivityMainBinding binding;
    private DialogUploadPhoto dialogUploadPhoto;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.imageView.setOnClickListener(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.imageView:

                dialogUploadPhoto = new DialogUploadPhoto.DialogBuilder(this,"Upload Profile Photo")
                        .setMediaHandler(this)
                        .build();
                dialogUploadPhoto.show(getSupportFragmentManager(), dialogUploadPhoto.getTag());
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        dialogUploadPhoto.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        dialogUploadPhoto.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void getImageHolderBean(ImageHolderBean imageHolderBean) {
        binding.imageView.setImageBitmap(imageHolderBean.getImageBitmap());

    }
}
