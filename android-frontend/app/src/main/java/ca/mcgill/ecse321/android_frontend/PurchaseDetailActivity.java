package ca.mcgill.ecse321.android_frontend;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.android_frontend.dto.PurchaseSummaryDto;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class PurchaseDetailActivity extends AppCompatActivity {

    private static final String TAG = "PurchaseDetailActivity";
    private static final String BACKEND = "https://onlinegallery-backend-g7.herokuapp.com";
    private static final String AWS = "https://og-img-repo.s3.us-east-1.amazonaws.com";
    private String username;

    ProgressBar pBar;
    TextView pText;

    // To get a retrofit object in order to do backend calls easily
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BACKEND)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    BackendInterface backendInterface = retrofit.create(BackendInterface.class);

    // To get a retrofit object in order to retrieve images from AWS
    Retrofit retrofitAWS = new Retrofit.Builder()
            .baseUrl(AWS)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    BackendInterface awsInterface = retrofitAWS.create(BackendInterface.class);

    /**
     * Initiates the customer purchase activity, retrieves serialized values passed to it
     * by the previous activity, and instantiates the loadData() function.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer_purchases);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // retrieve username that was inserted into the customer login forum
        Intent i = getIntent();
        username=(String) i.getSerializableExtra("USERNAME");

        pBar=findViewById(R.id.load_purchase_pBar);
        pText=findViewById(R.id.load_purchase_pText);

        pBar.setVisibility(View.VISIBLE);
        pText.setVisibility(View.VISIBLE);

        loadData();
    }

    /**
     * Makes backend call to retrieve a list of purchases done by the respective customer
     * in the form of a List<PurchaseSummaryDto>. Furthermore, it then passes the
     * List<PurchaseSummaryDto> to fetchAwsAndBuild().
     */
    public void loadData(){
        // Retrieve this customers respective purchases
        Observable<List<PurchaseSummaryDto>> call = backendInterface.getPurchasesByCustomerUsername(username);
        call
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<PurchaseSummaryDto>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<PurchaseSummaryDto> dtos) {
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

    /**
     * Fetches each customer's purchased artwork's Base64 photo from aws
     * and calls beginBuilding() after completion.
     *
     * @param dtos List<PurchaseSummaryDto>
     */
    @SuppressLint("CheckResult")
    public void fetchAwsAndBuild(List<PurchaseSummaryDto> dtos){

        List<Observable<String>> images = new ArrayList<>();

        for (PurchaseSummaryDto dto : dtos){
            images.add(awsInterface.getImgEncoding(dto.getArtworkUrl()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()));
        }

        Observable.zip(
                images,
                (Function<Object[], Object>) objects -> {
                    List<String> zippedStrings = new ArrayList<>();
                    for (Object o:objects){
                        zippedStrings.add((String) o);
                    }
                    return zippedStrings;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        t->beginBuilding(dtos, (List<String>) t)
                );

    }

    /**
     * Populates the RecyclerView that displays the customer's purchased artworks
     * and a brief summary of each purchase.
     *
     * @param dtos List<PurchaseSummaryDto>
     * @param t List<String>  Each string contains each artwork's photo's Base64 representation fetched from AWS
     */
    public void beginBuilding(List<PurchaseSummaryDto> dtos, List<String> t) {

        Log.e(TAG, "\n-------------------------------- refresh ----------------------------\n");
        List<Bitmap> bitmapList = new ArrayList<>();
        for (int i = 0; i < dtos.size(); i++) {
            Bitmap artBitmap = Helpers.Base64ToBitmap(t.get(i));
            bitmapList.add(artBitmap);
        }

        pBar.setVisibility(View.GONE);
        pText.setVisibility(View.GONE);

        RecyclerView rView = findViewById(R.id.purchase_recyclerview);
        // Call purchase adapter to fill front-end with dynamic information about this customers purchases
        PurchaseAdapter adapter = new PurchaseAdapter(this, bitmapList.toArray(new Bitmap[0]), dtos);
        rView.setAdapter(adapter);
        rView.setLayoutManager(new LinearLayoutManager(PurchaseDetailActivity.this));
    }
}
