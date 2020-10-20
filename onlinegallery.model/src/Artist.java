import java.util.Set;
import java.util.HashSet;

public class Artist {
private long artistId;

public void setArtistId(long value) {
   this.artistId = value;
}

public long getArtistId() {
   return this.artistId;
}

private String bankInfo;

public void setBankInfo(String value) {
   this.bankInfo = value;
}

public String getBankInfo() {
   return this.bankInfo;
}

/**
 * <pre>
 *           0..1     1..1
 * Artist ------------------------- GalleryRegistration
 *           artist        &lt;       galleryRegistration
 * </pre>
 */
private GalleryRegistration galleryRegistration;

public void setGalleryRegistration(GalleryRegistration value) {
   this.galleryRegistration = value;
}

public GalleryRegistration getGalleryRegistration() {
   return this.galleryRegistration;
}

/**
 * <pre>
 *           1..1     1..1
 * Artist ------------------------> Profile
 *           artist        &gt;       profile
 * </pre>
 */
private Profile profile;

public void setProfile(Profile value) {
   this.profile = value;
}

public Profile getProfile() {
   return this.profile;
}

/**
 * <pre>
 *           1..1     0..*
 * Artist ------------------------> Artwork
 *           artist        &gt;       artwork
 * </pre>
 */
private Set<Artwork> artwork;

public Set<Artwork> getArtwork() {
   if (this.artwork == null) {
this.artwork = new HashSet<Artwork>();
   }
   return this.artwork;
}

}
