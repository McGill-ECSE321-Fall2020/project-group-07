package ca.mcgill.ecse321.android_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import java.io.IOException;

import ca.mcgill.ecse321.android_frontend.dto.ArtistDto;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArtistLoginActivity extends AppCompatActivity  {

    private static final String TAG="ArtistLoginActivity";
    private static final String BACKEND="https://onlinegallery-backend-g7.herokuapp.com";


    Button loginButton;
    EditText usernameInput;
    EditText passwordInput;
    TextView errorView;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BACKEND)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    BackendInterface backendInterface = retrofit.create(BackendInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_login);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        errorView=findViewById(R.id.artist_login_error_msg);
        loginButton = findViewById(R.id.loginButton);

    }
    public void Login(View view){

        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        Observable<ArtistDto> getArtistCall = backendInterface.getArtistByUsername(username);

        getArtistCall
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ArtistDto>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ArtistDto p) {
                        Log.e(TAG, p.toString() );

                        Intent intent = new Intent(ArtistLoginActivity.this,UploadActivity.class);
                        intent.putExtra("USERNAME",p.getUsername());
                        startActivity(intent);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, e.getMessage() );
                        if (e instanceof HttpException){
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            try {
                                errorView.setText(body.string());
                            } catch (IOException ioException) {
                                errorView.setText("unknown error, try again later");
                            }
                        }
                        return;
                    }

                    @Override
                    public void onComplete() {

                    }

                });

    }
}
