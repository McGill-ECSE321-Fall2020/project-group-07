package ca.mcgill.ecse321.android_frontend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import ca.mcgill.ecse321.android_frontend.dto.GalleryRegistrationDto;
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

public class RegistrationActivity extends AppCompatActivity {
    private static final String TAG = "RegistrationActivity";
    private static final String BACKEND="https://onlinegallery-backend-g7.herokuapp.com";
    private TextView usernameInput;
    private TextView firstNameInput;
    private TextView lastNameInput;
    private TextView emailInput;
    private TextView passwordInput;
    private TextView errorView;
    private String username;


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BACKEND)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    BackendInterface backendInterface = retrofit.create(BackendInterface.class);
    /**
     * initiates the Activity, retrieves Serialized values passed to it by previous activities
     * initializes all the registration inputs for later use by the registration method
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        usernameInput = findViewById(R.id.usernameInput);
        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        errorView=findViewById(R.id.errorMsg);

    }
    /**
     * backend call to create a registration. Sets all required fields into the registration
     * dto, uses a Retrofit call, which upon completion calls the openCustomerRegistration() method
     *
     * @param view
     */
    public void register(View view){
        GalleryRegistrationDto dto = new GalleryRegistrationDto();
        dto.setEmail(emailInput.getText().toString().trim());
        dto.setFirstName(firstNameInput.getText().toString().trim());
        dto.setLastName(lastNameInput.getText().toString().trim());
        dto.setPassword(passwordInput.getText().toString().trim());
        dto.setUsername(usernameInput.getText().toString().trim());

        Observable<GalleryRegistrationDto> createRegistrationCall=backendInterface.createRegistration(dto);

        createRegistrationCall
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GalleryRegistrationDto>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @Override
                    public void onNext(@NonNull GalleryRegistrationDto gDto) {
                        Log.e(TAG, "onNext: "+dto.toString());
                        openCustomerRegistration();
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: "+e.getLocalizedMessage());
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
    /**
     * method that clears the inputs in the text fields for the registration forms
     *
     * @param view
     */
    public void clear(View view) {
        usernameInput.setText("");
        firstNameInput.setText("");
        lastNameInput.setText("");
        emailInput.setText("");
        passwordInput.setText("");
    }
    /**
     * method that opens the customer registration option by calling
     * CustomerRegistrationActivity using an Intent. Also stores the username value for later use
     * in other activity classes by using the putExtra method
     *
     */
    public void openCustomerRegistration(){
        Intent customerIntent = new Intent(this, CustomerRegistrationActivity.class);
        username = (usernameInput.getText().toString().trim());

        customerIntent.putExtra("USERNAME", username);

        startActivity(customerIntent);
    }
}
