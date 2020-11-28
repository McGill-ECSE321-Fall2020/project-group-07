package ca.mcgill.ecse321.android_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import ca.mcgill.ecse321.android_frontend.dto.CustomerDto;
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

public class CustomerLoginActivity extends AppCompatActivity {
    private static final String TAG="MainActivity";
    private static final String BACKEND="https://onlinegallery-backend-g7.herokuapp.com";
    private String type;
    TextView msgView;
    Button loginButton;
    EditText usernameInput;
    EditText passwordInput;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BACKEND)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    BackendInterface backendInterface = retrofit.create(BackendInterface.class);

    /**
     * initiates the Activity, retrieves Serialized values passed to it by previous activities
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        msgView=findViewById(R.id.loginMsg);
        loginButton = findViewById(R.id.loginButton);

        Intent i = getIntent();
        type = (String) i.getSerializableExtra("TYPE");

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
                        if (type.equals("viewPurchase")) {
                            Intent intent = new Intent(CustomerLoginActivity.this, PurchaseDetailActivity.class);
                            intent.putExtra("USERNAME", p.getUsername());
                            startActivity(intent);
                        }
                        else if (type.equals("startCheckout")){
                            Intent passedIntent = getIntent();
                            String artworkId = (String) passedIntent.getSerializableExtra("ARTWORKID");
                            Double total = (Double) passedIntent.getSerializableExtra("TOTAL");
                            String dest = (String) passedIntent.getSerializableExtra("DEST");
                            Double shipcost = (Double) passedIntent.getSerializableExtra("SHIPCOST");
                            String recipient = (String) passedIntent.getSerializableExtra("RECIPIENT");
                            String shiptype=(String) passedIntent.getSerializableExtra("SHIPTYPE");


                            Intent intent = new Intent(view.getContext(),CheckoutActivity.class);
                            intent.putExtra("ARTWORKID",artworkId);
                            intent.putExtra("DEST",dest);
                            intent.putExtra("SHIPCOST",shipcost);
                            intent.putExtra("TOTAL",total);
                            intent.putExtra("RECIPIENT",recipient);
                            intent.putExtra("SHIPTYPE",shiptype);
                            intent.putExtra("USERNAME",p.getUsername());
                            intent.putExtra("CUSTOMERID",p.getCustomerId());
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, e.getMessage() );
                        if (e instanceof HttpException){
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            try {
                                msgView.setText(body.string());
                            } catch (IOException ioException) {
                                msgView.setText("unknown error, try again later");
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
