package ca.mcgill.ecse321.retrofit_rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;

public class UploadActivity extends AppCompatActivity {

    private static final String TAG = "UploadActivity";
    Bitmap img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);


        img = BitmapFactory.decodeFile("/storage/emulated/0/DCIM/sunrise.jpg");
//        Log.e(TAG, "onCreate: "+img.getWidth());
//        Log.e(TAG, "onCreate: "+img.getHeight());
    }


    public void resize(View view){

    }

    public void upload(View view){

    }
}