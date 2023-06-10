/**
 * MfaData.java
 * 

 * @author omatei01 (2018)
 * 
 */
package ro.anzisoft.common.db.domain;

import lombok.Data;

/**
 * <b>MfaData</b><br>
 * 
 * @author omatei01 (2018)
 */
@Data
public class MfaDataDb {
	String code;
	String type;
	String tax;
	String valText;
	long valNum;
	String descr;
	boolean flash;
}
