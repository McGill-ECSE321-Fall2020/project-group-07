package ca.mcgill.ecse321.onlinegallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import ca.mcgill.ecse321.onlinegallery.dto.GalleryRegistrationDto;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import io.reactivex.Observable;

import org.json.JSONObject;

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

