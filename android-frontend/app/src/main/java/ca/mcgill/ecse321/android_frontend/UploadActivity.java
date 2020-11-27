package ca.mcgill.ecse321.android_frontend;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import ca.mcgill.ecse321.android_frontend.dto.ArtworkDto;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UploadActivity extends AppCompatActivity {
    private static final String BACKEND = "https://onlinegallery-backend-g7.herokuapp.com";
    private static final String AWS = "https://og-img-repo.s3.us-east-1.amazonaws.com";
    private static final String TAG = "UploadActivity";

    private static final int PERMISSION_REQUEST = 0;
    private static final int RESULT_LOAD_IMAGE = 0;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("MMddYYYY_HHmmss");

    private ArtworkDto uploadDto = new ArtworkDto();

    private String imgEncoding;
    private String awsUrl="";
    private String username;


    Bitmap img;
    Bitmap scaledImg;

    TextView titleView;
    TextView descView;
    TextView mediumView;
    TextView priceView;
    TextView dimView;
    TextView weightView;
    TextView commView;
    TextView responseMsg;

    Button uploadBtn;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BACKEND)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    BackendInterface backendInterface = retrofit.create(BackendInterface.class);

    Retrofit retrofitAWS = new Retrofit.Builder()
            .baseUrl(AWS)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    BackendInterface awsInterface = retrofitAWS.create(BackendInterface.class);

    private TextWatcher uploadWatcher = new TextWatcher() {

        private void populateDto(){
            uploadDto.setUsername(username);

            if (titleView.getText().toString().trim().length()>0){
                uploadDto.setName(titleView.getText().toString().trim());
            }
            if (descView.getText().toString().trim().length()>0){
                uploadDto.setDescription(descView.getText().toString().trim());
            }
            if (mediumView.getText().toString().trim().length()>0){
                uploadDto.setMedium(mediumView.getText().toString().trim());
            }
            if (priceView.getText().toString().trim().length()>0){
                uploadDto.setPrice(Double.parseDouble(priceView.getText().toString().trim()));
            }

            uploadDto.setStatus("1");

            if (dimView.getText().toString().trim().length()>0){
                uploadDto.setDimension(dimView.getText().toString().trim());
            }
            if (weightView.getText().toString().trim().length()>0){
                uploadDto.setWeight(Double.parseDouble(weightView.getText().toString().trim()));
            }
            if (commView.getText().toString().trim().length()>0){
                uploadDto.setCommission(Double.parseDouble(commView.getText().toString().trim()));
            }

            uploadDto.setNumViews(0);
            uploadDto.setUrl(awsUrl);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            boolean nonEmpty=(
                    (titleView.getText().toString().trim().length()>0) &&
                            (descView.getText().toString().trim().length()>0)&&
                            (mediumView.getText().toString().trim().length()>0)&&
                            (priceView.getText().toString().trim().length()>0)&&
                            (dimView.getText().toString().trim().length()>0)&&
                            (weightView.getText().toString().trim().length()>0)&&
                            (commView.getText().toString().trim().length()>0)
            );
            uploadBtn.setEnabled(nonEmpty);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean nonEmpty=(
                    (titleView.getText().toString().trim().length()>0) &&
                            (descView.getText().toString().trim().length()>0)&&
                            (mediumView.getText().toString().trim().length()>0)&&
                            (priceView.getText().toString().trim().length()>0)&&
                            (dimView.getText().toString().trim().length()>0)&&
                            (weightView.getText().toString().trim().length()>0)&&
                            (commView.getText().toString().trim().length()>0)
            );
            uploadBtn.setEnabled(nonEmpty);
            populateDto();
            if (nonEmpty){
                Log.e(TAG, "onTextChanged: "+uploadDto.toString() );
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            boolean nonEmpty=(
                    (titleView.getText().toString().trim().length()>0) &&
                            (descView.getText().toString().trim().length()>0)&&
                            (mediumView.getText().toString().trim().length()>0)&&
                            (priceView.getText().toString().trim().length()>0)&&
                            (dimView.getText().toString().trim().length()>0)&&
                            (weightView.getText().toString().trim().length()>0)&&
                            (commView.getText().toString().trim().length()>0)
            );
            uploadBtn.setEnabled(nonEmpty);
        }
    };


    /**
     * initiates the Activity, retrieves Serialized values passed to it by previous activities
     * and sets various TextViews to corresponding information.
     * Asks for permission to access device storage and media
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        titleView = findViewById(R.id.upload_title);
        titleView.addTextChangedListener(uploadWatcher);

        descView = findViewById(R.id.upload_desc);
        descView.addTextChangedListener(uploadWatcher);

        mediumView = findViewById(R.id.upload_medium);
        mediumView.addTextChangedListener(uploadWatcher);

        priceView = findViewById(R.id.upload_price);
        priceView.addTextChangedListener(uploadWatcher);

        dimView = findViewById(R.id.upload_dimension);
        dimView.addTextChangedListener(uploadWatcher);

        weightView = findViewById(R.id.upload_weight);
        weightView.addTextChangedListener(uploadWatcher);

        commView = findViewById(R.id.upload_commission);
        commView.addTextChangedListener(uploadWatcher);

        responseMsg = findViewById(R.id.response_msg);
        responseMsg.setText("");

        uploadBtn = findViewById(R.id.btn_upload);
        uploadBtn.setEnabled(false);

        username = "CMonet";             //this would be replaced by the artist login page before

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
        }

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    /**
     * resizes the chosen image to a width of 600 px, scales height proportionally
     * also creates a unique aws file name based on the artist username, time of upload, and image dimensions
     */
    public void resizeImg() {
        double originalWidth = img.getWidth();
        double originalHeight = img.getHeight();

        double sf = 600.0 / originalWidth;

        int newWidth = 600;
        int newHeight = (int) Math.round(sf * originalHeight);

        scaledImg = Bitmap.createScaledBitmap(img, newWidth, newHeight, false);

        Timestamp ts = new Timestamp(System.currentTimeMillis());
        awsUrl = username + "_" + sdf.format(ts) + "_600" + "_" + ((Integer) newHeight).toString();

        Log.e(TAG, "resizeImg: " + awsUrl);
        encode();
    }

    /**
     * converts the image to upload to a Base64 string representation for upload to aws
     */
    public void encode() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        scaledImg.compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream);
        imgEncoding = "data:image/png;base64," + Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
    }

    /**
     * makes a retrofit call to aws to upload the Base64 string of the image,
     * upon success, makes the subsequent backend call to also create the Artwork in the database
     * by sending an ArtworkDto with the url set to the unique aws filename
     * @param view
     */
    public void upload(View view) {
        Observable<String> uploadCall = awsInterface.uploadImgEncoding(awsUrl,imgEncoding);
        uploadCall
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.annotations.NonNull String s) {
                Log.e(TAG, "onNext: "+s );
                Log.e(TAG, "onNext: "+"successfully uploaded to aws!" );
                responseMsg.setText("artwork uploaded to AWS, creating in system ...");
                createArtwork();
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                Log.e(TAG, "onError: "+e.getLocalizedMessage());

                if (e instanceof HttpException){
                    ResponseBody body = ((HttpException) e).response().errorBody();
                    try {
                        responseMsg.setText(body.string());
                    } catch (IOException ioException) {
                        responseMsg.setText("unknown error, try again later");
                    }
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * the retrofit call triggered after successfully uploading the artwork as a Base64 to aws.
     * Body of the call is an ArtworkDto with the url attribute set to the unqiue aws url of the
     * uploaded Base64.
     */
    public void createArtwork() {
        Log.e(TAG, "createArtwork: " + "this is the triggered backend call");

        Observable<ArtworkDto> uploadCall = backendInterface.createArtwork(uploadDto);
        uploadCall
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Observer<ArtworkDto>() {
            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.annotations.NonNull ArtworkDto artworkDto) {
                Log.e(TAG, "onNext: "+artworkDto.toString());
                responseMsg.setText("created!");
                Intent intent = new Intent(UploadActivity.this,ConfirmUploadActivity.class);
                intent.putExtra("USERNAME",username);
                startActivity(intent);
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                Log.e(TAG, "onError: "+e.getLocalizedMessage());
                if (e instanceof HttpException){
                    ResponseBody body = ((HttpException) e).response().errorBody();
                    try {
                        responseMsg.setText(body.string());
                        Intent confirmIntent = new Intent(UploadActivity.this,ConfirmUploadActivity.class);

                        confirmIntent.putExtra("USERNAME",username);
                        startActivity(confirmIntent);

                    } catch (IOException ioException) {
                        responseMsg.setText("unknown error, try again later");
                    }
                }
            }

            @Override
            public void onComplete() {

            }
        });
    }


    /**
     * boiler plate of the request device storage permission
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "onRequestPermissionsResult: " + "permission granted");
                } else {
                    Log.e(TAG, "onRequestPermissionsResult: " + "permission denied");
                    finish();
                }
        }
    }

    /**
     * boiler plate of the process to select image from phone for upload
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_LOAD_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    img = BitmapFactory.decodeFile(picturePath);
                    resizeImg();
                }
        }
    }

}