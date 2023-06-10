/**
 * Customer.java
 * 
 * 
 * @author omatei01 (25 oct. 2017)
 * 
 */
package ro.anzisoft.common.db.domain;

import lombok.Data;

/**
 * <b>Customer</b>
 * vezi CustomerPos Table
 * 
 * @version 1.0
 */
@Data
public class CustomerDb {
	private String customerCode;// STRING (30),
	private String customerName;// STRING (100),
	private int customerDiscountType;// INTEGER,
	private String email;// STRING (100),
	private int status;// INTEGER
}
