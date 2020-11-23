package ca.mcgill.ecse321.retrofit_rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    private static final String TAG = "OrderActivity";

    private String title;
    private String dimension;
    private String medium;
    private String price;
    private String artist;
    private String artworkId;
    private String weight;

    private String destAddress="shipping address";
    private String recipientName="recipient name";
    private String shipMethod;

    RadioGroup radioGroup;
    RadioButton radioButton;
    Button continueButton;

    TextView titleView;
    TextView titleArtist;
    TextView titleDimension;
    TextView titleMedium;
    TextView titlePrice;
    TextView titleWeight;

    TextView addressView;
    TextView recipientView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();

        title=(String) intent.getSerializableExtra("TITLE");
        dimension = (String) intent.getSerializableExtra("DIMENSION");
        medium = (String) intent.getSerializableExtra("MEDIUM");
        price = "$"+(String) intent.getSerializableExtra("PRICE");
        artist = (String) intent.getSerializableExtra("ARTIST");
        weight = (String) intent.getSerializableExtra("WEIGHT");

        artworkId = (String) intent.getSerializableExtra("ARTWORKID");

        titleView = findViewById(R.id.order_title);
        titleArtist = findViewById(R.id.order_artist);
        titleDimension = findViewById(R.id.order_dimension);
        titleMedium = findViewById(R.id.order_medium);
        titlePrice = findViewById(R.id.order_price);
        titleWeight = findViewById(R.id.order_weight);
        addressView = findViewById(R.id.order_shipAddress);
        recipientView = findViewById(R.id.order_recipientName);
        continueButton = findViewById(R.id.order_continue);

        titleView.setText(title);
        titleArtist.setText(artist);
        titleDimension.setText(dimension);
        titleMedium.setText(medium);
        titlePrice.setText(price);
        titleWeight.setText(weight);

        radioGroup=findViewById(R.id.radioGroup);

    }

    public void checkButton(View view){
        int radioId=radioGroup.getCheckedRadioButtonId();
        radioButton=findViewById(radioId);
        shipMethod=radioButton.getText().toString();
        if (shipMethod.equals("gallery pickup")){
            destAddress="115 Normand, Montreal, QC";
            recipientName="Gallery Admin";

        }
        else if (shipMethod.equals("home delivery")){
            destAddress="shipping address";
            recipientName="recipient name";
        }

        addressView.setText(destAddress);
        recipientView.setText(recipientName);

        if (destAddress.equals("shipping address") || recipientName.equals("recipient name")){
            continueButton.setEnabled(false);
            continueButton.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        }
        else{
            continueButton.setEnabled(true);
        }
    }

    public void continueButton(View view){
        Intent intent = new Intent(view.getContext(),CheckoutActivity.class);
        view.getContext().startActivity(intent);
    }

}