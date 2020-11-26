package ca.mcgill.ecse321.onlinegallery;

import ca.mcgill.ecse321.onlinegallery.dto.GalleryRegistrationDto;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import io.reactivex.Observable;

public interface RegistrationBackend {

    @POST("/createRegistration")
    Observable<GalleryRegistrationDto> createRegistration(@Body GalleryRegistrationDto dto);

    @PUT("/setArtist/{username}")
    Observable<GalleryRegistrationDto> setArtist(@Path("username") String username);

    @PUT("/setCustomer/{username}")
    Observable<GalleryRegistrationDto> setCustomer(@Path("username") String username);


}
