/**
 * Item.java
 * 
 * 
 * @author omatei01 (2017)
 * 
 */
package ro.anzisoft.common.db.domain;

import lombok.Data;

/**
 * <b>Item</b>
 * <p>
 * -- descriere -- <br>
 * TODO
 * <p>
 * 
 * @author omatei01 (21 nov. 2017)
 *
 * @version 1.0
 */
@Data
public class ItemDb {

	private int itemId;// INTEGER PRIMARY KEY,
	private String itemCode;// STRING (30),
	private String itemName;// STRING (250),
	private String itemShortName;// STRING (30),
	private int itemType;// INTEGER,
	private String group1;// STRING (30),
	private String group2;// STRING (30),
	private String group3;// STRING (30),
	private String group4;// STRING (30),
	private String group5;// STRING (30),
	private int quantitySale;// INTEGER,
	private String umSale;// STRING (10),
	private int itemIdRef;// INTEGER,
	private int quantityRef;// INTEGER,
	private String umRef;// STRING (10),
	private String packCode;// STRING (30),
	private int itemPriceIncVat;// INTEGER,
	private int itemPriceExclVat;// INTEGER,
	private String itemVatQuote;// STRING (1),
	private int itemVatRate;// INTEGER,
	private int taxAdd;// INTEGER,
	private boolean itemWithScale;// BOOLEAN,
	private int itemDiscountQuote;// INTEGER,
	private int fidelityPoints;// INTEGER,
	private int redPriceIncVat;// INTEGER,
	private int itemDiscountAPercent;// INTEGER,
	private int itemDiscountAType;// INTEGER,
	private boolean itemPromo;// BOOLEAN,
	private int status;// INTEGER
	
}
