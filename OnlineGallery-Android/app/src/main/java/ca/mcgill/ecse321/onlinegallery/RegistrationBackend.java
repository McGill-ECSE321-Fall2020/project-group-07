package ca.mcgill.ecse321.onlinegallery;

import java.util.List;

import ca.mcgill.ecse321.onlinegallery.dto.GalleryRegistrationDto;
import retrofit2.http.POST;
import retrofit2.http.Path;
import io.reactivex.Observable;

public interface RegistrationBackend {

    @POST("/createRegistration")
    Observable<GalleryRegistrationDto> createRegistration();

}
