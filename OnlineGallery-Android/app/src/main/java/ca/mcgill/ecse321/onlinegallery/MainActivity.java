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
import android.widget.TextView;

import ca.mcgill.ecse321.onlinegallery.dto.GalleryRegistrationDto;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
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

public class MainActivity extends AppCompatActivity {
    public static final String TAG="MainActivity";
    public static final String BACKEND="https://onlinegallery-backend-g7.herokuapp.com";
    public Button regButton;
    public Button clearButton;
    public TextView usernameInput;
    public TextView firstNameInput;
    public TextView lastNameInput;
    public TextView emailInput;
    public TextView passwordInput;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BACKEND)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RegistrationBackend backendInterface = retrofit.create(RegistrationBackend.class);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        usernameInput = findViewById(R.id.usernameInput);
        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);

        regButton = findViewById(R.id.registerButton);
        regButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                GalleryRegistrationDto dto = new GalleryRegistrationDto();
                dto.setEmail(emailInput.getText().toString().trim());
                dto.setFirstName(firstNameInput.getText().toString().trim());
                dto.setLastName(lastNameInput.getText().toString().trim());
                dto.setPassword(passwordInput.getText().toString().trim());
                dto.setUsername(usernameInput.getText().toString().trim());

                Observable<GalleryRegistrationDto> createRegistrationCall=RegistrationBackend.createRegistration(dto);

                createRegistrationCall
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<GalleryRegistrationDto>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull GalleryRegistrationDto purchaseDto) {

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                openCustomerRegistration();
            }
        });
        clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameInput.setText("");
                firstNameInput.setText("");
                lastNameInput.setText("");
                emailInput.setText("");
                passwordInput.setText("");
            }
        }));
    }

    public void openCustomerRegistration(){
        Intent customerIntent = new Intent(this, CustomerActivity.class);
        startActivity(customerIntent);
    }
}