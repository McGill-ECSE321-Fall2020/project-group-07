package ca.mcgill.ecse321.onlinegallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    public static final String TAG="MainActivity";
    public static final String EXTRA_MESSAGE="ca.mcgill.ecse321.onlinegallery.MESSAGE";
    public static final String API_ROOT="https://onlinegallery-backend-g7.herokuapp.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidNetworking.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(getApplicationContext());
    }

    public void getRegistration(View view){
        EditText editText = (EditText) findViewById(R.id.usernameInput);
        String username = editText.getText().toString();
        String requestUrl=API_ROOT+"/getRegistration/"+username;
        AndroidNetworking.get(requestUrl).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                EditText registrationText = (EditText) findViewById(R.id.registrationInfo);
                registrationText.setText(response.toString());
                Log.d(TAG,"s");
            }
            @Override
            public void onError(ANError anError) {
                EditText registrationText = (EditText) findViewById(R.id.registrationInfo);
                registrationText.setText(anError.getErrorBody().toString());
                Log.d(TAG,"F");
            }
        });
    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.usernameInput);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }
}