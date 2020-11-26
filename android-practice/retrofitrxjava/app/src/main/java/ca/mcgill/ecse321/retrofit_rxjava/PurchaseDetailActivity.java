package ca.mcgill.ecse321.retrofit_rxjava;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.retrofit_rxjava.dto.ArtistDto;
import ca.mcgill.ecse321.retrofit_rxjava.dto.PurchaseDto;
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

public class PurchaseDetailActivity extends AppCompatActivity {

    private static final String TAG = "PurchaseDetailActivity";
    private static final String BACKEND = "https://onlinegallery-backend-g7.herokuapp.com";
    private static final String AWS = "https://og-img-repo.s3.us-east-1.amazonaws.com";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer_purchases);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loadData();
    }

    public void loadData(){
        Observable<List<PurchaseDto>> call = backendInterface.getPurchasesByCustomerUsername("mike13");
        call
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(new Observer<List<PurchaseDto>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull List<PurchaseDto> dtos) {
                    fetchAwsAndBuild(dtos);
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
    }

    @SuppressLint("CheckResult")
    public void fetchAwsAndBuild(List<PurchaseDto> dtos){

        List<Observable<String>> images = new ArrayList<>();

        for (PurchaseDto dto : dtos){
            String image = dto.getArtowkrUrl().split(".com/")[1];
            images.add(awsInterface.getImgEncoding(image).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()));
        }

    }
}
