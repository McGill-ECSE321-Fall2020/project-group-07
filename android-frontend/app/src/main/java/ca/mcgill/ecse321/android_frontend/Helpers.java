package ca.mcgill.ecse321.android_frontend;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public final class Helpers {

    /**
     * utility functio nto convert a Base64 String of an image to a Bitmap
     * @param rawEncoding String, Base64 String of an image
     * @return
     */
    public static Bitmap Base64ToBitmap(String rawEncoding){
        String base64=rawEncoding.split(",")[1];
        byte[] decoded= Base64.decode(base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decoded,0,decoded.length);
        return decodedByte;
    }
}
