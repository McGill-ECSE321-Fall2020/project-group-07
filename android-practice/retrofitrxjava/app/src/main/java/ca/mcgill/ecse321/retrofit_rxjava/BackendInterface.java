package ca.mcgill.ecse321.retrofit_rxjava;

import retrofit2.http.GET;
import retrofit2.http.Path;
import io.reactivex.Observable;

public interface BackendInterface {
    @GET("/{awsImgFileName}")
    Observable <String> getImgEncoding(@Path("awsImgFileName") String awsImgFileName);
}
