package ca.mcgill.ecse321.retrofit_rxjava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import java.io.File;

public class UploadActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;

    private static final String TAG = "UploadActivity";
    Bitmap img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

    }

    public void browse(View view){
        Log.e(TAG, "browse: "+Environment.getExternalStorageDirectory());
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.getContentUri("/storage/emulated/0/Pictures"));
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }


    public void resize(View view){

    }

    public void upload(View view){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //called when image is selected from gallery

        //make sure is called from gallery
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data!= null){
            //get address of selected image
            Uri selectedImage = data.getData();
            //display it
        }
    }
}