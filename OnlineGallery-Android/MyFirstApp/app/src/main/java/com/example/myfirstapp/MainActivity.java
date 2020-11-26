package com.example.myfirstapp;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;
    ImageView imageToUpload, downloadedImage;
    Button bUploadImage;
    Button bDownloadImage;
    EditText uploadedImageName;
    EditText uploadedImageDescription;
    EditText uploadedImageUser;
    EditText downloadedImageName;
    EditText downloadedImageId;
    private String[] permissions;
    private int requestCode;
    private static final String SERVER_ADDRESS = "https://artgalleryimagetry.000webhostapp.com/";
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageToUpload= (ImageView) findViewById(R.id.imageToUpload);
        downloadedImage= (ImageView) findViewById(R.id.downloadedImage);

        bUploadImage = (Button)findViewById(R.id.bUploadImage);
        bDownloadImage = (Button)findViewById(R.id.bDownloadImage);

        uploadedImageName = (EditText)findViewById(R.id.nameArtwork);
        uploadedImageDescription = (EditText)findViewById(R.id.descriptionArtwork);
        uploadedImageUser = (EditText)findViewById(R.id.userNameArtist);

        downloadedImageName = (EditText)findViewById(R.id.downloadedImageName);
        downloadedImageId = (EditText)findViewById(R.id.downloadedImageId);

        imageToUpload.setOnClickListener(this);
        bUploadImage.setOnClickListener(this);
        bDownloadImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //try to open gallery
        switch(v.getId()){
            //try to open gallery by intent
            case R.id.imageToUpload:
                //action pick for allowing user to select, gallery,
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //to get which pic is selected, need to give unique id
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);

                break;
            case R.id.bUploadImage:
                //get bit map of selected image
                Bitmap image = ((BitmapDrawable)imageToUpload.getDrawable()).getBitmap();
                new UploadImage(image, uploadedImageName.getText().toString()).execute();
                break;
            case R.id.bDownloadImage:
                break;
        }
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
            imageToUpload.setImageURI(selectedImage);
        }
    }

    //do on background an asynch task for connection with server
    private class UploadImage extends AsyncTask<Void, Void, Void>{

        Bitmap image;
        String name;

        public UploadImage(Bitmap image, String name){
            this.image  = image;
            this.name = name;
        }
        @Override
        protected Void doInBackground(Void... params) {
           //encode image, outputStream holds byte representation of image,
            //compress image into jpeg , compress into byte asrray stream
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            Log.i(TAG, encodedImage );


            //create list of things to be sent to server, name value pair to allow key.value
            HashMap<String, String> nameMap = new HashMap<String, String>();
            HashMap<String, String> imageMap = new HashMap<String, String>();
            nameMap.put("name", name);
            imageMap.put("image", encodedImage);
            ArrayList<HashMap> dataToSend = new ArrayList<>();
            dataToSend.add(imageMap);
            dataToSend.add(nameMap);

            //send to server
            try {
                getHttprequestParams(dataToSend);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }


    }

    private void getHttprequestParams(ArrayList<HashMap> data) throws IOException {
       // do setup of connection to server (not finalized and probably won't be used since we're using React)
        Uri.Builder b = new Uri.Builder();
        URL url = new URL (SERVER_ADDRESS);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(1000*30);
        conn.setReadTimeout(1000*30);
        for (HashMap<String, String> item : data){
            b.appendQueryParameter(item.get("name"), item.get("image"));
        }


    }


}