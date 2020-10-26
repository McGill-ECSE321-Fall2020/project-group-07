package ca.mcgill.ecse321.onlinegallery.service.Shipment;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.onlinegallery.dao.*;
import ca.mcgill.ecse321.onlinegallery.dto.*;
import ca.mcgill.ecse321.onlinegallery.model.*;
import ca.mcgill.ecse321.onlinegallery.service.*;
import ca.mcgill.ecse321.onlinegallery.service.exception.*;

@ExtendWith(MockitoExtension.class)
public class TestShipmentServiceAddToShipment {

	@Mock
	private ShipmentRepository shipRepo;

	@Mock
	private PurchaseRepository purchaseRepo;

	@InjectMocks
	private ShipmentService service;

	private static final Long VALID_SHIPID_EXISTPURCHASE_DELIVERY = (long) 0;
	private static final Long VALID_SHIPID_EXISTPURCHASE_PICKUP = (long) 1;
	private static final Long VALID_SHIPID_NOPURCHASE = (long) 2;
	private static final Long INVALID_SHIPID_NONEXIST = (long) 3;

	private static final Long VALID_PURCHASEID_DELIVERY = (long) 10;
	private static final Long VALID_PURCHASEID_PICKUP = (long) 11;
	private static final Long INVALID_PURCHASEID_NONEXIST = (long) 12;

	@BeforeEach
	public void setMockOutput() {

		lenient().when(purchaseRepo.existsById(anyLong())).thenAnswer((InvocationOnMock i) -> {
			if (i.getArgument(0).equals(INVALID_PURCHASEID_NONEXIST)) {
				return false;
			} else {
				return true;
			}
		});

		lenient().when(shipRepo.existsById(anyLong())).thenAnswer((InvocationOnMock i) -> {
			if (i.getArgument(0).equals(INVALID_SHIPID_NONEXIST)) {
				return false;
			} else {
				return true;
			}
		});

		lenient().when(purchaseRepo.findPurchaseByPurchaseId(anyLong())).thenAnswer((InvocationOnMock i) -> {
			if (i.getArgument(0).equals(VALID_PURCHASEID_DELIVERY)) {
				Purchase p = new Purchase();
				p.setPurchaseId(VALID_PURCHASEID_DELIVERY);
				p.setShipmentType(ShipmentType.OFFSITE_DELIVERY);
				return p;
			}

			if (i.getArgument(0).equals(VALID_PURCHASEID_PICKUP)) {
				Purchase p = new Purchase();
				p.setPurchaseId(VALID_PURCHASEID_PICKUP);
				p.setShipmentType(ShipmentType.ONSITE_PICKUP);
				return p;
			}

			return null;
		});

		lenient().when(shipRepo.findShipmentByShipmentId(anyLong())).thenAnswer((InvocationOnMock i) -> {
			if (i.getArgument(0).equals(VALID_SHIPID_EXISTPURCHASE_DELIVERY)) {
				Purchase p = new Purchase();
				p.setShipmentType(ShipmentType.OFFSITE_DELIVERY);

				Shipment s = new Shipment();
				s.getPurchase().add(p);

				return s;
			}

			if (i.getArgument(0).equals(VALID_SHIPID_EXISTPURCHASE_PICKUP)) {
				Purchase p = new Purchase();
				p.setShipmentType(ShipmentType.ONSITE_PICKUP);

				Shipment s = new Shipment();
				s.getPurchase().add(p);

				return s;
			}

			if (i.getArgument(0).equals(VALID_SHIPID_NOPURCHASE)) {

				Shipment s = new Shipment();
				return s;
			}

			return null;
		});

		lenient().when(shipRepo.save(any(Shipment.class))).thenAnswer((InvocationOnMock i) -> {
			return i.getArgument(0);
		});

	}

}
