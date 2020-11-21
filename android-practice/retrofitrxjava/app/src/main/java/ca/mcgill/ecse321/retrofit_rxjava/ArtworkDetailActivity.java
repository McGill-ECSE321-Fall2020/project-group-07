package ca.mcgill.ecse321.retrofit_rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class ArtworkDetailActivity extends AppCompatActivity {

    private String title;
    private String desc;
    private String dimension;
    private String medium;
    private String price;
    private String artist;
    private String artworkId;
    private String weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork_detail);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Intent intent = getIntent();
        title=(String) intent.getSerializableExtra("TITLE");
        desc = (String) intent.getSerializableExtra("DESC");
        dimension = (String) intent.getSerializableExtra("DIMENSION");
        medium = (String) intent.getSerializableExtra("MEDIUM");
        price = "$"+((Double) intent.getSerializableExtra("PRICE")).toString();
        artist = (String) intent.getSerializableExtra("ARTIST");
        artworkId = (String) intent.getSerializableExtra("ARTWORKID");
        weight = (String) intent.getSerializableExtra("WEIGHT");

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

    public void initiateBuy(View view){
        Intent intent = new Intent(view.getContext(),OrderActivity.class);

        intent.putExtra("TITLE",title);
        intent.putExtra("DESC",desc);
        intent.putExtra("MEDIUM",medium);
        intent.putExtra("DIMENSION",dimension);
        intent.putExtra("PRICE",price);
        intent.putExtra("ARTIST",artist);
        intent.putExtra("ARTWORkID",artworkId);
        intent.putExtra("WEIGHT",weight);

        view.getContext().startActivity(intent);
    }
}