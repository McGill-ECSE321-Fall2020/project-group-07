package ca.mcgill.ecse321.android_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="MainActivity";
    private static final String API_ROOT="https://onlinegallery-backend-g7.herokuapp.com";

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @SuppressLint("CheckResult")
    public void startDiscover(View view){
        Intent discoverIntent = new Intent(MainActivity.this, DiscoverActivity.class);
        startActivity(discoverIntent);
    }

    public void browseArtists(View view){
        Intent artistIntent = new Intent(MainActivity.this,BrowseArtistsActivity.class);
        startActivity(artistIntent);
    }

    public void startUpload(View view){
        Intent uploadIntent = new Intent(MainActivity.this,UploadActivity.class);
        startActivity(uploadIntent);
    }
}
