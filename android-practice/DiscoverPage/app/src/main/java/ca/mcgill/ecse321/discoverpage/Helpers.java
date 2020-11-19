package ca.mcgill.ecse321.discoverpage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public final class Helpers {

    public static Bitmap Base64ToBitmap(String rawEncoding){
        String base64=rawEncoding.split(",")[1];
        byte[] decoded=android.util.Base64.decode(base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decoded,0,decoded.length);
        return decodedByte;
    }
}
