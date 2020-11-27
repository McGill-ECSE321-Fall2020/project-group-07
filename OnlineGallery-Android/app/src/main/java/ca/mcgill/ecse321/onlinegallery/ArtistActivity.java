package ca.mcgill.ecse321.onlinegallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ca.mcgill.ecse321.onlinegallery.dto.GalleryRegistrationDto;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArtistActivity extends AppCompatActivity {
    public static final String TAG = "ArtistActivity";
    public static final String BACKEND = "https://onlinegallery-backend-g7.herokuapp.com";
    public String username;
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
        Intent intent=getIntent();
        username = (String) intent.getSerializableExtra("USERNAME");
    }
    public void yesArtist(View v) {

        Observable<GalleryRegistrationDto> createArtistCall=backendInterface.setArtist(username);

        createArtistCall
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GalleryRegistrationDto>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @Override
                    public void onNext(@NonNull GalleryRegistrationDto dto) {
                        Log.e(TAG, "onNext: "+username.toString());

                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: "+e.getLocalizedMessage());

                    }
                    @Override
                    public void onComplete() {

                    }
                });
        openMainPage();
    }

    public void noArtist(View v) {

        openMainPage();

    }

    public void openMainPage() {

    }
}

