package ca.mcgill.ecse321.discoverpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="MainActivity";
    private static final String API_ROOT="https://onlinegallery-backend-g7.herokuapp.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(getApplicationContext());
    }

    public void startDiscover(View view){

        AndroidNetworking.get(API_ROOT+"/getAllAvailableArtwork").build().getAsObjectList(ArtworkDto.class, new ParsedRequestListener<List<ArtworkDto>>() {
            @Override
            public void onResponse(List<ArtworkDto> dtos) {
                Intent intent = new Intent(MainActivity.this, DiscoverActivity.class);
                intent.putExtra("LIST",(Serializable) dtos);
                startActivity(intent);
            }
            @Override
            public void onError(ANError anError) {
                Log.d(TAG,"fail");
            }
        });


    }

    class ArtworkDto implements Serializable {
        private String url;
        private String medium;
        private String username;
        private String name;
        private String description;
        private String price;
        private String status;
        private String numViews;
        private String dimension;
        private String weight;
        private String commission;
        private String artworkId;

        public ArtworkDto(String url, String medium, String username, String name, String description, String price, String status, String numViews, String dimension, String weight, String commission, String artworkId) {
            this.url = url;
            this.medium = medium;
            this.username = username;
            this.name = name;
            this.description = description;
            this.price = price;
            this.status = status;
            this.numViews = numViews;
            this.dimension = dimension;
            this.weight = weight;
            this.commission = commission;
            this.artworkId = artworkId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getNumViews() {
            return numViews;
        }

        public void setNumViews(String numViews) {
            this.numViews = numViews;
        }

        public String getDimension() {
            return dimension;
        }

        public void setDimension(String dimension) {
            this.dimension = dimension;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
        }

        public String getArtworkId() {
            return artworkId;
        }

        public void setArtworkId(String artworkId) {
            this.artworkId = artworkId;
        }

        @Override
        public String toString() {
            return "ArtworkDto{" +
                    "url='" + url + '\'' +
                    ", medium='" + medium + '\'' +
                    ", username='" + username + '\'' +
                    ", name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    ", price='" + price + '\'' +
                    ", status='" + status + '\'' +
                    ", numViews='" + numViews + '\'' +
                    ", dimension='" + dimension + '\'' +
                    ", weight='" + weight + '\'' +
                    ", commission='" + commission + '\'' +
                    ", artworkId='" + artworkId + '\'' +
                    '}'+"\n";
        }
    }


}