import java.util.Set;
import java.util.HashSet;

public class Customer {
private long customerId;

public void setCustomerId(long value) {
   this.customerId = value;
}

public long getCustomerId() {
   return this.customerId;
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
 *           0..*     1..1
 * Customer ------------------------- GalleryRegistration
 *           customer        &lt;       galleryRegistration
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
 *           1..1     0..*
 * Customer ------------------------> Purchase
 *           customer        &gt;       purchase
 * </pre>
 */
private Set<Purchase> purchase;

public Set<Purchase> getPurchase() {
   if (this.purchase == null) {
this.purchase = new HashSet<Purchase>();
   }
   return this.purchase;
}

/**
 * <pre>
 *           0..*     0..*
 * Customer ------------------------- Artwork
 *           view        &gt;       browsedArtwork
 * </pre>
 */
private Set<Artwork> browsedArtwork;

public Set<Artwork> getBrowsedArtwork() {
   if (this.browsedArtwork == null) {
this.browsedArtwork = new HashSet<Artwork>();
   }
   return this.browsedArtwork;
}

/**
 * <pre>
 *           1..1     0..*
 * Customer ------------------------> Shipment
 *           customer        &gt;       shipment
 * </pre>
 */
private Set<Shipment> shipment;

public Set<Shipment> getShipment() {
   if (this.shipment == null) {
this.shipment = new HashSet<Shipment>();
   }
   return this.shipment;
}

}
