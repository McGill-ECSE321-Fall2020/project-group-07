package ca.mcgill.ecse321.android_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PaymentConfirmedActivity extends AppCompatActivity {


    /**
     * initiates the Activity, retrieves Serialized values passed to it by previous activities

     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmed);
    }


    /**
     * onClick method for "Home" button to return to app home
     * @param view
     */
    public void backHome (View view){
        Intent intent = new Intent(view.getContext(),MainActivity.class);
        view.getContext().startActivity(intent);
    }
}