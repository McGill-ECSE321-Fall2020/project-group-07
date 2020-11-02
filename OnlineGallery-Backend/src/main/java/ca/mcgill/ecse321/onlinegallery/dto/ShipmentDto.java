/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.onlinegallery.dto;
import java.util.*;

import ca.mcgill.ecse321.onlinegallery.model.ShipmentStatus;

// line 3 "../../../../../ShipmentDto.ump"
public class ShipmentDto
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ShipmentDto Attributes
  private Long shipmentId;
  private String sourceAddress;
  private String destinationAddress;
  private double shippingCost;
  private double totalCost;
  private ShipmentStatus shipmentStatus;
  private String recipientName;
  private Long customerId;

 
  private List<Long> purchases;

  public ShipmentDto () {
	  purchases = new ArrayList<Long>();
  }

  public boolean setShipmentId(Long aShipmentId)
  {
    boolean wasSet = false;
    shipmentId = aShipmentId;
    wasSet = true;
    return wasSet;
  }

  public boolean setSourceAddress(String aSourceAddress)
  {
    boolean wasSet = false;
    sourceAddress = aSourceAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setDestinationAddress(String aDestinationAddress)
  {
    boolean wasSet = false;
    destinationAddress = aDestinationAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setShippingCost(double aShippingCost)
  {
    boolean wasSet = false;
    shippingCost = aShippingCost;
    wasSet = true;
    return wasSet;
  }

  public boolean setTotalCost(double aTotalCost)
  {
    boolean wasSet = false;
    totalCost = aTotalCost;
    wasSet = true;
    return wasSet;
  }

  public boolean setShipmentStatus(ShipmentStatus aShipmentStatus)
  {
    boolean wasSet = false;
    shipmentStatus = aShipmentStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setRecipientName(String aRecipientName)
  {
    boolean wasSet = false;
    recipientName = aRecipientName;
    wasSet = true;
    return wasSet;
  }

  public boolean setCustomerId(Long acustomerId)
  {
    boolean wasSet = false;
    customerId = acustomerId;
    wasSet = true;
    return wasSet;
  }

  public Long getShipmentId()
  {
    return shipmentId;
  }

  public String getSourceAddress()
  {
    return sourceAddress;
  }

  public String getDestinationAddress()
  {
    return destinationAddress;
  }

  public double getShippingCost()
  {
    return shippingCost;
  }

  public double getTotalCost()
  {
    return totalCost;
  }

  public ShipmentStatus getShipmentStatus()
  {
    return shipmentStatus;
  }

  public String getRecipientName()
  {
    return recipientName;
  }

  public Long getCustomerId()
  {
    return customerId;
  }
  /* Code from template association_GetMany */
  public Long getPurchase(int index)
  {
    Long aPurchase = purchases.get(index);
    return aPurchase;
  }

  public List<Long> getPurchases()
  {
	  
	  return this.purchases;
  }
  
  public void setPurchases(List<Long> aListOfPurchases)
  {
	 this.purchases.clear();
	 purchases.addAll(aListOfPurchases);
  }


  public int indexOfPurchase(PurchaseDto aPurchase)
  {
    int index = purchases.indexOf(aPurchase);
    return index;
  }
 
  public void addPurchase(Long aPurchase)
  {
	  if (purchases == null) {
		  purchases = new ArrayList<Long>();
	  }
	  purchases.add(aPurchase);
  }

  public boolean removePurchase(Long aPurchase)
  {
    boolean wasRemoved = false;
    if (purchases.contains(aPurchase))
    {
      purchases.remove(aPurchase);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  


  public String toString()
  {
    return super.toString() + "["+
            "shipmentId" + ":" + getShipmentId()+ "," +
            "sourceAddress" + ":" + getSourceAddress()+ "," +
            "destinationAddress" + ":" + getDestinationAddress()+ "," +
            "shippingCost" + ":" + getShippingCost()+ "," +
            "totalCost" + ":" + getTotalCost()+ "," +
            "recipientName" + ":" + getRecipientName()+ "," +
            "userName" + ":" + getCustomerId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "shipmentStatus" + "=" + (getShipmentStatus() != null ? !getShipmentStatus().equals(this)  ? getShipmentStatus().toString().replaceAll("  ","    ") : "this" : "null");
  }
}