/**
 * Receipts.java
 * 
 * 
 * @author omatei01 (19 iul. 2017)
 * 
 */
package ro.anzisoft.common.model;

import java.util.HashMap;
import java.util.Map;

import ro.anzisoft.common.db.domain.DocDb;

/**
 * Receipts
 * <p>
 * <b>Description</b>
 * <p>
 * gestionare Receipt in cadrul sesiunilor<br>

 * @author omatei01 (19 iul. 2017 16:12:29)
 *
 * @version 1.0
 */
public class Receipts {
	private final Map<String, DocDb> receiptsMap = new HashMap<>();
	private DocDb currentReceipt;

}
