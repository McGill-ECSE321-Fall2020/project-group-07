package ca.mcgill.ecse321.retrofit_rxjava;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="MainActivity";
    private static final String API_ROOT="https://onlinegallery-backend-g7.herokuapp.com";

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void startPurchaseHistory(View view){
        Intent purchaseIntent = new Intent(MainActivity.this, PurchaseDetailActivity.class);
        startActivity(purchaseIntent);
    }
}
