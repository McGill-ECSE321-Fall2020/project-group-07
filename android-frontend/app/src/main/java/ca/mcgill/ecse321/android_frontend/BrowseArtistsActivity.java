package ca.mcgill.ecse321.android_frontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.android_frontend.dto.ArtistDto;
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

public class BrowseArtistsActivity extends AppCompatActivity {

    private static final String TAG = "BrowseArtistsActivity";
    private static final String BACKEND = "https://onlinegallery-backend-g7.herokuapp.com";
    private static final String AWS = "https://og-img-repo.s3.us-east-1.amazonaws.com";

    ProgressBar pBar;
    TextView pText;

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
        setContentView(R.layout.activity_browse_artists);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        pBar = findViewById(R.id.all_artist_loading_bar);
        pBar.setVisibility(View.VISIBLE);

        pText=findViewById(R.id.all_artist_loading_text);
        pText.setVisibility(View.VISIBLE);

        loadData();
    }

    public void loadData(){
        Observable<List<ArtistDto>> call = backendInterface.getAllArtists();
        call
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Observer<List<ArtistDto>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<ArtistDto> artistDtos) {
               fetchAws(artistDtos);
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
    public void fetchAws(List<ArtistDto> dtos){
        List<Observable<String>> requests = new ArrayList<>();

        for (ArtistDto d:dtos){
            Log.e(TAG, d.getUrl().split(".com/")[1]);
        }

        for (ArtistDto d:dtos){
            String profileImgUrl=d.getUrl().split(".com/")[1];
            requests.add(awsInterface.getImgEncoding(profileImgUrl).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()));
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

    public void startRecyclerView(List<ArtistDto> dtos, List<String> t) {

        Log.e(TAG, "\n-------------------------------- refresh ----------------------------\n");
        for (String s:t){
            Log.e(TAG, "startRecyclerView: "+s );
        }
        List<Bitmap> bitmapList = new ArrayList<>();
        for (int i = 0; i < dtos.size(); i++) {
            Bitmap artBitmap = Helpers.Base64ToBitmap(t.get(i));
            bitmapList.add(artBitmap);
        }

        pBar.setVisibility(View.GONE);
        pText.setVisibility(View.GONE);

        RecyclerView rView = findViewById(R.id.artist_recyclerview);
        ArtistAdapter adapter = new ArtistAdapter(this, bitmapList.toArray(new Bitmap[0]), dtos);
        rView.setAdapter(adapter);
        rView.setLayoutManager(new LinearLayoutManager(BrowseArtistsActivity.this));
    }
}