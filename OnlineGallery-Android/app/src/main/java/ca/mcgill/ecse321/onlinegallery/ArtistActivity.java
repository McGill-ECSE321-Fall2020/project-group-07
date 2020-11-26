package ca.mcgill.ecse321.onlinegallery;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArtistActivity extends AppCompatActivity {
    public static final String TAG = "ArtistActivity";
    public static final String BACKEND = "https://onlinegallery-backend-g7.herokuapp.com";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BACKEND)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RegistrationBackend backendInterface = retrofit.create(RegistrationBackend.class);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regartist);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    public void yesArtist(View v) {

        registerAsArtist();
    }

    public void noArtist(View v) {

        openMainPage();

    }

    public void registerAsArtist() {
        //do stuff
        openMainPage();

    }
    public void openMainPage() {

    }
}

