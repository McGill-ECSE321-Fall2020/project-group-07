package ca.mcgill.ecse321.android_frontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.android_frontend.dto.ArtistDto;
import ca.mcgill.ecse321.android_frontend.dto.ArtworkDto;
import ca.mcgill.ecse321.android_frontend.dto.GalleryRegistrationDto;
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

public class ConfirmUploadActivity extends AppCompatActivity {

    private static final String TAG = "ConfirmUploadActivity";
    private static final String BACKEND = "https://onlinegallery-backend-g7.herokuapp.com";
    private static final String AWS = "https://og-img-repo.s3.us-east-1.amazonaws.com";

    private String username;
    private String firstLast;
    private Long artistId;
    private String awsUrl;
    private Bitmap profilePic;
    private byte[] byteArray;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BACKEND)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    BackendInterface backendInterface = retrofit.create(BackendInterface.class);

    Retrofit retrofitAWS = new Retrofit.Builder()
            .baseUrl(AWS)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    BackendInterface awsInterface = retrofitAWS.create(BackendInterface.class);

    /**
     * initiates the Activity, retrieves Serialized values passed to it by previous activities
     * and sets various TextViews to corresponding information. Aslo instantiates the createPurchase()
     * function
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_upload);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i = getIntent();
        username=(String) i.getSerializableExtra("USERNAME");
        Log.e(TAG, "onCreate: "+username );

    }

    /**
     * onClick method associated with the "View My Portfolio" button after upload complete
     * takes the uploading artist to his/her portfolio page where they can see the newly uploaded
     * image at the top
     *
     * @param view
     */
    public void viewArtworks(View view){
        fetchRegistration();
    }

    /**
     * onClick method associated with the "Return Home" button after upload complete
     * takes the uploading artist back to app home page
     *
     * @param view
     */
    public void returnHome(View view){
        Intent intent = new Intent(ConfirmUploadActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * retrieves the username, first last names, and the profile picture of the uploading artist
     * for starting the ArtistPageActivity that would display the artist's artworks
     */
    public void fetchRegistration(){
        Observable<GalleryRegistrationDto> call = backendInterface.getRegistration(username);
        call
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Observer<GalleryRegistrationDto>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull GalleryRegistrationDto galleryRegistrationDto) {
                Log.e(TAG, "onNext: "+galleryRegistrationDto.toString() );
                firstLast=galleryRegistrationDto.getFirstName()+" "+galleryRegistrationDto.getLastName();
                artistId=galleryRegistrationDto.getArtistId();
                fetchAwsUrl();
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * fetches the Base64 representation of the artist's profile image
     */
    public void fetchAwsUrl(){
        Observable<ArtistDto> call = backendInterface.getArtist(artistId);
        call
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Observer<ArtistDto>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ArtistDto artistDto) {
                Log.e(TAG, "onNext: "+artistDto.toString() );
                awsUrl=artistDto.getUrl().split(".com/")[1];
                getProfilePic();
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * converts the Base64 profile pic to a Bitmap and then a byte[]
     * starts the RecyclerView upon success
     */
    public void getProfilePic(){
        Observable<String> call = awsInterface.getImgEncoding(awsUrl);
        call
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                profilePic=Helpers.Base64ToBitmap(s);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                profilePic.compress(Bitmap.CompressFormat.PNG,100,stream);
                byteArray=stream.toByteArray();
                startRecyclerView();
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * starts the RecyclerView for this artist
     */
    public void startRecyclerView() {
        Intent intent = new Intent(ConfirmUploadActivity.this,ArtistPageActivity.class);
        intent.putExtra("USERNAME",username);
        intent.putExtra("ARTISTID",artistId);
        intent.putExtra("FIRSTLAST",firstLast);
        intent.putExtra("PROFILEIMG",byteArray);

        startActivity(intent);
    }
}