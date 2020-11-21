package ca.mcgill.ecse321.retrofit_rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class ArtworkDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork_detail);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Intent intent = getIntent();
        String title=(String) intent.getSerializableExtra("TITLE");
        String desc = (String) intent.getSerializableExtra("DESC");
        String dimension = (String) intent.getSerializableExtra("DIMENSION");
        String medium = (String) intent.getSerializableExtra("MEDIUM");
        String price = "$"+((Double) intent.getSerializableExtra("PRICE")).toString();
        String artist = (String) intent.getSerializableExtra("ARTIST");

        TextView titleText = findViewById(R.id.detail_title);
        TextView descText=findViewById(R.id.detail_desc);
        TextView mediumText=findViewById(R.id.detail_medium);
        TextView dimensionText=findViewById(R.id.detail_dimension);
        TextView priceText=findViewById(R.id.detail_price);
        TextView artistText=findViewById(R.id.detail_artistname);

        titleText.setText(title);
        descText.setText(desc);
        mediumText.setText(medium);
        dimensionText.setText(dimension);
        priceText.setText(price);
        artistText.setText(artist);

    }
}