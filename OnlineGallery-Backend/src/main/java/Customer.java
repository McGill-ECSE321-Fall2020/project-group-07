import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Customer extends UserRole{
   private Integer customerId;

public void setCustomerId(Integer value) {
    this.customerId = value;
}
@Id
public Integer getCustomerId() {
    return this.customerId;
}
   private Set<Artwork> browsePieces;
   
   @ManyToMany
   public Set<Artwork> getBrowsePieces() {
      return this.browsePieces;
   }
   
   public void setBrowsePieces(Set<Artwork> browsePiecess) {
      this.browsePieces = browsePiecess;
   }
   
   private Set<Purchase> purchases;
   
   @OneToMany(mappedBy="customer" )
   public Set<Purchase> getPurchases() {
      return this.purchases;
   }
   
   public void setPurchases(Set<Purchase> purchasess) {
      this.purchases = purchasess;
   }
   
   }
