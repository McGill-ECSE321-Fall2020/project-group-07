package ca.mcgill.ecse321.onlinegallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import ca.mcgill.ecse321.onlinegallery.dto.GalleryRegistrationDto;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
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
    public static final String API_ROOT="https://onlinegallery-backend-g7.herokuapp.com";

    @SuppressLint("CheckResult")
    public void createRegistration(View view){

    }
    @SuppressLint("CheckResult")
    public void startClear(View view){

    }
    @SuppressLint("CheckResult")
    public void goBack(View view){

    }
}