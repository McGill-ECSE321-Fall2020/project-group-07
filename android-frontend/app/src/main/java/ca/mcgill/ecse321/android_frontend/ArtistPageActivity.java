package ca.mcgill.ecse321.android_frontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.android_frontend.dto.ArtworkDto;
import de.hdodenhof.circleimageview.CircleImageView;
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

public class ArtistPageActivity extends AppCompatActivity {

    private static final String TAG = "DiscoverActivity";
    private static final String BACKEND = "https://onlinegallery-backend-g7.herokuapp.com";
    private static final String AWS = "https://og-img-repo.s3.us-east-1.amazonaws.com";

    ProgressBar pBar;
    TextView pText;
    TextView noArtView;

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

    private String username;
    private Long artistId;
    private String firstLast;
    private Bitmap profileImg;

    CircleImageView profileView;
    TextView firstLastView;

    /**
     * initiates the Activity, retrieves Serialized values passed to it by previous activities
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        pBar= findViewById(R.id.artist_page_loading_bar);
        pText=findViewById(R.id.artist_page_loading_text);

        pBar.setVisibility(View.VISIBLE);
        pText.setVisibility(View.VISIBLE);

        noArtView=findViewById(R.id.no_artwork_msg);
        noArtView.setVisibility(View.GONE);

        Intent intent = getIntent();

        username=(String) intent.getSerializableExtra("USERNAME");
        firstLast=(String) intent.getSerializableExtra("FIRSTLAST");
        artistId=(Long) intent.getSerializableExtra("ARTISTID");
        byte[] byteArray=intent.getByteArrayExtra("PROFILEIMG");
        profileImg= BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);

        profileView = findViewById(R.id.page_profile_image);
        firstLastView=findViewById(R.id.page_artist_name);

        firstLastView.setText(firstLast);
        profileView.setImageBitmap(profileImg);

        loadData(artistId);
    }

    /**
     * makes a Retrofit call to the backend to retrieve the list of artworks of
     * this artist with the artistId. List of artworks returned as a list of
     * ArtworkDtos
     *
     * @param artistId Long used to retrieve the artist
     */
    public void loadData(Long artistId){
        Observable<List<ArtworkDto>> call = backendInterface.getArtistArtworks(artistId.toString());
        call
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Observer<List<ArtworkDto>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<ArtworkDto> artworkDtos) {
                if (artworkDtos.size()>0) {
                    fetchAws(artworkDtos);
                }
                else{
                    pBar.setVisibility(View.GONE);
                    pText.setVisibility(View.GONE);
                    noArtView.setVisibility(View.VISIBLE);
                    return;
                }
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
     * cycles through the List<ArtworkDto> to extract the AWS url of each artwork's photo
     * creates a list of Retrofit fetch calls to AWS to retrieve these photos' binary (Base64)
     * representations. Uses rxjava's Zip function to make these calls in parallel and waits until all
     * finished to proceed to the next method, which is startRecyclerView
     *
     * @param dtos List<ArtworkDto> containing the data to eventually display the artist's
     *             artworks in a RecyclerView
     */
    @SuppressLint("CheckResult")
    public void fetchAws(List<ArtworkDto> dtos) {
        Log.e(TAG, "stage1");
        List<Observable<String>> requests = new ArrayList<>();

        for (ArtworkDto d:dtos){
            Log.e(TAG, "fetchAws: "+ d.toString());
        }

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
    public void startRecyclerView(List<ArtworkDto> dtos, @NotNull List<String> t) {

        Log.e(TAG, "startRecyclerView: "+((Integer)dtos.size()).toString() );

        pBar.setVisibility(View.GONE);
        pText.setVisibility(View.GONE);

            Log.e(TAG, "\n-------------------------------- refresh ----------------------------\n");
            List<Bitmap> bitmapList = new ArrayList<>();
            for (int i = 0; i < dtos.size(); i++) {
                Bitmap artBitmap = Helpers.Base64ToBitmap(t.get(i));
                dtos.get(i).setArtBitmap(artBitmap);
            }


            RecyclerView rView = findViewById(R.id.artist_page_recyclerview);
            ImageAdapter adapter = new ImageAdapter(this, dtos, true);
            rView.setAdapter(adapter);
            rView.setLayoutManager(new LinearLayoutManager(ArtistPageActivity.this));
    }
}