package ca.mcgill.ecse321.retrofit_rxjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.retrofit_rxjava.dto.ArtworkDto;
import ca.mcgill.ecse321.retrofit_rxjava.dto.AvailableNumDto;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class DiscoverActivity extends AppCompatActivity {
    private static final String TAG = "DiscoverActivity";
    private static final String BACKEND = "https://onlinegallery-backend-g7.herokuapp.com";
    private static final String AWS = "https://og-img-repo.s3.us-east-1.amazonaws.com";
    private static final int NUM_TO_PULL = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loadData();
    }

    public void refresh(View view){
        loadData();
    }

    @SuppressLint("CheckResult")
    public void loadData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BACKEND)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BackendInterface backendInterface = retrofit.create(BackendInterface.class);

        Observable<AvailableNumDto> numDtoObservable = backendInterface.countAvailableArtwork().subscribeOn(Schedulers.newThread());
        numDtoObservable.flatMap((Function<AvailableNumDto, ObservableSource<List<ArtworkDto>>>) availableNumDto -> {
            int num = Math.min(NUM_TO_PULL, availableNumDto.getAvailable());
            return backendInterface.getRandomArtworks(num);
        })
        .subscribe(
                t -> fetchAws(t)
        );
    }

    @SuppressLint("CheckResult")
    public void fetchAws(List<ArtworkDto> dtos) {
        Log.e(TAG, "stage1");
        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(AWS)
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .build();

        BackendInterface awsInterface = retrofit.create(BackendInterface.class);
        List<Observable<String>> requests = new ArrayList<>();

        for (ArtworkDto d:dtos){
            requests.add(awsInterface.getImgEncoding(d.getUrl()).subscribeOn(Schedulers.newThread()));
        }

        Observable.zip(
                requests,
                (Function<Object[], Object>) objects -> {
                    List<String> zippedStrings = new ArrayList<>();
                    for (Object o:objects){
                        zippedStrings.add((String) o);
                    }
                    return zippedStrings;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        t->startRecyclerView(dtos, (List<String>) t)
                );
    }

    public void startRecyclerView(List<ArtworkDto> dtos, List<String> t){

        // easily turn this method into instantiating the recyclerview
        Log.e(TAG, "\n-------------------------------- refresh ----------------------------\n");
        List<Bitmap> bitmapList = new ArrayList<>();
        for (int i=0;i<dtos.size();i++){
            Bitmap artBitmap=Helpers.Base64ToBitmap(t.get(i));
            bitmapList.add(artBitmap);
        }

        RecyclerView rView = findViewById(R.id.recycler_view);
        ImageAdapter adapter = new ImageAdapter(this,bitmapList.toArray(new Bitmap[0]),dtos);
        rView.setAdapter(adapter);
        rView.setLayoutManager(new LinearLayoutManager(DiscoverActivity.this));
    }

    public void detailActivity(View view){

    }
}
