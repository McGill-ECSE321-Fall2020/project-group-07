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

import ca.mcgill.ecse321.android_frontend.dto.ArtworkDto;
import ca.mcgill.ecse321.android_frontend.dto.AvailableNumDto;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
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

public class DiscoverActivity extends AppCompatActivity {
    private static final String TAG = "DiscoverActivity";
    private static final String BACKEND = "https://onlinegallery-backend-g7.herokuapp.com";
    private static final String AWS = "https://og-img-repo.s3.us-east-1.amazonaws.com";
    private static final int NUM_TO_PULL = 30;

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

    /**
     * initiates the Activity, retrieves Serialized values passed to it by previous activities
     * and sets various TextViews to corresponding information. Aslo instantiates the loadData()
     * function
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        pBar=findViewById(R.id.discovery_load_progress);
        pBar.setVisibility(View.VISIBLE);

        pText=findViewById(R.id.loading_msg);
        pText.setVisibility(View.VISIBLE);

        loadData();
    }


    /**
     * makes backend call to retrieve a number of random artworks in the form of a List<ArtworkDto>
     * upon call completion, passes the List<ArtworkDto> to fetchAws() to retrieve the Base64 of
     * each Artwork's photo
     */
    @SuppressLint("CheckResult")
    public void loadData() {

        Observable<AvailableNumDto> numDtoObservable = backendInterface.countAvailableArtwork().subscribeOn(Schedulers.newThread());
        numDtoObservable.flatMap((Function<AvailableNumDto, ObservableSource<List<ArtworkDto>>>) availableNumDto -> {
            int num = Math.min(NUM_TO_PULL, availableNumDto.getAvailable());
            return backendInterface.getRandomArtworks(num);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ArtworkDto>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull List<ArtworkDto> artworkDtos) {
                        fetchAws(artworkDtos);
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
     * fetches each Artwork's Base64 photo from aws using the url associated with each ArtworkDto.
     * Uses a Zip call from rxjava and retrofit. Makes the calls parallel and only execute the next
     * step, which is startRecyclerView, after all parallel calls complete.
     * @param dtos List<ArtworkDto>
     */
    @SuppressLint("CheckResult")
    public void fetchAws(List<ArtworkDto> dtos) {
        Log.e(TAG, "stage1");
        List<Observable<String>> requests = new ArrayList<>();

        for (ArtworkDto d : dtos) {
            requests.add(awsInterface.getImgEncoding(d.getUrl()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()));
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

    /**
     *uses the retrieved Base64 String representation of the artwork's photos and each Artwork's associated
     * ArtworkDto to populate the RecyclerView that displays the artworks as a scrolling series on the
     * phone
     *
     * @param dtos List<ArtworkDto> containing the data to eventually display the artist's
     *             artworks in a RecyclerView
     * @param t List<String> containing each artwork's photo's Base64 representation fetched from AWS
     */
    public void startRecyclerView(List<ArtworkDto> dtos, List<String> t) {

        Log.e(TAG, "\n-------------------------------- refresh ----------------------------\n");
        List<Bitmap> bitmapList = new ArrayList<>();
        for (int i = 0; i < dtos.size(); i++) {
            Bitmap artBitmap = Helpers.Base64ToBitmap(t.get(i));
            dtos.get(i).setArtBitmap(artBitmap);
        }

        pBar.setVisibility(View.GONE);
        pText.setVisibility(View.GONE);

        RecyclerView rView = findViewById(R.id.recycler_view);
        ImageAdapter adapter = new ImageAdapter(this, dtos,false);
        rView.setAdapter(adapter);
        rView.setLayoutManager(new LinearLayoutManager(DiscoverActivity.this));
    }

}
