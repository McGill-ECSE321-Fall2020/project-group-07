package ca.mcgill.ecse321.discoverpage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse321.discoverpage.MainActivity.ArtworkDto;

public class DiscoverActivity extends AppCompatActivity {
    public static final String API_ROOT = "https://onlinegallery-backend-g7.herokuapp.com";
    private static final String TAG = "DiscoveryActivity";
    private static final String AWS="http://og-img-repo.s3.us-east-1.amazonaws.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        Intent i = getIntent();
        List<ArtworkDto> dtos= (List<ArtworkDto>)i.getSerializableExtra("LIST");
        for (ArtworkDto d:dtos){
            d.setUrl(AWS+"/"+d.getUrl());
        }

        for (ArtworkDto d:dtos){
            Log.d(TAG,d.getUrl());
        }
    }



}
