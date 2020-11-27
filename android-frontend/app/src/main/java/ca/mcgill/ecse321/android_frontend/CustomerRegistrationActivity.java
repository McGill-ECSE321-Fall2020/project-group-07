package ca.mcgill.ecse321.android_frontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

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

public class CustomerRegistrationActivity extends AppCompatActivity {
    private static final String TAG = "CustomerActivity";
    private static final String BACKEND = "https://onlinegallery-backend-g7.herokuapp.com";
    private String username;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BACKEND)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    BackendInterface backendInterface = retrofit.create(BackendInterface.class);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_customer);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }
    public void yesCustomer(View v){
        Intent intent=getIntent();
        username = (String) intent.getSerializableExtra("USERNAME");
        Observable<GalleryRegistrationDto> createCustomerCall=backendInterface.setCustomer(username);

        createCustomerCall
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GalleryRegistrationDto>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @Override
                    public void onNext(@NonNull GalleryRegistrationDto dto) {
                        Log.e(TAG, "onNext: "+username.toString());
                        openArtistRegistration();
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
    public void noCustomer(View v) {

        openArtistRegistration();
    }

    public void openArtistRegistration() {
        Intent artistIntent = new Intent(this, ArtistRegistrationActivity.class);
        artistIntent.putExtra("USERNAME", username);

        startActivity(artistIntent);
    }
}
