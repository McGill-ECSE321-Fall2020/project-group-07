package ca.mcgill.ecse321.android_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ConfirmUploadActivity extends AppCompatActivity {

    private static final String TAG = "ConfirmUploadActivity";

    private String username;
    private String firstLast;
    private Long artistId;
    private byte[] byteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_upload);

        Intent i = getIntent();
        username=(String) i.getSerializableExtra("USERNAME");
        Log.e(TAG, "onCreate: "+username );
    }
}