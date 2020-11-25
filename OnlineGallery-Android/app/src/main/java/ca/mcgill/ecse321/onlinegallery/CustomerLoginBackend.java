package ca.mcgill.ecse321.onlinegallery;

import java.util.List;

import ca.mcgill.ecse321.onlinegallery.dto.CustomerDto;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import io.reactivex.Observable;

public interface CustomerLoginBackend {

   @GET("/getCustomerByUsername/{username}")
    Observable<CustomerDto> getCustomerByUsername(@Path("username") String username);
}