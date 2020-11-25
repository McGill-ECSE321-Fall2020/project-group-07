package ca.mcgill.ecse321.onlinegallery;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ArtistActivity extends AppCompatActivity {
    public static final String TAG = "ArtistActivity";
    public static final String API_ROOT = "https://onlinegallery-backend-g7.herokuapp.com";
    public Button yesButton;
    public Button noButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regartist);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        yesButton = findViewById(R.id.artistYesButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerAsArtist();
            }
        });
        noButton = findViewById(R.id.artistNoButton);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openMainPage();

            }
        });
    }

    public void registerAsArtist() {
        //do stuff
        openMainPage();

    }
    public void openMainPage() {

    }
}

