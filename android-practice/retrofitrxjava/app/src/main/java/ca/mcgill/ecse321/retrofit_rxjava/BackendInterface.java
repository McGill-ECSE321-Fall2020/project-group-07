package ca.mcgill.ecse321.retrofit_rxjava;

import com.google.gson.annotations.JsonAdapter;

import java.util.List;

import ca.mcgill.ecse321.retrofit_rxjava.dto.ArtworkDto;
import ca.mcgill.ecse321.retrofit_rxjava.dto.AvailableNumDto;
import ca.mcgill.ecse321.retrofit_rxjava.dto.PaymentDto;
import ca.mcgill.ecse321.retrofit_rxjava.dto.PurchaseDto;
import ca.mcgill.ecse321.retrofit_rxjava.dto.ShipmentDto;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import io.reactivex.Observable;

public interface BackendInterface {
    @GET("/getAllAvailableArtwork")
    Observable<List<ArtworkDto>> getAllAvailableArtwork();

    @GET("/retrieveRandomAvailableArtworks/{num}")
    Observable<List<ArtworkDto>> getRandomArtworks(@Path("num") int num);

    @GET("/countAvailableArtworks")
    Observable<AvailableNumDto> countAvailableArtwork();

    @GET("/{awsImgFileName}")
    Observable<String> getImgEncoding(@Path("awsImgFileName") String awsImgFileName);

    @POST("/createPurchase")
    Observable<PurchaseDto> createPurchase(@Body PurchaseDto dto);

    @POST("/createShipment")
    Observable<ShipmentDto> createShipment(@Body ShipmentDto dto);

    @PUT("/payShipment")
    Observable<ShipmentDto> payShipment (@Body PaymentDto dto);
}
