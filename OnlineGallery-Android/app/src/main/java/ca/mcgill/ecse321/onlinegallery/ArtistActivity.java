package ca.mcgill.ecse321.onlinegallery;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

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

public class ArtistActivity extends AppCompatActivity {
    public static final String TAG = "ArtistActivity";
    public static final String BACKEND = "https://onlinegallery-backend-g7.herokuapp.com";
    public String username;
    public Long id;
    public String url;
    public int numSold;
    public double totalEarnings;
    public String selfDescription;
    public double rating;

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
                    public void onNext(@NonNull GalleryRegistrationDto gDto) {
                        Log.e(TAG, "onNext: "+username.toString());

                        ProfileDto dto = new ProfileDto();
                        //find way to get artist id?
                        url = "https://og-img-repo.s3.amazonaws.com/profile-placholder";
                        numSold =0;
                        totalEarnings = 0;
                        selfDescription = "self description placeholder";
                        rating = 0;

                        dto.setNumSold(numSold);
                       // dto.setProfileId();
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

