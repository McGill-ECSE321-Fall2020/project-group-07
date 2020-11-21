package ca.mcgill.ecse321.retrofit_rxjava;

import java.util.List;

import ca.mcgill.ecse321.retrofit_rxjava.dto.ArtworkDto;
import ca.mcgill.ecse321.retrofit_rxjava.dto.AvailableNumDto;
import io.reactivex.Single;
import retrofit2.http.GET;
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
}
