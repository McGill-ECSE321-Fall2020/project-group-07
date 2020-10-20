
public class GalleryAdmin {
private long adminId;

public void setAdminId(long value) {
   this.adminId = value;
}

public long getAdminId() {
   return this.adminId;
}

/**
 * <pre>
 *           0..1     1..1
 * GalleryAdmin ------------------------- GalleryRegistration
 *           admin        &lt;       galleryRegistration
 * </pre>
 */
private GalleryRegistration galleryRegistration;

public void setGalleryRegistration(GalleryRegistration value) {
   this.galleryRegistration = value;
}

public GalleryRegistration getGalleryRegistration() {
   return this.galleryRegistration;
}

}
