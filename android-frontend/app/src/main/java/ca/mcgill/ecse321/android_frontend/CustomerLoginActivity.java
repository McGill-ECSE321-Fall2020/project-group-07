package ca.mcgill.ecse321.android_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.mcgill.ecse321.onlinegallery.dto.CustomerDto;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomerLoginActivity extends AppCompatActivity {
    private static final String TAG="MainActivity";
    private static final String BACKEND="https://onlinegallery-backend-g7.herokuapp.com";
    Button loginButton;
    EditText usernameInput;
    EditText passwordInput;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BACKEND)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    CustomerLoginBackend backendInterface = retrofit.create(CustomerLoginBackend.class);

    /**
     * initiates the Activity, retrieves Serialized values passed to it by previous activities
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);

        loginButton = findViewById(R.id.loginButton);

    }

    /**
     * backend call that returns Customer dto on login click
     *
     * @param view
     */

    public void Login(View view){

        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        Observable<CustomerDto> getCustomerCall = backendInterface.getCustomerByUsername(username);

        getCustomerCall
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CustomerDto>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CustomerDto p) {
                        Log.e(TAG, p.toString() );

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, e.getMessage() );

                    }

                    @Override
                    public void onComplete() {

                    }

                });

    }

    public void Back(View view){
        Intent artistIntent = new Intent(this, ArtistLoginActivity.class);
        startActivity(artistIntent);
    }


}
