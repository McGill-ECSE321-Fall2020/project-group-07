/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse321.onlinegallery.dto;

// line 3 "../../../../../ShipmentDto.ump"
public class ShipmentDto
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum ShipmentStatus { ONSITE_PICKUP, OFFSITE_DELIVERY }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ShipmentDto Attributes
  private String sourceAddress;
  private String destinationAddress;
  private double shippingCost;
  private double totalCost;
  private ShipmentStatus shipmentStatus;
  private String recipientName;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ShipmentDto(String aSourceAddress, String aDestinationAddress, double aShippingCost, double aTotalCost, ShipmentStatus aShipmentStatus, String aRecipientName)
  {
    sourceAddress = aSourceAddress;
    destinationAddress = aDestinationAddress;
    shippingCost = aShippingCost;
    totalCost = aTotalCost;
    shipmentStatus = aShipmentStatus;
    recipientName = aRecipientName;
  }

  //------------------------
  // INTERFACE
  //------------------------

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

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "sourceAddress" + ":" + getSourceAddress()+ "," +
            "destinationAddress" + ":" + getDestinationAddress()+ "," +
            "shippingCost" + ":" + getShippingCost()+ "," +
            "totalCost" + ":" + getTotalCost()+ "," +
            "recipientName" + ":" + getRecipientName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "shipmentStatus" + "=" + (getShipmentStatus() != null ? !getShipmentStatus().equals(this)  ? getShipmentStatus().toString().replaceAll("  ","    ") : "this" : "null");
  }
}