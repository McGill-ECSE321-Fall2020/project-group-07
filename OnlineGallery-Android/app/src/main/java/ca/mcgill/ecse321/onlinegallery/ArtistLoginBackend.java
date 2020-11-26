package ca.mcgill.ecse321.onlinegallery;


import java.util.List;

import ca.mcgill.ecse321.onlinegallery.dto.ArtistDto;
import retrofit2.http.GET;
import retrofit2.http.Path;
import io.reactivex.Observable;

public interface ArtistLoginBackend {

    @GET("/getArtistByUsername/{username}")
    Observable<ArtistDto> getArtistByUsername(@Path("username") String username);

}
