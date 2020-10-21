import java.util.Set;
import java.util.HashSet;

public class Artwork {
private long artworkId;

public void setArtworkId(long value) {
   this.artworkId = value;
}

public long getArtworkId() {
   return this.artworkId;
}

private String name;

public void setName(String value) {
   this.name = value;
}

public String getName() {
   return this.name;
}

private String description;

public void setDescription(String value) {
   this.description = value;
}

public String getDescription() {
   return this.description;
}

private double price;

public void setPrice(double value) {
   this.price = value;
}

public double getPrice() {
   return this.price;
}

private ArtworkStatus status;

public void setStatus(ArtworkStatus value) {
   this.status = value;
}

public ArtworkStatus getStatus() {
   return this.status;
}

private boolean onSite;

public void setOnSite(boolean value) {
   this.onSite = value;
}

public boolean isOnSite() {
   return this.onSite;
}

private int numViews;

public void setNumViews(int value) {
   this.numViews = value;
}

public int getNumViews() {
   return this.numViews;
}

private String dimension;

public void setDimension(String value) {
   this.dimension = value;
}

public String getDimension() {
   return this.dimension;
}

private double weight;

public void setWeight(double value) {
   this.weight = value;
}

public double getWeight() {
   return this.weight;
}

private double shippingCost;

public void setShippingCost(double value) {
   this.shippingCost = value;
}

public double getShippingCost() {
   return this.shippingCost;
}

/**
 * <pre>
 *           1..1     0..1
 * Artwork ------------------------- Purchase
 *           artwork        &lt;       purchase
 * </pre>
 */
private Purchase purchase;

public void setPurchase(Purchase value) {
   this.purchase = value;
}

public Purchase getPurchase() {
   return this.purchase;
}

/**
 * <pre>
 *           0..*     0..*
 * Artwork ------------------------- Customer
 *           browsedArtwork        &lt;       view
 * </pre>
 */
private Set<Customer> view;

public Set<Customer> getView() {
   if (this.view == null) {
this.view = new HashSet<Customer>();
   }
   return this.view;
}

}
