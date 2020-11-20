package ca.mcgill.ecse321.retrofit_rxjava;

import java.util.List;

import ca.mcgill.ecse321.retrofit_rxjava.dto.ArtworkDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import io.reactivex.Observable;

public interface BackendInterface {
    @GET("/getAllAvailableArtwork")
    Call<List<ArtworkDto>> getAllAvailableArtwork();

    @GET("/{awsImgFileName}")
    Observable <String> getImgEncoding(@Path("awsImgFileName") String awsImgFileName);
}
