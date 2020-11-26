package ca.mcgill.ecse321.android_frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

public class OrderActivity extends AppCompatActivity {

    private static final String TAG = "OrderActivity";
    private static DecimalFormat df = new DecimalFormat("0.00");

    private String title;
    private String dimension;
    private String medium;
    private double priceVal;
    private String artist;
    private String artworkId;
    private String weight;
    private double shipVal;
    private double totalVal;

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
    TextView titleShipping;
    TextView titleTotal;

    TextView addressView;
    TextView recipientView;

    private TextWatcher shippingWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            String address = addressView.getText().toString().trim();
            String name = recipientView.getText().toString().trim();

            boolean nonEmpty=((address.length()>0)&&(name.length()>0));
            continueButton.setEnabled((nonEmpty));
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String address = addressView.getText().toString().trim();
            String name = recipientView.getText().toString().trim();

            boolean nonEmpty=((address.length()>0)&&(name.length()>0));
            continueButton.setEnabled(nonEmpty);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Intent intent = getIntent();

        title=(String) intent.getSerializableExtra("TITLE");
        dimension = (String) intent.getSerializableExtra("DIMENSION");
        medium = (String) intent.getSerializableExtra("MEDIUM");

        priceVal= Double.parseDouble((String) intent.getSerializableExtra("PRICE"));
        shipVal=Double.parseDouble((String) intent.getSerializableExtra("WEIGHT"))*110.34;
        totalVal=priceVal+shipVal;

        artist = (String) intent.getSerializableExtra("ARTIST");
        weight = (String) intent.getSerializableExtra("WEIGHT");
        artworkId = (String) intent.getSerializableExtra("ID");

        titleView = findViewById(R.id.order_title);
        titleArtist = findViewById(R.id.order_artist);
        titleDimension = findViewById(R.id.order_dimension);
        titleMedium = findViewById(R.id.order_medium);
        titlePrice = findViewById(R.id.order_price);
        titleShipping = findViewById(R.id.order_shippingPrice);
        titleTotal = findViewById(R.id.order_totalPrice);
        titleWeight = findViewById(R.id.order_weight);

        addressView = findViewById(R.id.ccNum);
        addressView.addTextChangedListener(shippingWatcher);

        recipientView = findViewById(R.id.recipientName);
        recipientView.addTextChangedListener(shippingWatcher);

        continueButton = findViewById(R.id.buy_button);

        titleView.setText(title);
        titleArtist.setText(artist);
        titleDimension.setText(dimension);
        titleMedium.setText(medium);
        titlePrice.setText("$"+df.format(priceVal));
        titleShipping.setText("$"+df.format(shipVal));
        titleTotal.setText("$"+df.format(totalVal));
        titleWeight.setText(weight);

        radioGroup=findViewById(R.id.radioGroup);

        destAddress="115 Normand, Montreal, QC";
        recipientName="Gallery Admin";

        addressView.setText(destAddress);
        recipientView.setText(recipientName);

        shipMethod="gallery pickup";

        Log.e(TAG, "onCreate: "+artworkId);

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
            destAddress="";
            recipientName="";
        }

        addressView.setText(destAddress);
        recipientView.setText(recipientName);
    }

    public void continueButton(View view){
        Intent intent = new Intent(view.getContext(),CheckoutActivity.class);
        intent.putExtra("ARTWORKID",artworkId);
        intent.putExtra("TOTAL",((Double) totalVal).toString());
        intent.putExtra("DEST",addressView.getText().toString().trim());
        intent.putExtra("SHIPCOST",(Double) shipVal);
        intent.putExtra("TOTAL",(Double) totalVal);
        intent.putExtra("RECIPIENT",recipientView.getText().toString().trim());

        if (shipMethod.equals("gallery pickup")){
            intent.putExtra("SHIPTYPE","ONSITE_PICKUP");
        }
        else if (shipMethod.equals("home delivery")){
            intent.putExtra("SHIPTYPE","OFFSITE_DELIVERY");
        }

        view.getContext().startActivity(intent);
    }

}