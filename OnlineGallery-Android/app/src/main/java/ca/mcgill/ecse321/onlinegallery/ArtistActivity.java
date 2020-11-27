package ca.mcgill.ecse321.onlinegallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import ca.mcgill.ecse321.onlinegallery.dto.GalleryRegistrationDto;
import ca.mcgill.ecse321.onlinegallery.dto.ProfileDto;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ArtistActivity extends AppCompatActivity {
    private static final String TAG = "ArtistActivity";
    private static final String BACKEND = "https://onlinegallery-backend-g7.herokuapp.com";
    private String username;
    private String url;
    private int numSold;
    private double totalEarnings;
    private String selfDescription;
    private double rating;
    private Long artistId;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BACKEND)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    BackendInterface backendInterface = retrofit.create(BackendInterface.class);

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
                    public void onNext(@NonNull GalleryRegistrationDto gDto) {
                        Log.e(TAG, "onNext: "+username.toString());
                        artistId = gDto.getArtistId();
                        createProfile();
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: "+e.getLocalizedMessage());

                    }
                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void createProfile(){
        ProfileDto dto = new ProfileDto();
        url = "https://og-img-repo.s3.amazonaws.com/profile-placholder";
        numSold = 0;
        totalEarnings = 0;
        selfDescription = "self description placeholder";
        rating = 3;

        dto.setNumSold(numSold);
        dto.setProfileId(artistId);
        dto.setRating(rating);
        dto.setSelfDescription(selfDescription);
        dto.setUrl(url);
        dto.setUsername(username);

        Observable<ProfileDto> createProfileCall=backendInterface.createProfile(dto);
        createProfileCall
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ProfileDto>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @Override
                    public void onNext(@NonNull ProfileDto pDto) {
                        Log.e(TAG, "onNext: "+dto.toString());
                        openMainPage();
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                            Log.e(TAG, "onError: "+e.getLocalizedMessage());

                    }
                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void noArtist(View v) {

        openMainPage();

    }

    public void openMainPage() {


    }
}
