package ca.mcgill.ecse321.retrofit_rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.retrofit_rxjava.dto.ArtworkDto;
import ca.mcgill.ecse321.retrofit_rxjava.dto.AvailableNumDto;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
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
    private static final int NUM_TO_PULL = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
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


        Observable<AvailableNumDto> numDtoObservable = backendInterface.countAvailableArtwork().subscribeOn(Schedulers.io());
        numDtoObservable.flatMap((Function<AvailableNumDto, ObservableSource<List<ArtworkDto>>>) availableNumDto -> {
            int num = Math.min(NUM_TO_PULL, availableNumDto.getAvailable());
            return backendInterface.getRandomArtworks(num);
        })
                .subscribeOn(Schedulers.io())
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
            requests.add(
                    awsInterface.getImgEncoding(d.getUrl()).subscribeOn(Schedulers.io())
            );
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
                .subscribeOn(Schedulers.io())
                .subscribe(
                        t->print(dtos, (List<String>) t)
                );
    }

    public void print(List<ArtworkDto> dtos, List<String> t){

        // easily turn this method into instantiating the recyclerview
        Log.e(TAG, "\n-------------------------------- refresh ----------------------------\n");
        for (int i=0;i<dtos.size();i++){
            Log.e(TAG, dtos.get(i).getName()+"  "+t.get(i) );
        }
    }
}

//        List<String> zippedStrings = new ArrayList<>();
//
//        Observable<String> ob1 = awsInterface.getImgEncoding("magicMike_11132020_65456PM_600_402");
//        ob1.subscribeOn(Schedulers.io());
//
//        Observable<String> ob2=awsInterface.getImgEncoding("jhansolo_11132020_81813AM_600_568");
//        ob2.subscribeOn(Schedulers.io());
//
//        List<Observable<String>>requests=new ArrayList<>();
//        requests.add(ob1);
//        requests.add(ob2);
//
//
//        Observable.zip(
//            requests,
//            new Function<Object[],Object>(){
//                    @Override
//                    public Object apply(Object[]objects)throws Exception{
//                            List<String> zippedStrings=new ArrayList<>();
//                            for(Object o:objects){
//                            zippedStrings.add((String)o);
//                            }
//                            return zippedStrings;
//                            }
//                })
//        .subscribeOn(Schedulers.newThread())
//        .subscribe(
//                new Consumer<Object>(){
//                @Override
//                public void accept(Object o)throws Exception{
//                        for(String s:(List<String>)o){
//                        Log.e(TAG,s);
//                        }
//                    }
//                },
//                new Consumer<Throwable>(){
//                @Override
//                public void accept(Throwable throwable){
//                        Log.e(TAG,throwable.getMessage(),throwable);
//                    }
//                });
