package ca.mcgill.ecse321.onlinegallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerActivity extends AppCompatActivity {
    public static final String TAG = "CustomerActivity";
    public static final String API_ROOT = "https://onlinegallery-backend-g7.herokuapp.com";
    public Button yesButton;
    public Button noButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regcustomer);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        yesButton = findViewById(R.id.customerYesButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerAsCustomer();
            }
        });
        noButton = findViewById(R.id.customerNoButton);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openArtistRegistration();

            }
        });
    }

    public void registerAsCustomer() {
        //do stuff
        openArtistRegistration();

    }
    public void openArtistRegistration() {
        Intent artistIntent = new Intent(this, ArtistActivity.class);
        startActivity(artistIntent);
    }
}

