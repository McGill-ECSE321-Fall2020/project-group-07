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

    /**
     * instantiates the app
     * @param savedInstanceState
     */
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    /**
     * onClick method of the "Discover Artworks" button, starts the DiscoverActivity
     * @param view
     */
    @SuppressLint("CheckResult")
    public void startDiscover(View view){
        Intent discoverIntent = new Intent(MainActivity.this, DiscoverActivity.class);
        startActivity(discoverIntent);
    }

    /**
     * onClick method of the "View Artists" button, starts the BrowseArtistsActivity
     * @param view
     */
    public void browseArtists(View view){
        Intent artistIntent = new Intent(MainActivity.this,BrowseArtistsActivity.class);
        startActivity(artistIntent);
    }

    /**
     * onClick method of the "Upload Artwork" button, starts the UploadActivity
     * @param view
     */
    public void startUpload(View view){
        Intent uploadIntent = new Intent(MainActivity.this,ArtistLoginActivity.class);
        startActivity(uploadIntent);
    }

    public void register(View view){
        Intent intent = new Intent(MainActivity.this,RegistrationActivity.class);
        startActivity(intent);
    }

    public void viewPurchases(View view){
        Intent purchaseIntent = new Intent(MainActivity.this,CustomerLoginActivity.class);
        purchaseIntent.putExtra("TYPE","viewPurchase");
        startActivity(purchaseIntent);
    }
}
