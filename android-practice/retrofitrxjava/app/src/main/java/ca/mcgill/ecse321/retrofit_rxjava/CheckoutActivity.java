package ca.mcgill.ecse321.retrofit_rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.retrofit_rxjava.dto.PaymentDto;
import ca.mcgill.ecse321.retrofit_rxjava.dto.PurchaseDto;
import ca.mcgill.ecse321.retrofit_rxjava.dto.ShipmentDto;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class CheckoutActivity extends AppCompatActivity {
    private static final String TAG = "CheckoutActivity";
    private static final String BACKEND = "https://onlinegallery-backend-g7.herokuapp.com";

    private String artworkId;
    private String dest;
    private Double shipCost;
    private Double totalCost;
    private String recipient;
    private String customerUsername;
    private String customerId;
    private String shipType;

    private Long shipmentId=null;

    private String ccNum="";
    private String ccExp="";
    private String ccFirstname="";
    private String ccLastname="";
    private String ccCSV="";

    TextView totalView;
    TextView ccNumView;
    TextView ccExpView;
    TextView ccFirstView;
    TextView ccLastView;
    TextView ccCsvView;
    TextView ccErrorView;

    Button finishButton;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BACKEND)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    BackendInterface backendInterface = retrofit.create(BackendInterface.class);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        finishButton=findViewById(R.id.finish_button);

        ccNumView=findViewById(R.id.ccNum);
        ccNumView.setText("");

        ccFirstView=findViewById(R.id.ccFirstname);
        ccFirstView.setText("");

        ccLastView=findViewById(R.id.ccLastname);
        ccLastView.setText("");

        ccExpView=findViewById(R.id.ccExp);
        ccExpView.setText("");

        ccCsvView=findViewById(R.id.ccCSV);
        ccCsvView.setText("");

        ccErrorView =findViewById(R.id.ccError);
        ccErrorView.setText("");


        ccNumView.addTextChangedListener(ccWatcher);
        ccFirstView.addTextChangedListener(ccWatcher);
        ccLastView.addTextChangedListener(ccWatcher);
        ccExpView.addTextChangedListener(ccWatcher);
        ccCsvView.addTextChangedListener(ccWatcher);

        Intent intent=getIntent();

        artworkId=(String) intent.getSerializableExtra("ARTWORKID");
        dest=(String) intent.getSerializableExtra("DEST");
        shipCost=(Double) intent.getSerializableExtra("SHIPCOST");
        totalCost=(Double) intent.getSerializableExtra("TOTAL");
        recipient=(String) intent.getSerializableExtra("RECIPIENT");
        customerUsername="mike13";                          // to be replaced with customerl ogin page
        customerId="55";                                    // to be replaced with customer login page
        shipType=(String) intent.getSerializableExtra("SHIPTYPE");


        totalView=findViewById(R.id.checkout_total);
        totalView.setText("$"+totalCost.toString());

        createPurchase();
    }


    public void createPurchase(){
        PurchaseDto dto = new PurchaseDto();
        dto.setArtworkId(artworkId);
        dto.setUsername(customerUsername);
        dto.setShipmentType(shipType);

        Observable<PurchaseDto> createPurchaseCall=backendInterface.createPurchase(dto);

        createPurchaseCall
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Observer<PurchaseDto>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull PurchaseDto purchaseDto) {
                ShipmentDto sDto = new ShipmentDto();

                List<Long> ids = new ArrayList<>();
                ids.add(Long.parseLong(purchaseDto.getPurchaseId()));

                sDto.setShipmentId((long) 1);
                sDto.setSourceAddress("115 Normand, Montreal, QC");
                sDto.setDestinationAddress(dest);
                sDto.setShippingCost(shipCost);
                sDto.setTotalCost(totalCost);
                sDto.setShipmentStatus("CREATED");
                sDto.setRecipientName(recipient);
                sDto.setCustomerId(Long.parseLong(customerId));
                sDto.setPurchases(ids);

                Log.e(TAG, "onNext: "+sDto.toString() );

                createShipment(sDto);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void createShipment(ShipmentDto dto){
        Observable<ShipmentDto> call = backendInterface.createShipment(dto);
        call
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Observer<ShipmentDto>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ShipmentDto shipmentDto) {
                Log.e(TAG, "onNext: "+shipmentDto.toString() );
                shipmentId=shipmentDto.getShipmentId();
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
            }
        });

    }

    public void payShipment(View view){
        ccNum=ccNumView.getText().toString().trim();
        ccFirstname=ccFirstView.getText().toString().trim();
        ccLastname=ccLastView.getText().toString().trim();
        ccExp=ccExpView.getText().toString().trim();
        ccCSV=ccCsvView.getText().toString().trim();

        PaymentDto dto = new PaymentDto();
        dto.setShipmentId(shipmentId);
        dto.setCcNum(ccNum);
        dto.setCcCSV(ccCSV);
        dto.setCcExp(ccExp);
        dto.setFirstName(ccFirstname);
        dto.setLastName(ccLastname);

        Observable<ShipmentDto> call = backendInterface.payShipment(dto);
        call
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<ShipmentDto>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ShipmentDto shipmentDto) {
                Log.e(TAG, "onNext: "+shipmentDto.toString() );
            }

            @Override
            public void onError(@NonNull Throwable e) {

                if (e instanceof HttpException){
                    ResponseBody body = ((HttpException) e).response().errorBody();
                    try {
                        ccErrorView.setText(body.string());
                    } catch (IOException ioException) {
                        ccErrorView.setText("unknown error, try again later");
                    }
//                    try{
//                        JSONObject jError = new JSONObject(body.string());
//                        String msg = jError.getJSONObject("error").getString("message");
//                    }catch(Exception exception){
//                        Log.e(TAG, exception.toString());
//                    }
                }
            }

            @Override
            public void onComplete() {

            }
        });

    }

    private TextWatcher ccWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            boolean nonEmpty=(ccNumView.getText().toString().trim().length()>0 &&
                    ccFirstView.getText().toString().trim().length()>0 &&
                    ccLastView.getText().toString().trim().length()>0 &&
                    ccExpView.getText().toString().trim().length()>0 &&
                    ccCsvView.getText().toString().trim().length()>0);
            finishButton.setEnabled(nonEmpty);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean nonEmpty=(ccNumView.getText().toString().trim().length()>0 &&
                    ccFirstView.getText().toString().trim().length()>0 &&
                    ccLastView.getText().toString().trim().length()>0 &&
                    ccExpView.getText().toString().trim().length()>0 &&
                    ccCsvView.getText().toString().trim().length()>0);
            finishButton.setEnabled(nonEmpty);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}