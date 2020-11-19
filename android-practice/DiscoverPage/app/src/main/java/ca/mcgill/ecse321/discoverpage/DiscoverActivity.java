package ca.mcgill.ecse321.discoverpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;


import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.discoverpage.dtos.ArtworkDto;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.TestSubscriber;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import ca.mcgill.ecse321.discoverpage.Helpers;
import rx.Subscriber;

public class DiscoverActivity extends AppCompatActivity {
    public static final String API_ROOT = "https://onlinegallery-backend-g7.herokuapp.com";
    private static final String TAG = "DiscoveryActivity";
    private static final String AWS="https://og-img-repo.s3.us-east-1.amazonaws.com";
    private static List<String> recyclerInfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        Intent passedIntent = getIntent();
        List<ArtworkDto> dtos = (List<ArtworkDto>) passedIntent.getSerializableExtra("LIST");

        Retrofit awsRetrofit = new Retrofit.Builder()
                .baseUrl(AWS)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        BackendInterface awsInterface = awsRetrofit.create(BackendInterface.class);

        Observable<String> ob1=awsInterface.getImgEncoding("magicMike_11132020_65456PM_600_402");
        Observable<String> ob2=awsInterface.getImgEncoding("jhansolo_11132020_81813AM_600_568");

        List<String> zippedStrings = new ArrayList<>();

        Observable.zip(
                ob1,
                ob2,
                (str1,str2)->str1+" \n"+str2).subscribe(zippedStrings::add);

        Log.e(TAG, zippedStrings.get(0));
        Log.e(TAG, zippedStrings.get(1));


//        RecyclerView rView=findViewById(R.id.recyclerView);
//        CustomAdapter adapter = new CustomAdapter(recyclerInfo.toArray(new String[0]));
//        rView.setAdapter(adapter);
//        rView.setLayoutManager(new LinearLayoutManager(this));


//
//        for (ArtworkDto d:dtos){
//            Call<String> call = awsInterface.getImgEncoding(d.getUrl());
//            call.enqueue(new Callback<String>() {
//                @Override
//                public void onResponse(Call<String> call, Response<String> response) {
//                    if (response.isSuccessful()){
//                        collect(d.getName());
//                    }
//                }
//                @Override
//                public void onFailure(Call<String> call, Throwable t) {
//                }
//            });
//        }0
    }



}
