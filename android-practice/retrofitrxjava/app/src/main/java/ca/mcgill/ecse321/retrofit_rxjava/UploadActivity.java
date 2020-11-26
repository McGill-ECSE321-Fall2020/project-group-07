package ca.mcgill.ecse321.retrofit_rxjava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class UploadActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST = 0;
    private static final int RESULT_LOAD_IMAGE=0;
//    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MMddYYYY_HHmmss");

    private static final String TAG = "UploadActivity";

    private String imgEncoding;
    private String awsUrl;
    private String username;

    Bitmap img;
    Bitmap scaledImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        username="hansolo";             //this would be replaced by the artist login page before

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String [] {Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST);
        }

        Intent intent = new Intent (Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,RESULT_LOAD_IMAGE);
    }

    public void resizeImg() {
        double originalWidth = img.getWidth();
        double originalHeight = img.getHeight();

        double sf = 600.0 / originalWidth;

        int newWidth = 600;
        int newHeight = (int) Math.round(sf * originalHeight);

        scaledImg = Bitmap.createScaledBitmap(img, newWidth, newHeight, false);

        Timestamp ts = new Timestamp(System.currentTimeMillis());
        awsUrl=username+"_"+sdf.format(ts)+"_600"+"_"+((Integer) newHeight).toString();

        Log.e(TAG, "resizeImg: "+awsUrl );
        encode();
    }

    public void encode(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        scaledImg.compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream);
        imgEncoding= "data:image/png;base64,"+Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSION_REQUEST:
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Log.e(TAG, "onRequestPermissionsResult: "+"permission granted");
                }else{
                    Log.e(TAG, "onRequestPermissionsResult: "+"permission denied");
                    finish();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_LOAD_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    img=BitmapFactory.decodeFile(picturePath);
                    resizeImg();
                }
        }
    }
}