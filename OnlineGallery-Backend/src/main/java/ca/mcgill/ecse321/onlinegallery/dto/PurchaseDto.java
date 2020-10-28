/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.onlinegallery.dto;

import ca.mcgill.ecse321.onlinegallery.model.ShipmentType;

// line 17 "../../../../../ShipmentDto.ump"
public class PurchaseDto
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PurchaseDto Attributes
  private Long purchaseId;
  private String username;
  private Long artworkId;
  private ShipmentType shipmentType;

  //PurchaseDto Associations
  private ShipmentDto shipment;

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPurchaseId(Long aPurchaseId)
  {
    boolean wasSet = false;
    purchaseId = aPurchaseId;
    wasSet = true;
    return wasSet;
  }

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    username = aUsername;
    wasSet = true;
    return wasSet;
  }

  public boolean setArtworkId(Long aArtworkId)
  {
    boolean wasSet = false;
    artworkId = aArtworkId;
    wasSet = true;
    return wasSet;
  }

  public boolean setShipmentType(ShipmentType aShipmentType)
  {
    boolean wasSet = false;
    shipmentType = aShipmentType;
    wasSet = true;
    return wasSet;
  }

  public Long getPurchaseId()
  {
    return purchaseId;
  }

  public String getUsername()
  {
    return username;
  }

  public Long getArtworkId()
  {
    return artworkId;
  }

  public ShipmentType getShipmentType()
  {
    return shipmentType;
  }
  /* Code from template association_GetOne */
  public ShipmentDto getShipment()
  {
    return shipment;
  }

  public boolean hasShipment()
  {
    boolean has = shipment != null;
    return has;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setShipment(ShipmentDto aShipment)
  {
    boolean wasSet = false;
    ShipmentDto existingShipment = shipment;
    shipment = aShipment;
    if (existingShipment != null && !existingShipment.equals(aShipment))
    {
      existingShipment.removePurchase(this);
    }
    if (aShipment != null)
    {
      aShipment.addPurchase(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    if (shipment != null)
    {
      ShipmentDto placeholderShipment = shipment;
      this.shipment = null;
      placeholderShipment.removePurchase(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "purchaseId" + ":" + getPurchaseId()+ "," +
            "username" + ":" + getUsername()+ "," +
            "artworkId" + ":" + getArtworkId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "shipmentType" + "=" + (getShipmentType() != null ? !getShipmentType().equals(this)  ? getShipmentType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "shipment = "+(getShipment()!=null?Integer.toHexString(System.identityHashCode(getShipment())):"null");
  }
}