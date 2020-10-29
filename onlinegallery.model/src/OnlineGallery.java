import java.util.Set;
import java.util.HashSet;

public class OnlineGallery {
private long systemId;

public void setSystemId(long value) {
   this.systemId = value;
}

public long getSystemId() {
   return this.systemId;
}

private int daysUp;

public void setDaysUp(int value) {
   this.daysUp = value;
}

public int getDaysUp() {
   return this.daysUp;
}

/**
 * <pre>
 *           1..1     0..1
 * OnlineGallery ------------------------> PhysicalGallery
 *           onlineGallery        &gt;       physicalGallery
 * </pre>
 */
private PhysicalGallery physicalGallery;

public void setPhysicalGallery(PhysicalGallery value) {
   this.physicalGallery = value;
}

public PhysicalGallery getPhysicalGallery() {
   return this.physicalGallery;
}

/**
 * <pre>
 *           1..1     0..*
 * OnlineGallery ------------------------> GalleryRegistration
 *           onlineGallery        &gt;       allRegistrations
 * </pre>
 */
private Set<GalleryRegistration> allRegistrations;

public Set<GalleryRegistration> getAllRegistrations() {
   if (this.allRegistrations == null) {
this.allRegistrations = new HashSet<GalleryRegistration>();
   }
   return this.allRegistrations;
}

}
