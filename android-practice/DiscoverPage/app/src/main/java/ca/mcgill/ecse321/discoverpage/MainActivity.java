package ca.mcgill.ecse321.discoverpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.Serializable;
import java.util.List;

import ca.mcgill.ecse321.discoverpage.dtos.ArtworkDto;
import ca.mcgill.ecse321.discoverpage.dtos.Todo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="MainActivity";
    private static final String API_ROOT="https://onlinegallery-backend-g7.herokuapp.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startDiscover(View view){
        //Retrofit Builder
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_ROOT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Instance of BackendInterface
        BackendInterface backendInterface = retrofit.create(BackendInterface.class);

        //
        Call<List<ArtworkDto>> call = backendInterface.getAllAvailableArtwork();
        call.enqueue(new Callback<List<ArtworkDto>>() {
            @Override
            public void onResponse(Call<List<ArtworkDto>> call, Response<List<ArtworkDto>> response) {
                if (response.isSuccessful()){
                    List<ArtworkDto> artworkDtos=response.body();
                    Intent discoverIntent = new Intent(MainActivity.this,DiscoverActivity.class);
                    discoverIntent.putExtra("LIST",(Serializable) artworkDtos);
                    startActivity(discoverIntent);
                }
            }
            @Override
            public void onFailure(Call<List<ArtworkDto>> call, Throwable t) {
            }
        });
    }
}