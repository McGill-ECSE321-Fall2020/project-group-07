package ca.mcgill.ecse321.android_frontend;

import java.util.List;

import ca.mcgill.ecse321.android_frontend.dto.ArtistDto;
import ca.mcgill.ecse321.android_frontend.dto.ArtworkDto;
import ca.mcgill.ecse321.android_frontend.dto.AvailableNumDto;
import ca.mcgill.ecse321.android_frontend.dto.PaymentDto;
import ca.mcgill.ecse321.android_frontend.dto.PurchaseDto;
import ca.mcgill.ecse321.android_frontend.dto.ShipmentDto;
import retrofit2.http.Body;
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

    @GET("/getAllArtists")
    Observable<List<ArtistDto>> getAllArtists();

    @GET("/getAvailableArtworkByArtistId/{artistId}")
    Observable<List<ArtworkDto>> getArtistArtworks(@Path("artistId") String artistId);

    @PUT("/{awsUrl}")
    Observable<String> uploadImgEncoding(@Path("awsUrl") String awsUrl,@Body String encoding);
}
