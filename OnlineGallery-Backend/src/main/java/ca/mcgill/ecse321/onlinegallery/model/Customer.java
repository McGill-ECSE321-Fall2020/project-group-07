package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;

@Entity
@Table(name="customer")
public class Customer {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "customer_id")
	private Long customerId;
    
    @Column(name="bank_info")
	private String bankInfo;
    
    public Customer() {}

	
    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name = "registration_id", nullable = false)
	private GalleryRegistration galleryRegistration;
	
	public GalleryRegistration getGalleryRegistration() {
		return this.galleryRegistration;
	}

	public void setGalleryRegistration(GalleryRegistration galleryRegistration) {
		this.galleryRegistration = galleryRegistration;
	}


	public void setCustomerId(Long value) {
		this.customerId = value;
	}


	public Long getCustomerId() {
		return this.customerId;
	}


	public void setBankInfo(String value) {
		this.bankInfo = value;
	}

	public String getBankInfo() {
		return this.bankInfo;
	}
	
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            },
            mappedBy = "viewers")
	   private Set<Artwork> browseArtworks;
	   
	   public Set<Artwork> getBrowseArtworks() {
	      return this.browseArtworks;
	   }
	   
	   public void setBrowseArtworks(Set<Artwork> browseArtworkss) {
	      this.browseArtworks = browseArtworkss;
	   }
   

   @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "customer")
   private Set<Purchase> purchases;
   
   public Set<Purchase> getPurchases() {
      return this.purchases;
   }
   
   public void setPurchases(Set<Purchase> purchasess) {
      this.purchases = purchasess;
   }
   
   

}