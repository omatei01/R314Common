/**
 * MfaMapperDb.java
 * 

 * @author omatei01 (2018)
 * 
 */
package ro.anzisoft.common.db.domain;

import lombok.Data;

/**
 * <b>MfaMapperDb</b><br>
 * @author omatei01 (2018)
 */
@Data
public class MfaConfigDb {
    String code;
    String opt;
    String val;    
    boolean flash;
    String  defa; //default
    String descr;

}
