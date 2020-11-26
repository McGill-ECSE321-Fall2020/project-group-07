package ca.mcgill.ecse321.onlinegallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomerActivity extends AppCompatActivity {
    public static final String TAG = "CustomerActivity";
    public static final String BACKEND = "https://onlinegallery-backend-g7.herokuapp.com";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BACKEND)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RegistrationBackend backendInterface = retrofit.create(RegistrationBackend.class);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regcustomer);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }
    public void yesCustomer(View v){

        registerAsCustomer();
    }
    public void noCustomer(View v) {

        openArtistRegistration();
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

