package ca.mcgill.ecse321.retrofit_rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import io.reactivex.Observable;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String AWS="https://og-img-repo.s3.us-east-1.amazonaws.com";

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AWS)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        BackendInterface awsInterface = retrofit.create(BackendInterface.class);

        List<String> zippedStrings = new ArrayList<>();

        Observable<String> ob1 = awsInterface.getImgEncoding("magicMike_11132020_65456PM_600_402");
        ob1.subscribeOn(Schedulers.io());

        Observable<String> ob2 = awsInterface.getImgEncoding("jhansolo_11132020_81813AM_600_568");
        ob2.subscribeOn(Schedulers.io());

        List<Observable<String>> requests = new ArrayList<>();
        requests.add(ob1);
        requests.add(ob2);


        Observable.zip(
                requests,
                new Function<Object[], Object>() {
                    @Override
                    public Object apply(Object[] objects) throws Exception {
                        List<String> zippedStrings = new ArrayList<>();
                        for (Object o:objects){
                            zippedStrings.add((String) o);
                        }
                        return zippedStrings;
                    }
                }
        ).subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                for (String s:(List<String>) o){
                    Log.e(TAG, s );
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                Log.e(TAG, throwable.getMessage(), throwable);
            }
        });

//        Observable.zip(
//                ob1.subscribeOn(Schedulers.io()),
//                ob2.subscribeOn(Schedulers.io()),
//                (str1,str2)->str1+" "+str2).subscribe(zippedStrings::add);
//
//        Log.e(TAG, ((Integer) zippedStrings.size()).toString() );

    }
}