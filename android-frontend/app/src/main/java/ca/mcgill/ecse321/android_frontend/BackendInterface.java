package ca.mcgill.ecse321.android_frontend;

import java.util.List;

import ca.mcgill.ecse321.android_frontend.dto.ArtistDto;
import ca.mcgill.ecse321.android_frontend.dto.ArtworkDto;
import ca.mcgill.ecse321.android_frontend.dto.AvailableNumDto;
import ca.mcgill.ecse321.android_frontend.dto.GalleryRegistrationDto;
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

    /**
     * call to retrieve all available artworks from backend
     * @return
     */
    @GET("/getAllAvailableArtwork")
    Observable<List<ArtworkDto>> getAllAvailableArtwork();

    /**
     * call to retrieve a number of random artworks from backend
     * @param num int number of random artworks to retrieve
     * @return
     */
    @GET("/retrieveRandomAvailableArtworks/{num}")
    Observable<List<ArtworkDto>> getRandomArtworks(@Path("num") int num);

    /**
     * call to count number of artworks available from the backend
     * @return
     */
    @GET("/countAvailableArtworks")
    Observable<AvailableNumDto> countAvailableArtwork();

    /**
     * call to fetch an Base64 String representation of a photo from AWS
     * @param awsImgFileName String unique identifier of the Base64 String file in the S3 bucket
     * @return
     */
    @GET("/{awsImgFileName}")
    Observable<String> getImgEncoding(@Path("awsImgFileName") String awsImgFileName);

    /**
     * call to create a purchase in the backend
     * @param dto PurchaseDto containing the information necessary to create purchase
     * @return
     */
    @POST("/createPurchase")
    Observable<PurchaseDto> createPurchase(@Body PurchaseDto dto);

    /**
     * call to create a shipment in the backend
     * @param dto ShipmentDto containing the information necessary to create a Shipment
     * @return
     */
    @POST("/createShipment")
    Observable<ShipmentDto> createShipment(@Body ShipmentDto dto);

    /**
     * call to pay a shipment
     * @param dto PaymentDto containing the information necessary to pay a Shipment
     * @return
     */
    @PUT("/payShipment")
    Observable<ShipmentDto> payShipment (@Body PaymentDto dto);

    /**
     * call to backend to get list of all Artists
     * @return
     */
    @GET("/getAllArtists")
    Observable<List<ArtistDto>> getAllArtists();

    /**
     * call to backend to retrieve a particular artist by his/her artistId
     * @param artistId Long identifying the artist
     * @return
     */
    @GET("/getArtistById/{artistId}")
    Observable<ArtistDto> getArtist(@Path("artistId") Long artistId);

    /**
     * call to backend to get an artist's available artworks
     * @param artistId Long identifying the artist
     * @return
     */
    @GET("/getAvailableArtworkByArtistId/{artistId}")
    Observable<List<ArtworkDto>> getArtistArtworks(@Path("artistId") String artistId);

    /**
     * call to upload a Base64 String representation of a photo to AWS
     * @param awsUrl String unique file identifier for the uploaded Base64 in S3
     * @param encoding String the Base64 String to upload
     * @return
     */
    @PUT("/{awsUrl}")
    Observable<String> uploadImgEncoding(@Path("awsUrl") String awsUrl,@Body String encoding);


    /**
     * call to backend to get the registration information associated with a username
     * @param username String
     * @return
     */
    @GET("/getRegistration/{username}")
    Observable<GalleryRegistrationDto> getRegistration(@Path("username") String username);

    /**
     * call to backend to create an Artwork
     * @param dto ArtworkDto containing the necessary information to crete an artwork
     * @return
     */
    @POST("/createArtwork")
    Observable<ArtworkDto> createArtwork(@Body ArtworkDto dto);
}
