package ca.mcgill.ecse321.discoverpage;

import java.util.List;

import ca.mcgill.ecse321.discoverpage.dtos.ArtworkDto;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BackendInterface {

    @GET("/getAllAvailableArtwork")
    Call<List<ArtworkDto>> getAllAvailableArtwork();

    @GET("/{awsImgFileName}")
    Observable<String> getImgEncoding(@Path("awsImgFileName") String awsImgFileName);

}
