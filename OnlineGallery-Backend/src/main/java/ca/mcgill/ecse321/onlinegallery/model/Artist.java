package ca.mcgill.ecse321.onlinegallery.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import ca.mcgill.ecse321.onlinegallery.model.GalleryRegistration;

import java.util.Set;
import javax.persistence.OneToMany;

@Entity
@Table(name="artist")
public class Artist {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "artist_id")
	private Long artistId;
    
    @Column(name="bank_info")
	private String bankInfo;
    
    public Artist() {}
    
    @ManyToOne(fetch=FetchType.LAZY,optional = false)
    @JoinColumn(name = "registration_id", nullable = false)
	private GalleryRegistration galleryRegistration;

	public GalleryRegistration getGalleryRegistration() {
		return this.galleryRegistration;
	}

	public void setGalleryRegistration(GalleryRegistration galleryRegistration) {
		this.galleryRegistration = galleryRegistration;
	}
	

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,optional=false)
	private Profile profile;

	public Profile getProfile() {
		return this.profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	
	@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "artist")
	private Set<Artwork> artworks;
	
	public Set<Artwork> getArtworks() {
		return this.artworks;
	}

	public void setArtworks(Set<Artwork> artworkss) {
		this.artworks = artworkss;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void setArtistId(Long value) {
		this.artistId = value;
	}


	public Long getArtistId() {
		return this.artistId;
	}


	public void setBankInfo(String value) {
		this.bankInfo = value;
	}

	public String getBankInfo() {
		return this.bankInfo;
	}






}