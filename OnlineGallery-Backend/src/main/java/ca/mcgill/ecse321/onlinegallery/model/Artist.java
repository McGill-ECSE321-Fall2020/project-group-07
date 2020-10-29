package ca.mcgill.ecse321.onlinegallery.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;

@Entity
@Table(name = "artist")
public class Artist {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "artist_id")
	private Long artistId;

	public void setArtistId(Long value) {
		this.artistId = value;
	}

	public Long getArtistId() {
		return this.artistId;
	}

	private String bankInfo;

	public void setBankInfo(String value) {
		this.bankInfo = value;
	}

	public String getBankInfo() {
		return this.bankInfo;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "artist")
	private GalleryRegistration galleryRegistration;

	public void setGalleryRegistration(GalleryRegistration value) {
		this.galleryRegistration = value;
	}

	public GalleryRegistration getGalleryRegistration() {
		return this.galleryRegistration;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = true)
	private Profile profile;

	public void setProfile(Profile value) {
		this.profile = value;
	}

	public Profile getProfile() {
		return this.profile;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "artist")
	private Set<Artwork> artwork;

	public Set<Artwork> getArtwork() {
		if (this.artwork == null) {
			this.artwork = new HashSet<Artwork>();
		}
		return this.artwork;
	}

}
